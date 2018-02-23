package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import wanion.lib.common.Util;

import javax.annotation.Nonnull;

public abstract class TileEntityBiggerCraftingTable extends TileEntity implements ISidedInventory
{
	private final ItemStack[] slots = new ItemStack[getSizeInventory()];

	public TileEntityBiggerCraftingTable()
	{
		Util.fillArray(slots, ItemStack.EMPTY);
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket()
	{
		final NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
	}

	@Override
	public final void onDataPacket(final NetworkManager networkManager, final SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public final void readFromNBT(final NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		readCustomNBT(nbtTagCompound);
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		writeCustomNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	void readCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = nbtTagCompound.getTagList("Contents", 10);
		final int max = getSizeInventory() - 1;
		for (int i = 0; i < max; i++)
			setInventorySlotContents(i, ItemStack.EMPTY);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			final NBTTagCompound slotCompound = nbtTagList.getCompoundTagAt(i);
			final int slot = slotCompound.getShort("Slot");
			if (slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, new ItemStack(slotCompound));
		}
	}

	NBTTagCompound writeCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = new NBTTagList();
		final int max = getSizeInventory() - 1;
		for (int i = 0; i < max; i++) {
			final ItemStack itemStack = getStackInSlot(i);
			if (itemStack.isEmpty())
				continue;
			final NBTTagCompound slotCompound = new NBTTagCompound();
			slotCompound.setShort("Slot", (short) i);
			nbtTagList.appendTag(itemStack.writeToNBT(slotCompound));
		}
		nbtTagCompound.setTag("Contents", nbtTagList);
		return nbtTagCompound;
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(final int slot)
	{
		return slots[slot];
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(final int slot, final int howMuch)
	{
		final ItemStack slotStack = slots[slot];
		if (slotStack.isEmpty())
			return ItemStack.EMPTY;
		final ItemStack newStack = slotStack.copy();
		newStack.setCount(howMuch);
		slotStack.setCount(slotStack.getCount() - howMuch);
		if ((slotStack.getCount()) == 0)
			slots[slot] = ItemStack.EMPTY;
		return newStack;
	}

	@Override
	@Nonnull
	public ItemStack removeStackFromSlot(final int index)
	{
		final ItemStack itemStack = slots[index];
		slots[index] = ItemStack.EMPTY;
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(final int slot, @Nonnull final ItemStack itemStack)
	{
		slots[slot] = itemStack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(@Nonnull final EntityPlayer entityPlayer)
	{
		return world.getTileEntity(pos) == this && entityPlayer.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) getPos().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(@Nonnull final EntityPlayer player) {}

	@Override
	public void closeInventory(@Nonnull final EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(final int slot, @Nonnull final ItemStack itemStack)
	{
		return true;
	}

	@Override
	public int getField(final int id)
	{
		return 0;
	}

	@Override
	public void setField(final int id, final int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear() { }

	@Override
	@Nonnull
	public int[] getSlotsForFace(@Nonnull final EnumFacing side)
	{
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, @Nonnull final ItemStack itemStackIn, @Nonnull final EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(final int index, @Nonnull final ItemStack stack, @Nonnull final EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}
}