package thebetweenlands.items.misc;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thebetweenlands.TheBetweenlands;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.manual.ManualManager;
import thebetweenlands.proxy.CommonProxy;

/**
 * Created by Bart on 8-8-2015.
 */
public class ItemManual extends Item {
	public ItemManual() {
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		player.openGui(TheBetweenlands.instance, CommonProxy.GUI_MANUAL, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return itemStack;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null && ManualManager.getFoundPages(player, BLItemRegistry.manualGuideBook) != null) {
			list.add("Current page: ");
			list.add("" + ManualManager.getCurrentPageNumber(BLItemRegistry.manualHL, player));
			list.add("Current category: ");
			if (ManualManager.getCurrentCategory(BLItemRegistry.manualGuideBook, player) != null)
				list.add(ManualManager.getCurrentCategory(BLItemRegistry.manualGuideBook, player).getCategoryNumber());
			list.addAll(ManualManager.getFoundPages(player, this));
		}
	}

}
