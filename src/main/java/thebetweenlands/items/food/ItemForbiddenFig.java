package thebetweenlands.items.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thebetweenlands.manual.IManualEntryItem;

public class ItemForbiddenFig extends ItemFood implements IDecayFood, IManualEntryItem {
	public ItemForbiddenFig() {
		super(20, 5.0F, false);
	}

	public int getDecayHealAmount(ItemStack stack) {
		return 20;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("fig.tooltip"));
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);

		if(player != null) {
			if(world.isRemote) {
				player.addChatMessage(new ChatComponentTranslation("chat.item.forbiddenfig"));
				world.playSoundAtEntity(player, "thebetweenlands:fig", 0.7F, 0.8F);
			} else {
				player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 1200, 1));
				player.addPotionEffect(new PotionEffect(Potion.weakness.getId(), 1200, 1));			 
			}
		}
	}

	@Override
	public String manualName(int meta) {
		return "forbiddenFig";
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