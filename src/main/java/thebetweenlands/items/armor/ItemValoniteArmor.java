package thebetweenlands.items.armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thebetweenlands.gemcircle.CircleGem;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.BLMaterial;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;
import thebetweenlands.manual.IManualEntryItem;

public class ItemValoniteArmor extends ItemArmorBL implements IManualEntryItem {

	public ItemValoniteArmor(int armorType) {
		super(BLMaterial.armorValonite, 2, armorType, "thebetweenlands:textures/armour/valonite1.png", "thebetweenlands:textures/armour/valonite2.png");
		String itemTexture;
		switch(armorType) {
		default:
		case 0:
			itemTexture = "thebetweenlands:valoniteHelmet";
			break;
		case 1:
			itemTexture = "thebetweenlands:valoniteChestplate";
			break;
		case 2:
			itemTexture = "thebetweenlands:valoniteLeggings";
			break;
		case 3:
			itemTexture = "thebetweenlands:valoniteBoots";
			break;
		}
		this.setTextureName(itemTexture);
		this.setGemTextures(CircleGem.AQUA, itemTexture + "AquaGem", "thebetweenlands:textures/armour/valonite1AquaGem.png", "thebetweenlands:textures/armour/valonite2AquaGem.png");
		this.setGemTextures(CircleGem.CRIMSON, itemTexture + "CrimsonGem", "thebetweenlands:textures/armour/valonite1CrimsonGem.png", "thebetweenlands:textures/armour/valonite2CrimsonGem.png");
		this.setGemTextures(CircleGem.GREEN, itemTexture + "GreenGem", "thebetweenlands:textures/armour/valonite1GreenGem.png", "thebetweenlands:textures/armour/valonite2GreenGem.png");
	}

	@Override
	protected boolean isLeggings(ItemStack stack) {
		return stack.getItem() == BLItemRegistry.valoniteLeggings;
	}

	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == BLItemRegistry.itemsGeneric && material.getItemDamage() == EnumItemGeneric.VALONITE_SHARD.id;
	}

	@Override
	public String manualName(int meta) {
		return "valoniteArmor";
	}

	@Override
	public Item getItem() {
		return this;
	}

	@Override
	public int[] recipeType(int meta) {
		return new int[]{2};
	}

	@Override
	public int metas() {
		return 0;
	}
}