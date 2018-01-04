package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.DeadSlot;
import wanion.biggercraftingtables.inventory.ShapeSlot;
import wanion.biggercraftingtables.inventory.SpecialSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoHugeCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoHugeCraftingTable(@Nonnull TileEntityAutoHugeCraftingTable tileEntityAutoHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoHugeCraftingTable);
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new Slot(tileEntityAutoHugeCraftingTable, y * 7 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoHugeCraftingTable, 49 + (y * 7 + x), 139 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoHugeCraftingTable, 99, 274, 72));
		addSlotToContainer(new SpecialSlot(tileEntityAutoHugeCraftingTable, 98, 274, 100));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 158 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 216));
	}

	@Override
	@Nonnull
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 99) {
				if (!mergeItemStack(itemstack1, 0, 49, false))
					return ItemStack.EMPTY;
			} else if (slot < 49 || slot == 99) {
				if (!mergeItemStack(itemstack1, 100, 136, true))
					return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0)
				actualSlot.putStack(ItemStack.EMPTY);
			actualSlot.onSlotChanged();
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack slotClick(final int slot, final int mouseButton, final ClickType clickType, final EntityPlayer entityPlayer)
	{
		if (slot > 48 && slot < 98) {
			if (clickType == ClickType.QUICK_MOVE)
				return ItemStack.EMPTY;
			final ItemStack playerStack = entityPlayer.inventory.getItemStack();
			final Slot actualSlot = inventorySlots.get(slot);
			final boolean slotHasStack = actualSlot.getHasStack();
			if (slotHasStack && !playerStack.isEmpty()) {
				actualSlot.putStack(ItemStack.EMPTY);
				return ItemStack.EMPTY;
			} else if (playerStack.isEmpty()) {
				if (clickType == ClickType.PICKUP) {
					actualSlot.putStack(ItemStack.EMPTY);
					return ItemStack.EMPTY;
				} else if (slotHasStack && playerStack.isItemEqual(actualSlot.getStack())) {
					actualSlot.putStack(ItemStack.EMPTY);
					return ItemStack.EMPTY;
				}
				final ItemStack slotStack = playerStack.copy();
				slotStack.setCount(0);
				actualSlot.putStack(ItemStack.EMPTY);
				return slotStack;
			}
			return ItemStack.EMPTY;
		} else return super.slotClick(slot, mouseButton, clickType, entityPlayer);
	}
}