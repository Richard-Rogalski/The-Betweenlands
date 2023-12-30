package thebetweenlands.entities.projectiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import thebetweenlands.client.particle.BLParticle;
import thebetweenlands.entities.mobs.EntityWight;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBoss;

public class EntityVolatileSoul extends Entity implements IProjectile {
	private Entity target = null;
	private int strikes = 0;
	private int ticksInAir;

	public EntityVolatileSoul(World world) {
		super(world);
		this.setSize(0.3F, 0.3F);
		this.noClip = true;
	}

	public EntityVolatileSoul(World world, EntityWight source) {
		this(world);
		this.setOwner(source.getUniqueID().toString());
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, "");
	}

	public void setOwner(String ownerUUID) {
		this.dataWatcher.updateObject(17, ownerUUID);
	}

	public String getOwnerUUID() {
		return this.dataWatcher.getWatchableObjectString(17);
	}

	public Entity getOwner() {
		try {
			UUID uuid = UUID.fromString(this.getOwnerUUID());
			return uuid == null ? null : this.getEntityByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}

	private Entity getEntityByUUID(UUID p_152378_1_) {
		for (int i = 0; i < this.worldObj.loadedEntityList.size(); ++i) {
			Entity entity = (Entity)this.worldObj.loadedEntityList.get(i);
			if (p_152378_1_.equals(entity.getUniqueID())) {
				return entity;
			}
		}
		return null;
	}

	protected void onImpact(MovingObjectPosition target) {
		if (target.entityHit != null && target.entityHit instanceof EntityLivingBase && target.entityHit instanceof EntityWight == false) {
			if(!this.worldObj.isRemote) {
				if(target.entityHit instanceof EntityPlayer && ((EntityPlayer)target.entityHit).isBlocking() && ((EntityPlayer)target.entityHit).getItemInUseDuration() <= 10) {
					this.motionX *= -6;
					this.motionY *= -6;
					this.motionZ *= -6;
					this.strikes++;
					return;
				}
				target.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getOwner()), 3);
				if(!this.isDead && target.entityHit instanceof EntityPlayer && (target.entityHit.isDead || ((EntityLivingBase)target.entityHit).getHealth() <= 0.0F)) {
					target.entityHit.setDead();
					/*EntityWight wight = new EntityWight(this.worldObj);
					wight.setLocationAndAngles(target.entityHit.posX, target.entityHit.posY + 0.05D, target.entityHit.posZ, target.entityHit.rotationYaw, target.entityHit.rotationPitch);
					if(this.worldObj.getCollidingBoundingBoxes(wight, wight.boundingBox).isEmpty()) {
						this.worldObj.spawnEntityInWorld(wight);
					}*/
				}
				this.setDead();
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
			}
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (this.isEntityInvulnerable()) {
			return false;
		} else {
			this.strikes++;
			if(this.strikes >= 3) {
				this.setDead();
				return true;
			}
			this.setBeenAttacked();
			if (source.getEntity() != null) {
				if(!this.worldObj.isRemote) {
					Vec3 vec3 = source.getEntity().getLookVec();
					if (vec3 != null) {
						this.motionX = vec3.xCoord * 1.5F;
						this.motionY = vec3.yCoord * 1.5F;
						this.motionZ = vec3.zCoord * 1.5F;
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void onUpdate() {
		if(!this.worldObj.isRemote && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL || this.getOwner() == null || this.getOwner().isDead)) {
			this.setDead();
			return;
		}

		if(!this.worldObj.isRemote) {
			if((this.getOwner() == null || !this.getOwner().isEntityAlive() || this.getOwner() instanceof EntityWight == false || !((EntityWight)this.getOwner()).isVolatile()) || this.target instanceof EntityFortressBoss)
				this.setDead();
		}

		if(!this.isDead) {
			this.ticksInAir++;
			if(this.worldObj.isRemote) {
				double px = this.posX + this.worldObj.rand.nextFloat() * 0.25F;
				double py = this.posY + this.worldObj.rand.nextFloat() * 0.25F;
				double pz = this.posZ + this.worldObj.rand.nextFloat() * 0.25F;
				Vec3 vec = Vec3.createVectorHelper(px, py, pz).subtract(Vec3.createVectorHelper(this.posX + 0.125F, this.posY + 0.125F, this.posZ + 0.125F)).normalize();
				BLParticle.STEAM_PURIFIER.spawn(this.worldObj, px, py, pz, vec.xCoord * 0.05F, vec.yCoord * 0.05F, vec.zCoord * 0.05F, 1.0F);
			}
			if(this.target == null || this.target.isDead) {
				List<Entity> targetList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(16.0D, 16.0D, 16.0D));
				List<Entity> eligibleTargets = new ArrayList<Entity>();
				if(this.worldObj.rand.nextInt(4) > 0) {
					for(Entity e : targetList) {
						if(e instanceof EntityPlayer) {
							eligibleTargets.add((EntityPlayer)e);
						}
					}
				}
				if(eligibleTargets.isEmpty()) {
					for(Entity e : targetList) {
						if(e instanceof EntityWight == false) {
							eligibleTargets.add(e);
						}
					}
				}
				if(!eligibleTargets.isEmpty()) {
					this.target = eligibleTargets.get(this.worldObj.rand.nextInt(eligibleTargets.size()));
				}
			} 
			if(this.target != null && this.ticksInAir >= 10) {
				double dx = this.target.boundingBox.minX + (this.target.boundingBox.maxX - this.target.boundingBox.minX) / 2.0D - this.posX;
				double dy = this.target.boundingBox.minY + (this.target.boundingBox.maxY - this.target.boundingBox.minY) / 2.0D - this.posY;
				double dz = this.target.boundingBox.minZ + (this.target.boundingBox.maxZ - this.target.boundingBox.minZ) / 2.0D - this.posZ;
				double dist = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
				double speed = 0.075D;
				double maxSpeed = 0.8D;
				this.motionX += dx / dist * speed;
				this.motionY += dy / dist * speed;
				this.motionZ += dz / dist * speed;
				Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
				if(motion.lengthVector() > maxSpeed) {
					motion = motion.normalize();
					this.motionX = motion.xCoord * maxSpeed;
					this.motionY = motion.yCoord * maxSpeed;
					this.motionZ = motion.zCoord * maxSpeed;
				}
			}
			Vec3 currentPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 nextPos = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition hitObject = this.worldObj.rayTraceBlocks(currentPos, nextPos);
			currentPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			nextPos = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (hitObject != null) {
				nextPos = Vec3.createVectorHelper(hitObject.hitVec.xCoord, hitObject.hitVec.yCoord, hitObject.hitVec.zCoord);
			}
			Entity hitEntity = null;
			List hitEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(2.0D, 2.0D, 2.0D));
			double minDist = 0.0D;
			for (int i = 0; i < hitEntities.size(); ++i) {
				Entity entity1 = (Entity)hitEntities.get(i);
				if (entity1.canBeCollidedWith() && (this.ticksInAir >= 10)) {
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(currentPos, nextPos);
					if (movingobjectposition1 != null) {
						double d1 = currentPos.distanceTo(movingobjectposition1.hitVec);
						if (d1 < minDist || minDist == 0.0D) {
							hitEntity = entity1;
							minDist = d1;
						}
					}
				}
			}
			if (hitEntity != null) {
				hitObject = new MovingObjectPosition(hitEntity);
			}
			if (hitObject != null) {
				this.onImpact(hitObject);
			}
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		}

		super.onUpdate();
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float speed, float randMotion) {
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double)f2;
		y /= (double)f2;
		z /= (double)f2;
		x += this.rand.nextGaussian() * 0.007499999832361937D * (double)randMotion;
		y += this.rand.nextGaussian() * 0.007499999832361937D * (double)randMotion;
		z += this.rand.nextGaussian() * 0.007499999832361937D * (double)randMotion;
		x *= (double)speed;
		y *= (double)speed;
		z *= (double)speed;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, (double)f3) * 180.0D / Math.PI);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.setOwner(nbt.getString("ownerUUID"));
		this.strikes = nbt.getInteger("strikes");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setString("ownerUUID", this.getOwnerUUID());
		nbt.setInteger("strikes", this.strikes);
	}
}