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
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.slot.DeadSlot;
import wanion.biggercraftingtables.inventory.slot.ShapeSlot;
import wanion.biggercraftingtables.inventory.slot.SpecialSlot;

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
		addSlotToContainer(new SpecialSlot(tileEntityAutoBiggerCraftingTable, 50, 202, 82));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 35 + (18 * x), 122 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 35 + (18 * i), 180));
	}

	@Nonnull
	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 51) {
				if (!mergeItemStack(itemstack1, 0, 25, false))
					return ItemStack.EMPTY;
			} else if (slot < 25 || slot == 51) {
				if (!mergeItemStack(itemstack1, 52, 88, true))
					return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0)
				actualSlot.putStack(ItemStack.EMPTY);
			actualSlot.onSlotChanged();
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public ItemStack slotClick(final int slot, final int mouseButton, final ClickType clickType, final EntityPlayer entityPlayer)
	{
		if (slot > 24 && slot < 50) {
			final Slot actualSlot = inventorySlots.get(slot);
			if (clickType == ClickType.QUICK_MOVE) {
				actualSlot.putStack(ItemStack.EMPTY);
			} else if (clickType == ClickType.PICKUP) {
				final ItemStack playerStack = entityPlayer.inventory.getItemStack();
				final boolean slotHasStack = actualSlot.getHasStack();
				if (!playerStack.isEmpty() && !slotHasStack) {
					final ItemStack newSlotStack = playerStack.copy();
					newSlotStack.setCount(1);
					actualSlot.putStack(newSlotStack);
				} else if (playerStack.isEmpty() && slotHasStack || !playerStack.isEmpty() && playerStack.isItemEqual(actualSlot.getStack())) {
					actualSlot.putStack(ItemStack.EMPTY);
				}
			}
			tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			return ItemStack.EMPTY;
		} else if (slot == 50) {
			if (inventorySlots.get(slot).getHasStack()) {
				clearShape(tileEntityAutoBiggerCraftingTable.half, tileEntityAutoBiggerCraftingTable.full);
				tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			}
			return ItemStack.EMPTY;
		} else return super.slotClick(slot, mouseButton, clickType, entityPlayer);
	}
}