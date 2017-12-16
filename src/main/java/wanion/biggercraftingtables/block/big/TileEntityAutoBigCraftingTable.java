package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityAutoBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.big.BigRecipeRegistry.IBigRecipe;

public final class TileEntityAutoBigCraftingTable extends TileEntityAutoBiggerCraftingTable<IBigRecipe>
{
	@Override
	public int getSizeInventory()
	{
		return 52;
	}

	@Override
	public String getInventoryName()
	{
		return "container.AutoBigCraftingTable";
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IBigRecipe> getRecipeRegistry()
	{
		return BigRecipeRegistry.instance;
	}
}