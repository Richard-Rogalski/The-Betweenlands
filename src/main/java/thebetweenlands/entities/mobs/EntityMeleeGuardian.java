package thebetweenlands.entities.mobs;

import net.minecraft.world.World;

public class EntityMeleeGuardian extends EntityTempleGuardian implements IEntityBL {

	public EntityMeleeGuardian(World worldObj) {
		super(worldObj);
		this.setSize(1.0F, 2.5F);
	}

	@Override
	protected String getLivingSound() {
		if (!getActive()) return null;
		return "thebetweenlands:templeGuardianMeleeLiving";
	}

	@Override
	public String pageName() {
		return "meleeGuardian";
	}
}