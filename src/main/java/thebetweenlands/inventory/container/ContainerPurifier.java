package thebetweenlands.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;
import thebetweenlands.tileentities.TileEntityPurifier;

public class ContainerPurifier extends Container {

	protected TileEntityPurifier purifier;

	public ContainerPurifier(InventoryPlayer inventory, TileEntityPurifier tileentity) {
		purifier = tileentity;

		addSlotToContainer(new Slot(tileentity, 0, 61, 54));
		addSlotToContainer(new Slot(tileentity, 1, 61, 14));
		addSlotToContainer(new Slot(tileentity, 2, 121, 34));
		
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		for (int i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			if (slotIndex > 2) {
				if (stack1.getItem() == BLItemRegistry.itemsGeneric && stack1.getItemDamage() == EnumItemGeneric.SULFUR.id) {
					if (!mergeItemStack(stack1, 0, 1, false))
						return null;
				} else if (!mergeItemStack(stack1, 1, 2, true))
						return null;
			} else if (!mergeItemStack(stack1, 3, inventorySlots.size(), false))
				return null;
			if (stack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
			if (stack1.stackSize != stack.stackSize)
				slot.onPickupFromSlot(player, stack1);
			else
				return null;
		}
		return stack;
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafter) {
		super.addCraftingToCrafters(crafter);
		purifier.sendGUIData(this, crafter);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : crafters)
			purifier.sendGUIData(this, (ICrafting) crafter);
	}

	@Override
	public void updateProgressBar(int id, int value) {
		purifier.getGUIData(id, value);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
