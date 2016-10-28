package wanion.biggercraftingtables.recipe;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipeRegistry<R extends IAdvancedRecipe>
{
	public final TLongObjectMap<List<R>> shapedRecipes = new TLongObjectHashMap<>();
	public final TIntObjectMap<List<R>> shapelessRecipes = new TIntObjectHashMap<>();

	public final void addRecipe(@Nonnull final R recipe)
	{
		final long recipeKey = recipe.getRecipeKey();
		if (recipeKey != 0) {
			if (!shapedRecipes.containsKey(recipeKey))
				shapedRecipes.put(recipeKey, new ArrayList<>());
			shapedRecipes.get(recipeKey).add(recipe);
		} else {
			final int recipeSize = recipe.getRecipeSize();
			if (!shapelessRecipes.containsKey(recipeSize))
				shapelessRecipes.put(recipeSize, new ArrayList<>());
			shapelessRecipes.get(recipeSize).add(recipe);
		}
	}

	public final void removeRecipe(@Nullable final R recipe)
	{
		if (recipe == null)
			return;
		final long recipeKey = recipe.getRecipeKey();
		if (recipeKey != 0) {
			final List<R> shapedRecipeList = shapedRecipes.get(recipeKey);
			if (shapedRecipeList != null)
				shapedRecipeList.remove(recipe);
		} else {
			final List<R> shapelessRecipeList = shapelessRecipes.get(recipe.getRecipeSize());
			if (shapelessRecipeList != null)
				shapelessRecipeList.remove(recipe);
		}
	}

	public final ItemStack findMatchingRecipe(final InventoryCrafting matrix)
	{
		return null;
	}
}