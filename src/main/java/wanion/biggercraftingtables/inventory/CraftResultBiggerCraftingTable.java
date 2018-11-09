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
import net.minecraft.util.text.ITextComponent;
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
	public boolean isEmpty()
	{
		return tileEntityBiggerCraftingTable.isEmpty();
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(final int slot)
	{
		return tileEntityBiggerCraftingTable.getStackInSlot(this.slot);
	}

	@Nonnull
	@Override
	public ItemStack decrStackSize(final int slot, int howMuch)
	{
		return tileEntityBiggerCraftingTable.decrStackSize(this.slot, howMuch);
	}

	@Nonnull
	@Override
	public ItemStack removeStackFromSlot(final int index)
	{
		return tileEntityBiggerCraftingTable.removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(final int slot, @Nonnull final ItemStack itemStack)
	{
		tileEntityBiggerCraftingTable.setInventorySlotContents(this.slot, itemStack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return tileEntityBiggerCraftingTable.getInventoryStackLimit();
	}

	@Override
	public void markDirty()
	{
		tileEntityBiggerCraftingTable.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}

	@Override
	public void openInventory(@Nonnull final EntityPlayer player) {}

	@Override
	public void closeInventory(@Nonnull final EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(final int slot, @Nonnull final ItemStack itemStack)
	{
		return tileEntityBiggerCraftingTable.isItemValidForSlot(slot, itemStack);
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear() {}

	@Nonnull
	@Override
	public String getName()
	{
		return tileEntityBiggerCraftingTable.getName();
	}

	@Override
	public boolean hasCustomName()
	{
		return tileEntityBiggerCraftingTable.hasCustomName();
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return tileEntityBiggerCraftingTable.getDisplayName();
	}
}