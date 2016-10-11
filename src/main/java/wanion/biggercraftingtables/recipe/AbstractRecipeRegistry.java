package wanion.biggercraftingtables.recipe;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class AbstractRecipeRegistry
{
	public final TIntObjectMap<List<IAdvancedRecipe>> craftingRecipes = new TIntObjectHashMap<>();

	public abstract ItemStack findMatchingRecipe(final InventoryCrafting matrix);
}