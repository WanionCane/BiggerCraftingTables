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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.CraftResultBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.slot.BiggerCraftingSlot;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

public final class ContainerGiantCraftingTable extends ContainerBiggerCraftingTable
{
	private final InventoryCrafting craftingMatrix;
	private final IInventory craftingResult;

	public ContainerGiantCraftingTable(@Nonnull final TileEntityGiantCraftingTable tileEntityGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityGiantCraftingTable);
		craftingMatrix = new CraftingBiggerCraftingTable(this, 9);
		craftingResult = new CraftResultBiggerCraftingTable(tileEntityGiantCraftingTable, 81);
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(craftingMatrix, y * 9 + x, 8 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new BiggerCraftingSlot(this, craftingResult, craftingMatrix, 0, 183, 90));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 194 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 252));
		onCraftMatrixChanged(craftingMatrix);
	}

	@Override
	public void onCraftMatrixChanged(final IInventory inventory)
	{
		final IGiantRecipe giantRecipe = GiantRecipeRegistry.INSTANCE.findMatchingRecipe(craftingMatrix);
		craftingResult.setInventorySlotContents(0, giantRecipe != null ? giantRecipe.getOutput() : ItemStack.EMPTY);
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
			if (slot > 81) {
				if (!mergeItemStack(itemstack1, 0, 81, false))
					return ItemStack.EMPTY;
			} else if (slot == 81) {
				if (!mergeItemStack(itemstack1, 82, 118, true))
					return ItemStack.EMPTY;
				actualSlot.onSlotChange(itemstack1, itemstack);
			} else if (!mergeItemStack(itemstack1, 82, 118, true))
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
}