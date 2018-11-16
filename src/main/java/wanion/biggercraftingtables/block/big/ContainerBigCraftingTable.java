package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.CraftResultBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.slot.BiggerCraftingSlot;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.big.BigRecipeRegistry.IBigRecipe;

public final class ContainerBigCraftingTable extends ContainerBiggerCraftingTable
{
	private final InventoryCrafting craftingMatrix;
	private final IInventory craftingResult;

	public ContainerBigCraftingTable(@Nonnull final TileEntityBigCraftingTable tileEntityBigCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityBigCraftingTable);
		craftingMatrix = new CraftingBiggerCraftingTable(this, 5);
		craftingResult = new CraftResultBiggerCraftingTable(tileEntityBigCraftingTable, 25);
		for (int y = 0; y < 5; y++)
			for (int x = 0; x < 5; x++)
				addSlotToContainer(new Slot(craftingMatrix, y * 5 + x, 44 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new BiggerCraftingSlot(this, craftingResult, craftingMatrix, 0, 147, 54));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 122 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 180));
		initVars();
		onCraftMatrixChanged(craftingMatrix);
	}

	@Override
	public void onCraftMatrixChanged(final IInventory inventory)
	{
		final IBigRecipe IBigRecipe = BigRecipeRegistry.INSTANCE.findMatchingRecipe(craftingMatrix);
		craftingResult.setInventorySlotContents(0, IBigRecipe != null ? IBigRecipe.getOutput() : ItemStack.EMPTY);
	}
}