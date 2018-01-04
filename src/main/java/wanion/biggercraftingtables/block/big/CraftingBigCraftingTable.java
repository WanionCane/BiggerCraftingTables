package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class CraftingBigCraftingTable extends InventoryCrafting
{
	private final TileEntityBigCraftingTable tileEntityBigCraftingTable;
	private final Container container;

	public CraftingBigCraftingTable(@Nonnull final Container container, @Nonnull final TileEntityBigCraftingTable tileEntityBigCraftingTable)
	{
		super(container, 5, 5);
		this.tileEntityBigCraftingTable = tileEntityBigCraftingTable;
		this.container = container;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int slot)
	{
		return slot >= getSizeInventory() ? ItemStack.EMPTY : tileEntityBigCraftingTable.getStackInSlot(slot);
	}

	@Override
	@Nonnull
	public ItemStack getStackInRowAndColumn(final int row, final int column)
	{
		final int slot = row * 5 + column;
		return slot < 25 ? getStackInSlot(slot) : ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(final int slot, final int decrement)
	{
		final ItemStack stack = tileEntityBigCraftingTable.getStackInSlot(slot);
		if (!stack.isEmpty()) {
			ItemStack itemstack;
			if (stack.getCount() <= decrement) {
				itemstack = stack.copy();
				tileEntityBigCraftingTable.setInventorySlotContents(slot, ItemStack.EMPTY);
				container.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = stack.splitStack(decrement);
				container.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(final int slot, @Nonnull final ItemStack itemstack)
	{
		tileEntityBigCraftingTable.setInventorySlotContents(slot, itemstack);
		container.onCraftMatrixChanged(this);
	}
}