package wanion.biggercraftingtables.inventory.slot;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;

import javax.annotation.Nonnull;

public final class BiggerCraftingSlot extends SpecialSlot
{
	private final ContainerBiggerCraftingTable containerBiggerCraftingTable;
	private final IInventory craftingMatrix;

	public BiggerCraftingSlot(@Nonnull final ContainerBiggerCraftingTable containerBiggerCraftingTable, @Nonnull final IInventory craftingResult, @Nonnull final IInventory craftingMatrix, final int id, final int x, final int y)
	{
		super(craftingResult, id, x, y);
		this.containerBiggerCraftingTable = containerBiggerCraftingTable;
		this.craftingMatrix = craftingMatrix;
	}

	@Override
	@Nonnull
	public ItemStack onTake(final EntityPlayer thePlayer, @Nonnull final ItemStack stack)
	{
		onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
	}

	@Override
	protected void onCrafting(@Nonnull final ItemStack stack)
	{
		for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
			final ItemStack slotStack = craftingMatrix.getStackInSlot(i);
			if (slotStack.isEmpty())
				continue;
			slotStack.setCount(slotStack.getCount() - 1);
			if (slotStack.getCount() == 0)
				craftingMatrix.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		containerBiggerCraftingTable.onCraftMatrixChanged(craftingMatrix);
	}
}