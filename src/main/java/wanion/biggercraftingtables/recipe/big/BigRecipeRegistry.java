package wanion.biggercraftingtables.recipe.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

public final class BigRecipeRegistry extends AbstractRecipeRegistry<BigRecipe>
{
	public static final BigRecipeRegistry instance = new BigRecipeRegistry();

	private BigRecipeRegistry() {}
}