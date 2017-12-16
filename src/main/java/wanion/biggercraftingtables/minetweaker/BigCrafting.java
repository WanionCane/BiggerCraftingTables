package wanion.biggercraftingtables.minetweaker;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.biggercraftingtables.recipe.big.ShapedBigRecipe;
import wanion.biggercraftingtables.recipe.big.ShapelessBigRecipe;
import wanion.lib.common.MineTweakerHelper;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.List;

import static wanion.biggercraftingtables.recipe.big.BigRecipeRegistry.IBigRecipe;

@ZenClass("mods.biggercraftingtables.Big")
public final class BigCrafting
{
	private BigCrafting() {}

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
				input[y][x] = MineTweakerHelper.toActualObject(inputs[y][x]);
		MineTweakerAPI.apply(new Add(new ShapedBigRecipe(MineTweakerHelper.toStack(output), RecipeHelper.rawShapeToShape(input, 5))));
	}

	@ZenMethod
	public static void addShapeless(@Nonnull final IItemStack output, @Nonnull final IIngredient[] inputs)
	{
		MineTweakerAPI.apply(new Add(new ShapelessBigRecipe(MineTweakerHelper.toStack(output), MineTweakerHelper.toObjects(inputs))));
	}

	@ZenMethod
	public static void remove(final IItemStack target)
	{
		MineTweakerAPI.apply(new Remove(MineTweakerHelper.toStack(target)));
	}

	private static class Add implements IUndoableAction
	{
		private final IBigRecipe recipe;

		public Add(@Nonnull final IBigRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			BigRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			recipe.setRemoved(true);
			BigRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding IBigRecipe for " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-adding IBigRecipe Recipe for " + recipe.getOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	private static class Remove implements IUndoableAction
	{
		private final ItemStack itemStackToRemove;
		private final IBigRecipe recipe;

		private Remove(@Nonnull final ItemStack itemStackToRemove)
		{
			this.itemStackToRemove = itemStackToRemove;
			IBigRecipe recipe = null;
			for (final List<IBigRecipe> IBigRecipeList : BigRecipeRegistry.instance.shapedRecipes.valueCollection()) {
				if (IBigRecipeList == null)
					continue;
				for (final IBigRecipe IBigRecipe : IBigRecipeList) {
					if (IBigRecipe.getOutput().isItemEqual(itemStackToRemove)) {
						BigRecipeRegistry.instance.removeRecipe(recipe = IBigRecipe);
						break;
					}
				}
			}
			if (recipe == null) {
				for (final List<IBigRecipe> IBigRecipeList : BigRecipeRegistry.instance.shapelessRecipes.valueCollection()) {
					if (IBigRecipeList == null)
						continue;
					for (final IBigRecipe IBigRecipe : IBigRecipeList) {
						if (IBigRecipe.getOutput().isItemEqual(itemStackToRemove)) {
							BigRecipeRegistry.instance.removeRecipe(recipe = IBigRecipe);
							break;
						}
					}
				}
			}
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			if (recipe != null)
				recipe.setRemoved(true);
			BigRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return recipe != null;
		}

		@Override
		public void undo()
		{
			recipe.setRemoved(false);
			BigRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing IBigRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-removing IBigRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}
}