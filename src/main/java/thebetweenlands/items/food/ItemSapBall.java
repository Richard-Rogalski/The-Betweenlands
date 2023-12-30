package thebetweenlands.items.food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import thebetweenlands.manual.IManualEntryItem;

public class ItemSapBall extends ItemFood implements IDecayFood, IManualEntryItem {
	public ItemSapBall() {
		super(0, 0f, false);
		setAlwaysEdible();
	}

	public int getDecayHealAmount(ItemStack stack) {
		return 2;
	}

	@Override
	public String manualName(int meta) {
		return "sapBall";
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
