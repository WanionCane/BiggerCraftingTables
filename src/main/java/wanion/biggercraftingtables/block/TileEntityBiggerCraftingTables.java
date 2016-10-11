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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBiggerCraftingTables extends TileEntity implements IInventory
{
	private final ItemStack[] slots;

	public TileEntityBiggerCraftingTables()
	{
		slots = new ItemStack[getSizeInventory()];
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public final Packet getDescriptionPacket()
	{
		final NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 3, nbttagcompound);
	}

	@Override
	public final void onDataPacket(final NetworkManager networkManager, final S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public final void readFromNBT(final NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		readCustomNBT(nbtTagCompound);
	}

	@Override
	public final void writeToNBT(final NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		writeCustomNBT(nbtTagCompound);
	}

	public void readCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = nbtTagCompound.getTagList("Contents", 10);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			final NBTTagCompound slotCompound = nbtTagList.getCompoundTagAt(i);
			final int slot = slotCompound.getShort("Slot");
			if (slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotCompound));
		}
	}

	public NBTTagCompound writeCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < getSizeInventory() - 1; i++) {
			final ItemStack itemStack = getStackInSlot(i);
			if (itemStack != null) {
				final NBTTagCompound slotCompound = new NBTTagCompound();
				slotCompound.setShort("Slot", (short) i);
				nbtTagList.appendTag(itemStack.writeToNBT(slotCompound));
			}
		}
		nbtTagCompound.setTag("Contents", nbtTagList);
		return nbtTagCompound;
	}

	@Override
	public ItemStack getStackInSlot(final int slot)
	{
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int howMuch)
	{
		final ItemStack slotStack = slots[slot];
		if (slotStack == null)
			return null;
		final ItemStack newStack = slotStack.copy();
		newStack.stackSize = howMuch;
		if ((slotStack.stackSize -= howMuch) == 0)
			slots[slot] = null;
		return newStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemStack)
	{
		slots[slot] = itemStack;
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
	public boolean isUseableByPlayer(final EntityPlayer entityPlayer)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack)
	{
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}