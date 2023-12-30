package thebetweenlands.herblore.aspects.list;

import net.minecraft.nbt.NBTTagCompound;
import thebetweenlands.herblore.aspects.IAspectType;

public class AspectArmaniis implements IAspectType {
	@Override
	public String getName() {
		return "Armaniis";
	}

	@Override
	public String getType() {
		return "Desire";
	}

	@Override
	public String getDescription() {
		return "Has effect on the desires of a mob or the player. Could be useful for food, but also things like trading or corrupting the desire.";
	}

	@Override
	public int getIconIndex() {
		return 0;
	}

	@Override
	public int getColor() {
		return 0xFFFFCC00;
	}
}
