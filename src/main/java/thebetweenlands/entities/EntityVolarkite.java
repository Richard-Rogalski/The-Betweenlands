package thebetweenlands.entities;

import io.netty.buffer.ByteBuf;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.misc.ItemVolarkite;

import com.google.common.base.Strings;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityVolarkite extends Entity implements IEntityAdditionalSpawnData {

	private static Map<EntityPlayer, Integer> gliderMap = new WeakHashMap<EntityPlayer, Integer>();
	private static Map<EntityPlayer, Integer> gliderClientMap = new WeakHashMap<EntityPlayer, Integer>();

	public static Map<EntityPlayer, Integer> getMapForSide(boolean isRemote) {
		return isRemote? gliderClientMap : gliderMap;
	}

	public static boolean isEntityHoldingGlider(Entity player) {
		return gliderClientMap.containsKey(player);
	}

	public static boolean isPlayerOnGround(Entity player) {
		Integer gliderId = gliderClientMap.get(player);
		if (gliderId != null) {
			Entity glider = player.worldObj.getEntityByID(gliderId);
			if (glider instanceof EntityVolarkite)
				return ((EntityVolarkite)glider).isPlayerOnGround();
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public static void updateGliders(World worldObj) {
		Iterator<Entry<EntityPlayer, Integer>> it = gliderClientMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EntityPlayer, Integer> next = it.next();
			EntityPlayer player = next.getKey();
			Entity entity = worldObj.getEntityByID(next.getValue());
			if (!(entity instanceof EntityVolarkite))
				continue;
			EntityVolarkite glider = (EntityVolarkite)entity;
			if (player == null || player.isDead || glider.isDead || player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemVolarkite) || player.worldObj.provider.dimensionId != glider.worldObj.provider.dimensionId) {
				glider.setDead();
				it.remove();
			} else 
				glider.fixPositions(Minecraft.getMinecraft().thePlayer);
		}
	}

	private EntityPlayer player;

	private boolean shouldDespawn = false;

	public EntityVolarkite(World world) {
		super(world);
	}

	public EntityVolarkite(World world, EntityPlayer player) {
		this(world);
		this.player = player;
		gliderMap.put(player, getEntityId());
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(2, Byte.valueOf((byte)0));
	}

	public void despawnGlider() {
		shouldDespawn = true;
	}

	public boolean isPlayerOnGround() {
		return this.dataWatcher.getWatchableObjectByte(2) == 1;
	}

	@Override
	public void onUpdate() {
		if (player == null) {
			setDead();
		} else {
			if (!worldObj.isRemote)
				this.dataWatcher.updateObject(2, Byte.valueOf((byte)(player.onGround? 1 : 0)));

			ItemStack held = player.getHeldItem();
			if (player.isDead || held == null || held.getItem() == null || held.getItem() != BLItemRegistry.volarkite || shouldDespawn || player.dimension != this.dimension) {
				getMapForSide(worldObj.isRemote).remove(player);
				setDead();
			} else {
				fixPositions();
				double horizontalSpeed = 0.03;
				double verticalSpeed = 0.4;
				if (player.isSneaking()) {
					horizontalSpeed = 0.1;
					verticalSpeed = 0.7;
				}
				if (!player.onGround && player.motionY < 0) {
					player.motionY *= verticalSpeed;
					motionY *= verticalSpeed;
					double x = Math.cos(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
					double z = Math.sin(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
					player.motionX += x;
					player.motionZ += z;
					player.fallDistance = 0f;
				}
			}
		}
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	private void fixPositions(EntityPlayer thePlayer) {

		if (player != null) {

			this.lastTickPosX = prevPosX = player.prevPosX;
			this.lastTickPosY = prevPosY = player.prevPosY;
			this.lastTickPosZ = prevPosZ = player.prevPosZ;

			this.posX = player.posX;
			this.posY = player.posY;
			this.posZ = player.posZ;

			setPosition(posX, posY, posZ);
			this.prevRotationYaw = player.prevRenderYawOffset;
			this.rotationYaw = player.renderYawOffset;

			this.prevRotationPitch = player.prevRotationPitch;
			this.rotationPitch = player.rotationPitch;

			if (player != thePlayer) {
				this.posY += 1.2;
				this.prevPosY += 1.2;
				this.lastTickPosY += 1.2;
			}

			this.motionX = this.posX - this.prevPosX;
			this.motionY = this.posY - this.prevPosY;
			this.motionZ = this.posZ - this.prevPosZ;

		}

	}

	public void fixPositions() {
		fixPositions(null);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

	@Override
	public void writeSpawnData(ByteBuf data) {
		ByteBufUtils.writeUTF8String(data, player != null? player.getCommandSenderName() : "");
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		String username = ByteBufUtils.readUTF8String(data);
		if (Strings.isNullOrEmpty(username)) {
			setDead();
		} else {
			player = worldObj.getPlayerEntityByName(username);
			gliderClientMap.put(player, getEntityId());
		}
	}
}



