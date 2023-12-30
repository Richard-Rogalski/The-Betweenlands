package thebetweenlands.entities.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import thebetweenlands.entities.projectiles.EntitySnailPoisonJet;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.misc.ItemGeneric;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;

public class EntityBloodSnail extends EntityMob implements IEntityBL {

	public EntityBloodSnail(World world) {
		super(world);
		setSize(0.7F, 0.5F);
		stepHeight = 0.0F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.5D, false));
		tasks.addTask(2, new EntityAIWander(this, 0.5D));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 3;
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		if (isBurning())
			entityDropItem(ItemGeneric.createStack(BLItemRegistry.snailFleshCooked, 1, 0), 0.0F);
		else
			entityDropItem(ItemGeneric.createStack(BLItemRegistry.snailFleshRaw, 1, 0), 0.0F);

		if (rand.nextBoolean())
			entityDropItem(ItemGeneric.createStack(EnumItemGeneric.BLOOD_SNAIL_SHELL, 1), 0.0F);		
		else
			entityDropItem(ItemGeneric.createStack(EnumItemGeneric.POISON_GLAND, 2), 0.0F);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "thebetweenlands:snailLiving";
	}

	@Override
	protected String getHurtSound() {
		return "thebetweenlands:snailHurt";
	}

	@Override
	protected String getDeathSound() {
		return "thebetweenlands:snailDeath";
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLiving) {
				byte duration = 0;
				if (worldObj.difficultySetting == EnumDifficulty.NORMAL)
					duration = 7;
				else if (worldObj.difficultySetting == EnumDifficulty.HARD)
					duration = 15;

				if (duration > 0) {
					((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
					((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, duration * 20, 0));
				}
			}
			return true;
		} else
			return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (getAttackTarget() != null && this.isEntityAlive()) {
			float distance = (float) getDistance(getAttackTarget().posX, getAttackTarget().boundingBox.minY, getAttackTarget().posZ);
			if (getRangeAttackTimer() < 100 && distance > 3)
				setRangeAttackTimer(getRangeAttackTimer() + 2);
			if (getRangeAttackTimer() == 100 && distance > 3)
				shootMissile(getAttackTarget(), distance);
		}
	}

	public void shootMissile(EntityLivingBase entity, float distance) {
		setRangeAttackTimer(0);
		if (canEntityBeSeen(entity)) {
			EntityThrowable missile = new EntitySnailPoisonJet(worldObj, this);
			missile.rotationPitch -= -20.0F;
			double targetX = entity.posX + entity.motionX - posX;
			double targetY = entity.posY + entity.getEyeHeight() - 1.100000023841858D - posY;
			double targetZ = entity.posZ + entity.motionZ - posZ;
			float target = MathHelper.sqrt_double(targetX * targetX + targetZ * targetZ);
			missile.setThrowableHeading(targetX, targetY + target * 0.1F, targetZ, 0.75F, 8.0F);
			worldObj.spawnEntityInWorld(missile);
		}
	}

	public void setRangeAttackTimer(int size) {
		dataWatcher.updateObject(20, size);
	}

	public int getRangeAttackTimer() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	@Override
	public String pageName() {
		return "bloodSnail";
	}
}
