package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityAutoBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry.IHugeRecipe;

public final class TileEntityAutoHugeCraftingTable extends TileEntityAutoBiggerCraftingTable<HugeRecipeRegistry.IHugeRecipe>
{
	@Override
	public int getSizeInventory()
	{
		return 100;
	}

	@Override
	public String getName()
	{
		return "container.autohugecraftingtable";
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IHugeRecipe> getRecipeRegistry()
	{
		return HugeRecipeRegistry.instance;
	}
}