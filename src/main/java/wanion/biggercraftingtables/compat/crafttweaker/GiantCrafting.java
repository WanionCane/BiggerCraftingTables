package wanion.biggercraftingtables.compat.crafttweaker;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.biggercraftingtables.recipe.giant.ShapedGiantRecipe;
import wanion.biggercraftingtables.recipe.giant.ShapelessGiantRecipe;
import wanion.lib.common.CraftTweakerHelper;
import wanion.lib.common.Util;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.List;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass("mods.biggercraftingtables.Giant")
public final class GiantCrafting
{
	private GiantCrafting() {}

	@ZenMethod
	public static void addShaped(@Nonnull final IItemStack output, @Nonnull final IIngredient[][] inputs)
	{
		int height = inputs.length;
		int width = 0;
		for (final IIngredient[] row : inputs)
			if (width < row.length)
				width = row.length;
		final Object[][] input = new Object[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				input[y][x] = CraftTweakerHelper.toActualObject(inputs[y][x]);
		CraftTweakerAPI.apply(new Add(new ShapedGiantRecipe(CraftTweakerHelper.toStack(output), RecipeHelper.rawShapeToShape(Util.treeDimArrayToTwoDimArray(input)).actualShape)));
	}

	@ZenMethod
	public static void addShapeless(@Nonnull final IItemStack output, @Nonnull final IIngredient[] inputs)
	{
		CraftTweakerAPI.apply(new Add(new ShapelessGiantRecipe(CraftTweakerHelper.toStack(output), CraftTweakerHelper.toObjects(inputs))));
	}

	@ZenMethod
	public static void remove(final IItemStack target)
	{
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toStack(target)));
	}

	private static class Add implements IAction
	{
		private final IGiantRecipe recipe;

		public Add(@Nonnull final IGiantRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			GiantRecipeRegistry.INSTANCE.addRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding GiantRecipe for " + recipe.getOutput().getDisplayName();
		}
	}

	private static class Remove implements IAction
	{
		private final ItemStack itemStackToRemove;
		private final IGiantRecipe recipe;

		private Remove(@Nonnull final ItemStack itemStackToRemove)
		{
			this.itemStackToRemove = itemStackToRemove;
			IGiantRecipe recipe = null;
			for (final List<IGiantRecipe> giantRecipeList : GiantRecipeRegistry.INSTANCE.recipes.values()) {
				if (recipe != null)
					break;
				if (giantRecipeList == null)
					continue;
				for (final IGiantRecipe giantRecipe : giantRecipeList) {
					if (giantRecipe.getOutput().isItemEqual(itemStackToRemove)) {
						recipe = giantRecipe;
						break;
					}
				}
			}
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			GiantRecipeRegistry.INSTANCE.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing GiantRecipe for " + itemStackToRemove.getDisplayName();
		}
	}
}