package thebetweenlands.items.tools;

import thebetweenlands.creativetabs.BLCreativeTabs;
import net.minecraft.item.Item;
import thebetweenlands.manual.IManualEntryItem;

public class ItemWeedwoodBucketRubber extends Item implements IManualEntryItem {
	public ItemWeedwoodBucketRubber() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName("thebetweenlands.bucketOfRubber");
		this.setTextureName("thebetweenlands:weedwoodBucketRubber");
		this.setCreativeTab(BLCreativeTabs.items);
	}

	@Override
	public String manualName(int meta) {
		return "bucketOfRubber";
	}

	@Override
	public Item getItem() {
		return this;
	}

	@Override
	public int[] recipeType(int meta) {
		return new int[0];
	}

	@Override
	public int metas() {
		return 0;
	}
}
