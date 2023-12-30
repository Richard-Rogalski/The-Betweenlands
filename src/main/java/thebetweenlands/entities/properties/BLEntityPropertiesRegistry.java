package thebetweenlands.entities.properties;

import thebetweenlands.entities.properties.list.EntityPropertiesCircleGem;
import thebetweenlands.entities.properties.list.EntityPropertiesDecay;
import thebetweenlands.entities.properties.list.EntityPropertiesFlight;
import thebetweenlands.entities.properties.list.EntityPropertiesFood;
import thebetweenlands.entities.properties.list.EntityPropertiesPortal;
import thebetweenlands.entities.properties.list.EntityPropertiesRingInput;
import thebetweenlands.entities.properties.list.equipment.EntityPropertiesEquipment;
import thebetweenlands.entities.properties.list.recruitment.EntityPropertiesRecruit;
import thebetweenlands.entities.properties.list.recruitment.EntityPropertiesRecruiter;
import thebetweenlands.manual.EntityPropertiesManual;

public class BLEntityPropertiesRegistry {
	public static final EntityPropertiesHandler HANDLER = new EntityPropertiesHandler();

	static {
		HANDLER.registerProperties(EntityPropertiesPortal.class);
		HANDLER.registerProperties(EntityPropertiesDecay.class);
		HANDLER.registerProperties(EntityPropertiesManual.class);
		HANDLER.registerProperties(EntityPropertiesCircleGem.class);
		HANDLER.registerProperties(EntityPropertiesFood.class);
		HANDLER.registerProperties(EntityPropertiesEquipment.class);
		HANDLER.registerProperties(EntityPropertiesRecruit.class);
		HANDLER.registerProperties(EntityPropertiesRecruiter.class);
		HANDLER.registerProperties(EntityPropertiesFlight.class);
		HANDLER.registerProperties(EntityPropertiesRingInput.class);
	}
}
