package thebetweenlands.herblore.aspects;

import net.minecraft.nbt.NBTTagCompound;

public interface IAspectType {
	String getName();

	String getType();

	String getDescription();
	
	int getIconIndex();
	
	int getColor();
}
