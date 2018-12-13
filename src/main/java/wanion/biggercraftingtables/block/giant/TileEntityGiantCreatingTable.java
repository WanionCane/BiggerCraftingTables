package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.TileEntityBiggerCreatingTable;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

public final class TileEntityGiantCreatingTable extends TileEntityBiggerCreatingTable
{
	@Nonnull
	@Override
	public Reference.TableTypes getTableType()
	{
		return Reference.TableTypes.GIANT;
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry getRecipeRegistry()
	{
		return GiantRecipeRegistry.INSTANCE;
	}

	@Override
	public String getName()
	{
		return "container.giantcreatingtable.name";
	}
}