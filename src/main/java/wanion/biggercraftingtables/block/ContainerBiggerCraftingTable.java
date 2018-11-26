package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.inventory.CraftResultBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.slot.BiggerCraftingSlot;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;

public abstract class ContainerBiggerCraftingTable<R extends IAdvancedRecipe> extends Container
{
	private final InventoryCrafting craftingMatrix;
	private final IInventory craftingResult;
	private final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable;
	private int playerInventoryEnds, playerInventoryStarts, result;

	public ContainerBiggerCraftingTable(final int root, final int inventoryStartsX, final int inventoryStartsY, final int playerStartsX, final int playerStartsY, final int resultX, final int resultY, @Nonnull final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		this.tileEntityBiggerCraftingTable = tileEntityBiggerCraftingTable;
		craftingMatrix = new CraftingBiggerCraftingTable(this, root);
		craftingResult = new CraftResultBiggerCraftingTable(tileEntityBiggerCraftingTable, root * root);
		for (int y = 0; y < root; y++)
			for (int x = 0; x < root; x++)
				addSlotToContainer(new Slot(craftingMatrix, y * root + x, inventoryStartsX + (18 * x), inventoryStartsY + (18 * y)));
		addSlotToContainer(new BiggerCraftingSlot(this, craftingResult, craftingMatrix, 0, resultX, resultY));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, playerStartsX + (18 * x), playerStartsY + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, playerStartsX + (18 * i), playerStartsY + 58));
		playerInventoryEnds = inventorySlots.size();
		playerInventoryStarts = playerInventoryEnds - 36;
		result = playerInventoryStarts - 1;
		onCraftMatrixChanged(craftingMatrix);
	}

	@Nonnull
	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > result) {
				if (!mergeItemStack(itemstack1, 0, result, false))
					return ItemStack.EMPTY;
			} else if (slot == result) {
				if (!mergeItemStack(itemstack1, playerInventoryStarts, playerInventoryEnds, true))
					return ItemStack.EMPTY;
				actualSlot.onSlotChange(itemstack1, itemstack);
			} else if (!mergeItemStack(itemstack1, playerInventoryStarts, playerInventoryEnds, true))
				return ItemStack.EMPTY;
			if (itemstack1.getCount() == 0)
				actualSlot.putStack(ItemStack.EMPTY);
			else
				actualSlot.onSlotChanged();
			if (itemstack1.getCount() != itemstack.getCount())
				actualSlot.onTake(entityPlayer, itemstack1);
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}

	@Override
	public final void onCraftMatrixChanged(final IInventory inventory)
	{
		final R recipe = getRecipeRegistry().findMatchingRecipe(craftingMatrix);
		craftingResult.setInventorySlotContents(0, recipe != null ? recipe.getOutput() : ItemStack.EMPTY);
	}

	@Override
	public final boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}

	@Nonnull
	public abstract AbstractRecipeRegistry<R> getRecipeRegistry();

	private class CraftingBiggerCraftingTable extends InventoryCrafting
	{
		private final int root, square;

		private CraftingBiggerCraftingTable(@Nonnull final ContainerBiggerCraftingTable container, final int root)
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