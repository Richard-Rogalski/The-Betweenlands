package thebetweenlands.herblore.aspects.list;

import thebetweenlands.herblore.aspects.IAspectType;

public class AspectYunugaz implements IAspectType {
	@Override
	public String getName() {
		return "Yunugaz";
	}

	@Override
	public String getType() {
		return "Wind";
	}

	@Override
	public String getDescription() {
		return "Magical property which relates to wind. Any combination with this effect can be related to the element wind.";
	}

	@Override
	public int getIconIndex() {
		return 13;
	}

	@Override
	public int getColor() {
		return 0xFF00FFBB;
	}
}
