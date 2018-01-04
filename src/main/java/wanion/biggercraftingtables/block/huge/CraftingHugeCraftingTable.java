package wanion.biggercraftingtables.block.huge;

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

public final class CraftingHugeCraftingTable extends InventoryCrafting
{
	private final TileEntityHugeCraftingTable tileEntityHugeCraftingTable;
	private final Container container;

	public CraftingHugeCraftingTable(@Nonnull final Container container, @Nonnull final TileEntityHugeCraftingTable tileEntityBigCraftingTable)
	{
		super(container, 7, 7);
		this.tileEntityHugeCraftingTable = tileEntityBigCraftingTable;
		this.container = container;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int slot)
	{
		return slot >= getSizeInventory() ? ItemStack.EMPTY : tileEntityHugeCraftingTable.getStackInSlot(slot);
	}

	@Override
	@Nonnull
	public ItemStack getStackInRowAndColumn(final int row, final int column)
	{
		final int slot = row * 7 + column;
		return slot < 49 ? getStackInSlot(slot) : ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(final int slot, final int decrement)
	{
		final ItemStack stack = tileEntityHugeCraftingTable.getStackInSlot(slot);
		if (!stack.isEmpty()) {
			ItemStack itemstack;
			if (stack.getCount() <= decrement) {
				itemstack = stack.copy();
				tileEntityHugeCraftingTable.setInventorySlotContents(slot, ItemStack.EMPTY);
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
		tileEntityHugeCraftingTable.setInventorySlotContents(slot, itemstack);
		container.onCraftMatrixChanged(this);
	}
}