package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.TileEntityBiggerCreatingTable;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

public final class TileEntityBigCreatingTable extends TileEntityBiggerCreatingTable<BigRecipeRegistry.IBigRecipe>
{
	@Nonnull
	@Override
	public Reference.TableTypes getTableType()
	{
		return Reference.TableTypes.BIG;
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<BigRecipeRegistry.IBigRecipe> getRecipeRegistry()
	{
		return BigRecipeRegistry.INSTANCE;
	}

	@Nonnull
	@Override
	public String getDefaultName()
	{
		return "container.bigcreatingtable.name";
	}
}