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
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import wanion.biggercraftingtables.Config;
import wanion.lib.common.MetaItem;
import wanion.lib.common.WTileEntity;
import wanion.lib.common.control.ControlController;
import wanion.lib.common.control.IControl;
import wanion.lib.common.control.energy.EnergyControl;
import wanion.lib.common.control.redstone.RedstoneControl;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class TileEntityAutoBiggerCraftingTable<R extends IAdvancedRecipe> extends WTileEntity implements ITickable
{
	public final int full = getSizeInventory() - 2, half = full / 2, powerConsumption = half * Config.INSTANCE.powerMultiplier;
	public final RedstoneControl redstoneControl;
	public final EnergyControl energyControl;
	private final BiggerCraftingMatrix biggerCraftingMatrix = new BiggerCraftingMatrix((int) Math.sqrt(half));
	private final ControlController controlController;
	private R cachedRecipe = null;
	private TIntIntMap patternMap = null;

	protected TileEntityAutoBiggerCraftingTable()
	{
		this.controlController = getController(ControlController.class);
		controlController.add((this.redstoneControl = new RedstoneControl(this)));
		controlController.add((this.energyControl = new EnergyControl(powerConsumption * Config.INSTANCE.capacityMultiplier, powerConsumption)));
		addCapability(CapabilityEnergy.ENERGY, energyControl);
		addCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ItemHandlerAutoBiggerCraftingTable(this));
		recipeShapeChanged();
	}

	@Override
	public final void update()
	{
		if (world == null || world.isRemote)
			return;
		final Collection<IControl<?>> allControls = controlController.getInstances();
		if (!allControls.stream().allMatch(IControl::canOperate))
			return;
		if (cachedRecipe == null) {
			if (patternMap != null)
				patternMap = null;
			return;
		}
		final ItemStack recipeStack = itemStacks.get(getSizeInventory() - 1);
		final ItemStack outputStack = itemStacks.get(getSizeInventory() - 2);
		if (recipeStack.isEmpty() || (!outputStack.isEmpty() && outputStack.getCount() == outputStack.getMaxStackSize()))
			return;
		if (patternMap == null)
			patternMap = MetaItem.getKeySizeMap(half, full, itemStacks);
		if (outputStack.isEmpty() && notMatches(MetaItem.getSmartKeySizeMap(0, half, itemStacks), patternMap))
			return;
		else if (!outputStack.isEmpty() && outputStack.getCount() + recipeStack.getCount() > outputStack.getMaxStackSize() || notMatches(MetaItem.getSmartKeySizeMap(0, half, itemStacks), patternMap))
			return;
		allControls.forEach(IControl::operate);
		cleanInput();
		if (outputStack.isEmpty())
			itemStacks.set(getSizeInventory() - 2, recipeStack.copy());
		else
			outputStack.setCount(outputStack.getCount() + recipeStack.getCount());
		markDirty();
	}

	private boolean notMatches(@Nonnull final TIntIntMap inputMap, @Nonnull final TIntIntMap patternMap)
	{
		if (inputMap.size() >= patternMap.size() && inputMap.keySet().containsAll(patternMap.keySet())) {
			for (final int key : patternMap.keys())
				if (inputMap.get(key) < patternMap.get(key))
					return true;
			return false;
		} else
			return true;
	}

	private void cleanInput()
	{
		final TIntIntMap patternMap = new TIntIntHashMap(this.patternMap);
		for (int i = 0; i < half && !patternMap.isEmpty(); i++) {
			final ItemStack itemStack = itemStacks.get(i);
			final int key = MetaItem.get(itemStack);
			if (patternMap.containsKey(key)) {
				final int total = patternMap.get(key);
				final int dif = MathHelper.clamp(total, 1, itemStack.getCount());
				if (itemStack.getItem() == Items.WATER_BUCKET || itemStack.getItem() == Items.LAVA_BUCKET || itemStack.getItem() instanceof UniversalBucket)
					setInventorySlotContents(i, new ItemStack(Items.BUCKET));
				else if (!itemStack.getItem().hasContainerItem(itemStack))
					itemStack.setCount(itemStack.getCount() - dif);
				if (dif - total == 0)
					patternMap.remove(key);
				else
					patternMap.put(key, total - dif);
				if (itemStack.getCount() == 0)
					itemStacks.set(i, ItemStack.EMPTY);
			}
		}
	}

	@Nonnull
	public abstract AbstractRecipeRegistry<R> getRecipeRegistry();

	@Override
	public void readCustomNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		super.readCustomNBT(nbtTagCompound);
		recipeShapeChanged();
	}

	@Override
	public boolean isItemValidForSlot(final int slot, @Nonnull final ItemStack itemStack)
	{
		return true;
	}

	final void recipeShapeChanged()
	{
		R matchedRecipe = getRecipeRegistry().findMatchingRecipe(biggerCraftingMatrix);
		itemStacks.set(getSizeInventory() - 1, (cachedRecipe = matchedRecipe) != null ? cachedRecipe.getOutput().copy() : ItemStack.EMPTY);
		patternMap = null;
	}

	private final class BiggerCraftingMatrix extends InventoryCrafting
	{
		final int square;

		private BiggerCraftingMatrix(final int squareRoot)
		{
			super(new Container()
			{
				@Override
				public boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
				{
					return false;
				}
			}, squareRoot, squareRoot);
			this.square = squareRoot * squareRoot;
		}

		@Override
		@Nonnull
		public ItemStack getStackInSlot(final int slot)
		{
			return itemStacks.get(square + slot);
		}
	}

	private static class ItemHandlerAutoBiggerCraftingTable extends InvWrapper
	{
		private final TileEntityAutoBiggerCraftingTable<? extends IAdvancedRecipe> tileEntityAutoBiggerCraftingTable;

		private ItemHandlerAutoBiggerCraftingTable(@Nonnull final TileEntityAutoBiggerCraftingTable<? extends IAdvancedRecipe> tileEntityAutoBiggerCraftingTable)
		{
			super(tileEntityAutoBiggerCraftingTable);
			this.tileEntityAutoBiggerCraftingTable = tileEntityAutoBiggerCraftingTable;
		}

		@Nonnull
		@Override
		public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate)
		{
			return slot >= tileEntityAutoBiggerCraftingTable.half ? stack : super.insertItem(slot, stack, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(final int slot, final int amount, final boolean simulate)
		{
			boolean full = slot == tileEntityAutoBiggerCraftingTable.full;
			final ItemStack slotStack = simulate ? getStackInSlot(slot).copy() : getStackInSlot(slot);
			if (full || slotStack.getItem() == Items.BUCKET) {
				if (slotStack.isEmpty())
					return ItemStack.EMPTY;
				final ItemStack newStack = slotStack.copy();
				final int newStackSize = MathHelper.clamp(amount, 1, newStack.getCount());
				newStack.setCount(newStackSize);
				slotStack.setCount(slotStack.getCount() - newStackSize);
				if (!simulate && slotStack.isEmpty()) {
					setStackInSlot(slot, ItemStack.EMPTY);
					getInv().markDirty();
				}
				return newStack;
			} else return ItemStack.EMPTY;
		}
	}
}