package wanion.biggercraftingtables.recipe.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.lib.recipe.advanced.AbstractRecipeRegistry;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

public final class GiantRecipeRegistry extends AbstractRecipeRegistry<GiantRecipeRegistry.IGiantRecipe>
{
	public static final GiantRecipeRegistry INSTANCE = new GiantRecipeRegistry();

	private GiantRecipeRegistry() {}

	public interface IGiantRecipe extends IAdvancedRecipe {}
}