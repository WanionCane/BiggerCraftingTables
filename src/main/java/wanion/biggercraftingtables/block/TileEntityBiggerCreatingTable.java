package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.common.control.ShapeControl;
import wanion.lib.common.WTileEntity;
import wanion.lib.common.control.ControlController;
import wanion.lib.common.matching.IMatchingInventory;
import wanion.lib.common.matching.Matching;
import wanion.lib.common.matching.MatchingController;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;

public abstract class TileEntityBiggerCreatingTable<R extends IAdvancedRecipe> extends WTileEntity implements ISidedInventory, IMatchingInventory
{
	private final ControlController controlController;

	public TileEntityBiggerCreatingTable()
	{
		final int max = getSizeInventory() - 1;
		controlController = getController(ControlController.class);
		final MatchingController matchingController = getController(MatchingController.class);
		for (int i = 0; i < max; i++)
			matchingController.add(new Matching(itemStacks, i));
		controlController.add(new ShapeControl());
	}

	public final int getRoot()
	{
		return getTableType().getRoot();
	}

	@Nonnull
	public final ShapeControl getShapeControl()
	{
		return controlController.get(ShapeControl.class);
	}

	@Nonnull
	public abstract Reference.TableTypes getTableType();

	@Nonnull
	public abstract AbstractRecipeRegistry<R> getRecipeRegistry();

	@Override
	public final int getSizeInventory()
	{
		final int root = getRoot();
		return root * root + 1;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Nonnull
	@Override
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

	@Nonnull
	@Override
	public MatchingController getMatchingController() {
		return getController(MatchingController.class);
	}
}