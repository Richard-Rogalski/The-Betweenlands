package thebetweenlands.entities.entityAI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import thebetweenlands.entities.mobs.EntityDarkDruid;

public class EntityAINearestAttackableTargetDruid extends EntityAINearestAttackableTarget {
	private EntityDarkDruid druid;

	public EntityAINearestAttackableTargetDruid(EntityDarkDruid druid) {
		super(druid, EntityPlayer.class, 0, true);
		this.druid = druid;
	}

	@Override
	protected boolean isSuitableTarget(EntityLivingBase target, boolean ignoreDisabledDamage) {
		return super.isSuitableTarget(target, ignoreDisabledDamage) && target.onGround && druid.getAttackCounter() == 0;
	}

	@Override
	public boolean continueExecuting() {
		Entity target = druid.getAttackTarget();
		return target != null && target.isEntityAlive() && (druid.getAttackCounter() != 0 || target.onGround);
	}

	@Override
	protected double getTargetDistance() {
		return 7;
	}
}
