package wanion.biggercraftingtables.recipe.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.lib.recipe.advanced.IAdvancedRecipe;

public abstract class HugeRecipe implements IAdvancedRecipe
{
	private boolean removed = false;

	@Override
	public final boolean removed()
	{
		return removed;
	}

	@Override
	public final void setRemoved(final boolean removed)
	{
		this.removed = removed;
	}
}