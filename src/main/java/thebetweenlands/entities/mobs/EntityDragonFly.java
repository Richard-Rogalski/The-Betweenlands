package thebetweenlands.entities.mobs;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.misc.ItemGeneric;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thebetweenlands.manual.ManualManager;

public class EntityDragonFly extends EntityAmbientCreature implements IEntityBL {
	public ChunkCoordinates currentFlightTarget;
	public boolean entityFlying;

	public EntityDragonFly(World world) {
		super(world);
		setSize(0.9F, 0.5F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(2, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.9D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	protected void fall(float par1) {
	}

	@Override
	protected String getLivingSound() {
		return "thebetweenlands:dragonfly";
	}

	@Override
	protected String getDeathSound() {
		return "thebetweenlands:squish";
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		int chance = rand.nextInt(4) + rand.nextInt(1 + looting);
		int amount;
		for (amount = 0; amount < chance; ++amount) {
			entityDropItem(ItemGeneric.createStack(EnumItemGeneric.DRAGONFLY_WING, 4), 0.0F);
		}
	}

	public boolean isFlying() {
		return !onGround;
	}

	public void setEntityFlying(boolean state) {
		entityFlying = state;
	}

	@Override
	public void onUpdate() {
		if (motionY < 0.0D) {
			motionY *= 0.6D;
		}
		if (!worldObj.isRemote) {
			if (rand.nextInt(200) == 0) {
				if (!entityFlying) {
					setEntityFlying(true);
				} else {
					setEntityFlying(false);
				}
			}
			if (entityFlying) {
				flyAbout();
			} else {
				land();
			}
			if (!entityFlying) {
				if (isInWater()) {
					motionY += 0.2F;
				}
				if (worldObj.isAnyLiquid(boundingBox.expand(0D, 1D, 0D))) {
					flyAbout();
				}
				if (worldObj.getClosestPlayerToEntity(this, 4.0D) != null) {
					flyAbout();
				}
			}
		}
		super.onUpdate();
	}

	public void flyAbout() {
		if (currentFlightTarget != null) {
			if (!worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ) || currentFlightTarget.posY < 1 || worldObj.getBlock(currentFlightTarget.posX, currentFlightTarget.posY + 1, currentFlightTarget.posZ) == Blocks.water) {
				currentFlightTarget = null;
			}
		}
		if (currentFlightTarget == null || rand.nextInt(30) == 0 || currentFlightTarget.getDistanceSquared((int) posX, (int) posY, (int) posZ) < 10F) {
			currentFlightTarget = new ChunkCoordinates((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
		}
		flyToTarget();
	}

	public void flyToTarget() {
		if (currentFlightTarget != null) {
			double targetX = currentFlightTarget.posX + 0.5D - posX;
			double targetY = currentFlightTarget.posY + 1D - posY;
			double targetZ = currentFlightTarget.posZ + 0.5D - posZ;
			motionX += (Math.signum(targetX) * 0.5D - motionX) * 0.1D;
			motionY += (Math.signum(targetY) * 0.7D - motionY) * 0.1D;
			motionZ += (Math.signum(targetZ) * 0.5D - motionZ) * 0.1D;
			float angle = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
			float rotation = MathHelper.wrapAngleTo180_float(angle - rotationYaw);
			moveForward = 0.5F;
			rotationYaw += rotation;
		}
	}

	private void land() {
		// Nothing to see here - yet
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return isInLurkersMouth() ? false : super.isInRangeToRender3d(x, y, z);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isInLurkersMouth();
	}

	@Override
	protected boolean canDespawn() {
		return !isInLurkersMouth();
	}

	@Override
	public boolean canEntityBeSeen(Entity entity) {
		return isInLurkersMouth() ? false : super.canEntityBeSeen(entity);
	}

	private boolean isInLurkersMouth() {
		return ridingEntity instanceof EntityLurker;
	}

	@Override
	protected void onDeathUpdate() {
		deathTime++;
		if (deathTime == 20) {
			if (!worldObj.isRemote && (recentlyHit > 0 || isPlayer()) && func_146066_aG() && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
				int experiencePoints = getExperiencePoints(attackingPlayer);
				while (experiencePoints > 0) {
					int amount = EntityXPOrb.getXPSplit(experiencePoints);
					experiencePoints -= amount;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, amount));
				}
			}
			setDead();
			if (!isInLurkersMouth()) {
				for (int particle = 0; particle < 20; particle++) {
					double motionX = rand.nextGaussian() * 0.02D;
					double motionY = rand.nextGaussian() * 0.02D;
					double motionZ = rand.nextGaussian() * 0.02D;
					worldObj.spawnParticle("explode", posX + rand.nextFloat() * width * 2.0F - width, posY + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, motionX, motionY, motionZ);
				}
			}
		}
	}
	@Override
	public String pageName() {
		return "dragonFly";
	}
}
