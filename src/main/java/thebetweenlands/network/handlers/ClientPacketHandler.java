package thebetweenlands.network.handlers;

import java.util.HashMap;

import com.google.common.collect.Maps;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.client.audio.EntityIdleSound;
import thebetweenlands.client.particle.BLParticle;
import thebetweenlands.network.base.SubscribePacket;
import thebetweenlands.network.packet.client.PacketPlayIdleSound;
import thebetweenlands.network.packet.server.PacketAttackTarget;
import thebetweenlands.network.packet.server.PacketDruidAltarProgress;
import thebetweenlands.network.packet.server.PacketDruidTeleportParticle;
import thebetweenlands.network.packet.server.PacketRevengeTarget;
import thebetweenlands.network.packet.server.PacketSnailHatchParticle;
import thebetweenlands.network.packet.server.PacketTickspeed;
import thebetweenlands.network.packet.server.PacketWeedWoodBushRustle;
import thebetweenlands.proxy.ClientProxy;
import thebetweenlands.tileentities.TileEntityDruidAltar;

public class ClientPacketHandler {

	////// Entity idle sounds /////
	@SubscribePacket
	public static void handlePacketPlayIdleSound(PacketPlayIdleSound packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		if(world != null && world.isRemote) {
			Entity source = packet.getEntity(world);
			if(source != null && Minecraft.getMinecraft().getSoundHandler() != null && source.isEntityAlive()) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new EntityIdleSound(source, new ResourceLocation(packet.name), packet.volume, packet.pitch));
			}
		}
	}

	////// Druid //////
	@SubscribePacket
	public static void handleDruidTeleportParticles(PacketDruidTeleportParticle packet) {
		World world = FMLClientHandler.instance().getWorldClient();

		if (world != null && world.isRemote)
		{
			for (int a = 0; a < 360; a += 4)
			{
				double ang = a * Math.PI / 180D;
				BLParticle.SMOKE.spawn(world, packet.x - MathHelper.sin((float) ang) * 0.25D, packet.y, packet.z + MathHelper.cos((float) ang) * 0.25D, -MathHelper.sin((float) ang) * 0.1D, 0.01D, MathHelper.cos((float) ang) * 0.1, 0);
				BLParticle.SMOKE.spawn(world, packet.x -MathHelper.sin((float) ang) * 0.25D, packet.y + 0.5D, packet.z + MathHelper.cos((float) ang) * 0.25D, -MathHelper.sin((float) ang) * 0.1D, 0.01D, MathHelper.cos((float) ang) * 0.1, 0);
				BLParticle.SMOKE.spawn(world, packet.x -MathHelper.sin((float) ang) * 0.25D, packet.y + 1D, packet.z + MathHelper.cos((float) ang) * 0.25D, -MathHelper.sin((float) ang) * 0.1D, 0.01D, MathHelper.cos((float) ang) * 0.1, 0);
			}
		}
	}




	////// Druid Altar //////
	private static final ResourceLocation soundLocation = new ResourceLocation("thebetweenlands:druidChant");
	private static final HashMap<String, PositionedSoundDC> tileSoundMap = Maps.newHashMap();
	@SideOnly(Side.CLIENT)
	static class PositionedSoundDC extends PositionedSound {
		protected PositionedSoundDC(ResourceLocation resourceLocation) {
			super(resourceLocation);
		}

		public PositionedSoundDC setPos(float x, float y, float z) {
			xPosF = x;
			yPosF = y;
			zPosF = z;
			return this;
		}
	}
	@SubscribePacket
	public static void handleDruidAltar(PacketDruidAltarProgress packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		if (world != null && world.isRemote) {
			TileEntity te = world.getTileEntity(packet.x, packet.y, packet.z);
			if (te instanceof TileEntityDruidAltar) {
				TileEntityDruidAltar teda = (TileEntityDruidAltar) te;
				if (packet.progress < 0) {
					SoundHandler mcSoundHandler = Minecraft.getMinecraft().getSoundHandler();
					if (packet.progress == -1) {
						PositionedSoundDC sound = tileSoundMap.get(teda.xCoord + "|" + teda.yCoord + "|" + teda.zCoord);
						if (sound == null) {
							sound = new PositionedSoundDC(soundLocation).setPos(packet.x, packet.y, packet.z);
							tileSoundMap.put(packet.x + "|" + packet.y + "|" + packet.z, sound);
						} else {
							if (mcSoundHandler.isSoundPlaying(sound)) {
								mcSoundHandler.stopSound(sound);
							}
						}
						mcSoundHandler.playSound(sound);
						for (int x = -8; x < 8; x++) {
							for (int y = -8; y < 8; y++) {
								for (int z = -8; z < 8; z++) {
									Block block = world.getBlock(teda.xCoord + x, teda.yCoord + y, teda.zCoord + z);
									if (block == BLBlockRegistry.druidStone1 || block == BLBlockRegistry.druidStone2 || 
											block == BLBlockRegistry.druidStone3 || block == BLBlockRegistry.druidStone4 || 
											block == BLBlockRegistry.druidStone5) {
										BLParticle.ALTAR_CRAFTING.spawn(world, te.xCoord + x, te.yCoord + y, te.zCoord + z, 0, 0, 0, 1.0F, teda);
									}
								}
							}
						}
					} else if (packet.progress == -2) {
						PositionedSoundDC sound = tileSoundMap.get(packet.x + "|" + packet.y + "|" + packet.z);
						if (sound != null) {
							if (mcSoundHandler.isSoundPlaying(sound)) {
								mcSoundHandler.stopSound(sound);
							}
						}
					}
				}
			}
		}
	}


	////// Snail Hatch Particle //////
	public static void handleSnailHatchParticle(PacketSnailHatchParticle packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		EffectRenderer eff = Minecraft.getMinecraft().effectRenderer;
		if (world != null && world.isRemote) {
			for (int count = 0; count <= 50; ++count) {
				eff.addEffect(new EntityBreakingFX(world, packet.x + (world.rand.nextDouble() - 0.5D) * 0.35F, packet.y + world.rand.nextDouble() * 0.175F, packet.z + (world.rand.nextDouble() - 0.5D) * 0.35F, Items.slime_ball));
			}
		}
	}

	@SubscribePacket
	public static void handleTickspeed(PacketTickspeed packet) {
		ClientProxy.debugTimer.setTicksPerSecond(packet.getTicksPerSecond());
	}

	@SubscribePacket
	public static void handleSetAttackTarget(PacketRevengeTarget packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		Entity entity = world.getEntityByID(packet.getAId());
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			Entity target = packet.getBId() == -1 ? null : world.getEntityByID(packet.getBId());
			if (target == null) {
				entityLivingBase.setRevengeTarget(null);
			} else if (target instanceof EntityLivingBase) {
				entityLivingBase.setRevengeTarget((EntityLivingBase) target);
			}
		}
	}

	@SubscribePacket
	public static void handleSetAttackTarget(PacketAttackTarget packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		Entity entity = world.getEntityByID(packet.getAId());
		if (entity instanceof EntityLiving) {
			EntityLiving entityLiving = (EntityLiving) entity;
			Entity target = packet.getBId() == -1 ? null : world.getEntityByID(packet.getBId());
			if (target == null) {
				entityLiving.setAttackTarget(null);
			} else if (target instanceof EntityLivingBase) {
				entityLiving.setAttackTarget((EntityLivingBase) target);
			}
		}
	}

	@SubscribePacket
	public static void handleWeedWoodBushRustle(PacketWeedWoodBushRustle packet) {
		World world = FMLClientHandler.instance().getWorldClient();
		int leafCount = (int)(47 * packet.getStrength()) + 1;
		float x = packet.getX() + 0.5F, y = packet.getY() + 0.5F, z = packet.getZ() + 0.5F;
		while (leafCount --> 0) {
			BLParticle.RUSTLE_LEAF.spawn(world, x, y, z);
		}
	}
}
