package wanion.biggercraftingtables.block;

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

import javax.annotation.Nonnull;

public final class CraftResultBiggerCraftingTables implements IInventory
{
	private final TileEntityBiggerCraftingTables tileEntityBiggerCraftingTables;
	private final int slot;

	public CraftResultBiggerCraftingTables(@Nonnull final TileEntityBiggerCraftingTables tileEntityBiggerCraftingTables, final int slot)
	{
		this.tileEntityBiggerCraftingTables = tileEntityBiggerCraftingTables;
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
		return tileEntityBiggerCraftingTables.getStackInSlot(this.slot);
	}

	@Override
	public ItemStack decrStackSize(final int slot, int howMuch)
	{
		return tileEntityBiggerCraftingTables.decrStackSize(this.slot, howMuch);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemStack)
	{
		tileEntityBiggerCraftingTables.setInventorySlotContents(this.slot, itemStack);
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
		tileEntityBiggerCraftingTables.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCraftingTables.isUseableByPlayer(entityPlayer);
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack)
	{
		return tileEntityBiggerCraftingTables.isItemValidForSlot(slot, itemStack);
	}
}