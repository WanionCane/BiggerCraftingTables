package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.DeadSlot;
import wanion.biggercraftingtables.inventory.ShapeSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoBigCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoBigCraftingTable(@Nonnull TileEntityAutoBigCraftingTable tileEntityAutoBiggerCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoBiggerCraftingTable);
		for (int y = 0; y < 5; y++)
			for (int x = 0; x < 5; x++)
				addSlotToContainer(new Slot(tileEntityAutoBiggerCraftingTable, y * 5 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 5; y++)
			for (int x = 0; x < 5; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoBiggerCraftingTable, 25 + (y * 5 + x), 103 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoBiggerCraftingTable, 51, 202, 54));
		addSlotToContainer(new Slot(tileEntityAutoBiggerCraftingTable, 50, 202, 82));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 122 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 180));
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = (Slot) this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 51) {
				if (!mergeItemStack(itemstack1, 0, 25, false))
					return null;
			} else if (slot < 25 || slot == 51) {
				if (!mergeItemStack(itemstack1, 52, 88, true))
					return null;
			}
			if (itemstack1.stackSize == 0)
				actualSlot.putStack(null);
			actualSlot.onSlotChanged();
		}
		return itemstack;
	}

	@Override
	public ItemStack slotClick(final int slot, final int mouseButton, final int modifier, final EntityPlayer entityPlayer)
	{
		if (slot > 24 && slot < 50) {
			if (modifier == 2)
				return null;
			final ItemStack playerStack = entityPlayer.inventory.getItemStack();
			final Slot actualSlot = (Slot) inventorySlots.get(slot);
			final boolean slotHasStack = actualSlot.getHasStack();
			if (slotHasStack && playerStack == null) {
				actualSlot.putStack(null);
				return null;
			} else if (playerStack != null) {
				if (modifier == 1) {
					actualSlot.putStack(null);
					return null;
				}
				final ItemStack slotStack = playerStack.copy();
				slotStack.stackSize = 0;
				actualSlot.putStack(slotStack);
				return slotStack;
			}
			return null;
		} else return super.slotClick(slot, mouseButton, modifier, entityPlayer);
	}
}