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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.CraftResultBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.huge.HugeRecipe;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;

import javax.annotation.Nonnull;

public final class ContainerHugeCraftingTable extends ContainerBiggerCraftingTable
{
	private final InventoryCrafting craftingMatrix;
	private final IInventory craftingResult;

	public ContainerHugeCraftingTable(@Nonnull final TileEntityHugeCraftingTable tileEntityHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityHugeCraftingTable);
		craftingMatrix = new CraftingHugeCraftingTable(this, tileEntityHugeCraftingTable);
		craftingResult = new CraftResultBiggerCraftingTable(tileEntityHugeCraftingTable, 49);
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new Slot(craftingMatrix, y * 7 + x, 8 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, craftingMatrix, craftingResult, 0, 147, 72));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 158 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 216));
		onCraftMatrixChanged(craftingMatrix);
	}

	@Override
	public void onCraftMatrixChanged(final IInventory inventory)
	{
		final HugeRecipe hugeRecipe = HugeRecipeRegistry.instance.findMatchingRecipe(craftingMatrix);
		craftingResult.setInventorySlotContents(0, hugeRecipe != null ? hugeRecipe.getOutput() : null);
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = (Slot) inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > 49) {
				if (!mergeItemStack(itemstack1, 0, 49, false))
					return null;
			} else if (slot == 49) {
				if (!mergeItemStack(itemstack1, 50, 86, true))
					return null;
				actualSlot.onSlotChange(itemstack1, itemstack);
			} else if (!mergeItemStack(itemstack1, 50, 86, true))
				return null;
			if (itemstack1.stackSize == 0)
				actualSlot.putStack(null);
			else
				actualSlot.onSlotChanged();
			if (itemstack1.stackSize != itemstack.stackSize)
				actualSlot.onPickupFromSlot(entityPlayer, itemstack1);
		}
		return itemstack;
	}
}