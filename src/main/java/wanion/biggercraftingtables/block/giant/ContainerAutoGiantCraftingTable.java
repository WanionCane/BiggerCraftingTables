package wanion.biggercraftingtables.block.giant;

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

public final class ContainerAutoGiantCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoGiantCraftingTable(@Nonnull TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoGiantCraftingTable);
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(tileEntityAutoGiantCraftingTable, y * 9 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoGiantCraftingTable, 81 + (y * 9 + x), 175 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoGiantCraftingTable, 163, 346, 90));
		addSlotToContainer(new SpecialSlot(tileEntityAutoGiantCraftingTable, 162, 346, 118));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 107 + (18 * x), 194 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 107 + (18 * i), 252));
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
			if (slot > 163) {
				if (!mergeItemStack(itemstack1, 0, 81, false))
					return ItemStack.EMPTY;
			} else if (slot < 81 || slot == 163) {
				if (!mergeItemStack(itemstack1, 164, 200, true))
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
		if (slot > 80 && slot < 162) {
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
		} else if (slot == 162) {
			if (inventorySlots.get(slot).getHasStack()) {
				clearShape(tileEntityAutoBiggerCraftingTable.half, tileEntityAutoBiggerCraftingTable.full);
				tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			}
			return ItemStack.EMPTY;
		} else return super.slotClick(slot, mouseButton, clickType, entityPlayer);
	}
}