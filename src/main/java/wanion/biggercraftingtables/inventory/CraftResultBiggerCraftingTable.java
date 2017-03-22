package wanion.biggercraftingtables.inventory;

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
import wanion.biggercraftingtables.block.TileEntityBiggerCraftingTable;

import javax.annotation.Nonnull;

public final class CraftResultBiggerCraftingTable implements IInventory
{
	private final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable;
	private final int slot;

	public CraftResultBiggerCraftingTable(@Nonnull final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable, final int slot)
	{
		this.tileEntityBiggerCraftingTable = tileEntityBiggerCraftingTable;
		this.slot = slot;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(final int slot)
	{
		return tileEntityBiggerCraftingTable.getStackInSlot(this.slot);
	}

	@Override
	public ItemStack decrStackSize(final int slot, int howMuch)
	{
		return tileEntityBiggerCraftingTable.decrStackSize(this.slot, howMuch);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemStack)
	{
		tileEntityBiggerCraftingTable.setInventorySlotContents(this.slot, itemStack);
	}

	@Override
	public String getInventoryName()
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		tileEntityBiggerCraftingTable.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCraftingTable.isUseableByPlayer(entityPlayer);
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack)
	{
		return tileEntityBiggerCraftingTable.isItemValidForSlot(slot, itemStack);
	}
}