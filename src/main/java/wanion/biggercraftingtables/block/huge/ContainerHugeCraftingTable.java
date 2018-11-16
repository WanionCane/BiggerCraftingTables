package wanion.biggercraftingtables.block.huge;

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
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry.IHugeRecipe;

public final class ContainerHugeCraftingTable extends ContainerBiggerCraftingTable
{
	private final InventoryCrafting craftingMatrix;
	private final IInventory craftingResult;

	public ContainerHugeCraftingTable(@Nonnull final TileEntityHugeCraftingTable tileEntityHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityHugeCraftingTable);
		craftingMatrix = new CraftingBiggerCraftingTable(this, 7);
		craftingResult = new CraftResultBiggerCraftingTable(tileEntityHugeCraftingTable, 49);
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new Slot(craftingMatrix, y * 7 + x, 8 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new BiggerCraftingSlot(this, craftingResult, craftingMatrix, 0, 147, 72));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 158 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 216));
		initVars();
		onCraftMatrixChanged(craftingMatrix);
	}

	@Override
	public void onCraftMatrixChanged(final IInventory inventory)
	{
		final IHugeRecipe hugeRecipe = HugeRecipeRegistry.INSTANCE.findMatchingRecipe(craftingMatrix);
		craftingResult.setInventorySlotContents(0, hugeRecipe != null ? hugeRecipe.getOutput() : ItemStack.EMPTY);
	}
}