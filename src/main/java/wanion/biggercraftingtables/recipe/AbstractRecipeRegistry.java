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
		final int root = (int) Math.sqrt(matrix.getSizeInventory());
		int offSetX = 0, offSetY = 0, recipeSize = 0;
		long recipeKey = 0;
		boolean foundX = false, foundY = false;
		for (int x = 0; !foundX && x < root; x++) {
			for (int y = 0; !foundX && y < root; y++)
				if (matrix.getStackInSlot(y * root + x) != null)
					foundX = true;
			if (foundX)
				offSetX = x;
		}
		for (int y = 0; !foundY && y < root; y++) {
			for (int x = 0; x < root; x++) {
				if (matrix.getStackInSlot(y * root + x) != null)
					foundY = true;
				if (foundY)
					offSetY = y;
			}
		}
		for (int y = 0; true; y++) {
			final int actualY = offSetY + y;
			if (root < actualY)
				break;
			for (int x = 0; true; x++) {
				final int actualX = offSetX + x;
				if (root < actualX)
					break;
				if (matrix.getStackInSlot(actualY * root + actualX) != null) {
					recipeKey |= 1 << (y * root + x);
					recipeSize++;
				}
			}
		}
		ItemStack output = null;
		final List<R> shapedRecipeList = shapedRecipes.get(recipeKey);
		if (shapedRecipeList != null) {
			for (R shapedRecipe : shapedRecipeList) {
				output = shapedRecipe.recipeMatch(matrix, offSetX, offSetY);
				if (output != null)
					break;
			}
		}
		if (output == null) {
			final List<R> shapelessRecipeList = shapelessRecipes.get(recipeSize);
			if (shapelessRecipeList != null) {
				for (R shapelessRecipe : shapelessRecipeList) {
					output = shapelessRecipe.recipeMatch(matrix, offSetX, offSetY);
					if (output != null)
						break;
				}
			}
		}
		return output;
	}
}