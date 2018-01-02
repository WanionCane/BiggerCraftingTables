package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import wanion.lib.common.MetaItem;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;

public abstract class TileEntityAutoBiggerCraftingTable<R extends IAdvancedRecipe> extends TileEntity implements ISidedInventory
{
	private final ItemStack[] itemStacks = new ItemStack[getSizeInventory()];
	private final int full = getSizeInventory() - 2;
	private final int half = full / 2;
	private final BiggerCraftingMatrix biggerCraftingMatrix = new BiggerCraftingMatrix((int) Math.sqrt(half));
	private R cachedRecipe = null;
	private TIntIntMap patternMap = null;
	private final int[] slots;

	protected TileEntityAutoBiggerCraftingTable()
	{
		slots = new int[half + 1];
		for (int i = 0; i < half; i++)
			slots[i] = i;
		slots[half] = full;
	}

	@Nonnull
	public abstract AbstractRecipeRegistry<R> getRecipeRegistry();

	public final void recipeShapeChanged()
	{
		R matchedRecipe = getRecipeRegistry().findMatchingRecipe(biggerCraftingMatrix);
		itemStacks[getSizeInventory() - 1] = (cachedRecipe = matchedRecipe) != null ? cachedRecipe.getOutput().copy() : null;
		patternMap = null;
	}

	@Override
	public final void updateEntity()
	{
		if (worldObj == null || worldObj.isRemote)
			return;
		if (cachedRecipe != null && cachedRecipe.removed()) {
			cachedRecipe = null;
			patternMap = null;
			recipeShapeChanged();
			return;
		} else if (cachedRecipe == null)
			return;
		final ItemStack recipeStack = itemStacks[getSizeInventory() - 1];
		final ItemStack outputStack = itemStacks[getSizeInventory() - 2];
		if (recipeStack == null || (outputStack != null && outputStack.stackSize == outputStack.getMaxStackSize()))
			return;
		if (patternMap == null)
			patternMap = MetaItem.getKeySizeMap(half, full, itemStacks);
		if (outputStack == null && !matches(MetaItem.getSmartKeySizeMap(0, half, itemStacks), patternMap))
			return;
		else if (outputStack != null && outputStack.stackSize + recipeStack.stackSize > outputStack.getMaxStackSize() || !matches(MetaItem.getSmartKeySizeMap(0, half, itemStacks), patternMap))
			return;
		cleanInput();
		if (outputStack == null)
			itemStacks[getSizeInventory() - 2] = recipeStack.copy();
		else
			outputStack.stackSize += recipeStack.stackSize;
		markDirty();
	}

	private boolean matches(@Nonnull final TIntIntMap inputMap, @Nonnull final TIntIntMap patternMap)
	{
		if (inputMap.size() >= patternMap.size() && inputMap.keySet().containsAll(patternMap.keySet())) {
			for (final int key : patternMap.keys())
				if (inputMap.get(key) < patternMap.get(key))
					return false;
			return true;
		} else
			return false;
	}

	private void cleanInput()
	{
		final TIntIntMap patternMap = new TIntIntHashMap(this.patternMap);
		for (int i = 0; i < half && !patternMap.isEmpty(); i++) {
			final ItemStack itemStack = itemStacks[i];
			final int key = MetaItem.get(itemStack);
			if (patternMap.containsKey(key)) {
				final int total = patternMap.get(key);
				final int dif = MathHelper.clamp_int(total, 1, itemStack.stackSize);
				if (!itemStack.getItem().hasContainerItem(itemStack))
					itemStack.stackSize -= dif;
				if (dif - total == 0)
					patternMap.remove(key);
				else
					patternMap.put(key, total - dif);
				if (itemStack.stackSize == 0)
					itemStacks[i] = null;
			}
		}
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
		recipeShapeChanged();
	}

	@Override
	public final void writeToNBT(final NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		writeCustomNBT(nbtTagCompound);
	}

	void readCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = nbtTagCompound.getTagList("Contents", 10);
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			final NBTTagCompound slotCompound = nbtTagList.getCompoundTagAt(i);
			final int slot = slotCompound.getShort("Slot");
			if (slot >= 0 && slot < getSizeInventory())
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotCompound));
		}
	}

	NBTTagCompound writeCustomNBT(final NBTTagCompound nbtTagCompound)
	{
		final NBTTagList nbtTagList = new NBTTagList();
		final int max = getSizeInventory() - 1;
		for (int i = 0; i < max; i++) {
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
		return itemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int howMuch)
	{
		final ItemStack slotStack = itemStacks[slot];
		if (slotStack == null)
			return null;
		final ItemStack newStack = slotStack.copy();
		newStack.stackSize = howMuch;
		if ((slotStack.stackSize -= howMuch) == 0)
			itemStacks[slot] = null;
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
		itemStacks[slot] = itemStack;
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
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
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

	@Override
	public int[] getAccessibleSlotsFromSide(final int side)
	{
		return slots;
	}

	@Override
	public boolean canInsertItem(final int slot, final ItemStack itemStack, final int side)
	{
		return slot < half;
	}

	@Override
	public boolean canExtractItem(final int slot, final ItemStack itemStack, final int side)
	{
		return slot == full;
	}

	private final class BiggerCraftingMatrix extends InventoryCrafting
	{
		final int square;

		private BiggerCraftingMatrix(final int squareRoot)
		{
			super(new Container()
			{
				@Override
				public boolean canInteractWith(final EntityPlayer entityPlayer)
				{
					return false;
				}
			}, squareRoot, squareRoot);
			this.square = squareRoot * squareRoot;
		}

		@Override
		public ItemStack getStackInSlot(final int slot)
		{
			return itemStacks[square + slot];
		}
	}
}