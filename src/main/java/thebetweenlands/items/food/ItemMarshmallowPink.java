package thebetweenlands.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import thebetweenlands.manual.IManualEntryItem;

public class ItemMarshmallowPink extends ItemFood implements IManualEntryItem
{
    public ItemMarshmallowPink() {
        super(4, 10F, false);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);

        if( player != null ) {
            player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 400, 1));
        }
    }

    @Override
    public String manualName(int meta) {
        return "marshmallowPink";
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
