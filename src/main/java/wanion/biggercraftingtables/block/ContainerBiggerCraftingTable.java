package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class ContainerBiggerCraftingTable extends Container
{
	private final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable;

	public ContainerBiggerCraftingTable(@Nonnull final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable)
	{
		this.tileEntityBiggerCraftingTable = tileEntityBiggerCraftingTable;
	}

	@Override
	public boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}

	protected class CraftingBiggerCraftingTable extends InventoryCrafting
	{
		private final int root, square;

		public CraftingBiggerCraftingTable(@Nonnull final ContainerBiggerCraftingTable container, final int root)
		{
			super(container, root, root);
			square = (this.root = root) * root;
		}

		@Nonnull
		@Override
		public ItemStack getStackInSlot(final int slot)
		{
			return slot >= getSizeInventory() ? ItemStack.EMPTY : tileEntityBiggerCraftingTable.getStackInSlot(slot);
		}

		@Nonnull
		@Override
		public ItemStack getStackInRowAndColumn(final int row, final int column)
		{
			final int slot = row * root + column;
			return slot < square ? getStackInSlot(slot) : ItemStack.EMPTY;
		}

		@Nonnull
		@Override
		public ItemStack decrStackSize(final int slot, final int decrement)
		{
			final ItemStack stack = tileEntityBiggerCraftingTable.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				ItemStack itemstack;
				if (stack.getCount() <= decrement) {
					itemstack = stack.copy();
					tileEntityBiggerCraftingTable.setInventorySlotContents(slot, ItemStack.EMPTY);
					onCraftMatrixChanged(this);
					return itemstack;
				} else {
					itemstack = stack.splitStack(decrement);
					onCraftMatrixChanged(this);
					return itemstack;
				}
			} else {
				return ItemStack.EMPTY;
			}
		}

		@Override
		public void setInventorySlotContents(final int slot, @Nonnull final ItemStack itemstack)
		{
			tileEntityBiggerCraftingTable.setInventorySlotContents(slot, itemstack);
			onCraftMatrixChanged(this);
		}
	}
}