package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityAutoBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

public final class TileEntityAutoGiantCraftingTable extends TileEntityAutoBiggerCraftingTable<IGiantRecipe>
{
	@Nonnull
	@Override
	public String getDefaultName()
	{
		return "container.autogiantcraftingtable.name";
	}

	@Override
	public int getSizeInventory()
	{
		return 164;
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IGiantRecipe> getRecipeRegistry()
	{
		return GiantRecipeRegistry.INSTANCE;
	}
}