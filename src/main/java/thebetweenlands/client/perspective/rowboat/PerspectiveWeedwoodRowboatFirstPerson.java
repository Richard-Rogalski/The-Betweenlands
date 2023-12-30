package thebetweenlands.client.perspective.rowboat;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import thebetweenlands.client.input.WeedwoodRowboatHandler;
import thebetweenlands.client.perspective.Perspective;
import thebetweenlands.client.perspective.PerspectiveFirstPerson;
import thebetweenlands.utils.MathUtils;
import thebetweenlands.utils.confighandler.ConfigHandler;

public class PerspectiveWeedwoodRowboatFirstPerson extends PerspectiveFirstPerson {
	@Override
	protected boolean canCycleTo(Perspective perspective) {
		return perspective == WeedwoodRowboatHandler.THIRD_PERSON_PERSPECTIVE;
	}

	@Override
	protected boolean canCycleFrom(Perspective perspective) {
		return WeedwoodRowboatHandler.INSTANCE.isPlayerInRowboat();
	}

	@Override
	protected void onCycleTo() {
		ConfigHandler.rowboatView = false;
		ConfigHandler.INSTANCE.save();
	}

	@Override
	protected void applyMovement(Entity entity, float x, float y) {
		if (entity.ridingEntity == null) {
			return;
		}
		final float yawLimit = 135;
		final float yawResistance = 45;
		float currentPitch = entity.rotationPitch;
		float currentYaw = MathHelper.wrapAngleTo180_float(entity.rotationYaw);
		float rowboatYawOffset = MathHelper.wrapAngleTo180_float(entity.ridingEntity.rotationYaw - 90);
		float relativeYaw = MathHelper.wrapAngleTo180_float(currentYaw - rowboatYawOffset);
		float scaleX = 0.15F;
		float scaleY = 0.15F;
		float distanceToLimit = Math.abs(yawLimit - Math.abs(relativeYaw));
		if (distanceToLimit < yawResistance) {
			if (x * relativeYaw > 0) {
				scaleX *= distanceToLimit / yawResistance;
			}
			scaleY = scaleY * distanceToLimit / yawResistance * 0.7F + scaleY * 0.3F;
		}
		entity.rotationYaw += x * scaleX;
		entity.rotationPitch -= y * scaleY;
		final float pitchLimit = 90;
		if (entity.rotationPitch > pitchLimit) {
			entity.rotationPitch = pitchLimit;
		}
		if (entity.rotationPitch < -pitchLimit) {
			entity.rotationPitch = -pitchLimit;
		}
		entity.rotationYaw -= rowboatYawOffset;
		entity.rotationYaw = MathHelper.wrapAngleTo180_float(entity.rotationYaw);
		if (entity.rotationYaw > yawLimit) {
			entity.rotationYaw = yawLimit;
		}
		if (entity.rotationYaw < -yawLimit) {
			entity.rotationYaw = -yawLimit;
		}
		entity.rotationYaw += rowboatYawOffset;
		entity.prevRotationYaw = MathUtils.adjustAngleForInterpolation(entity.rotationYaw, entity.prevRotationYaw);
	}

	@Override
	protected Perspective getPerspectiveForOpaqueBlockView() {
		return this;
	}
}
