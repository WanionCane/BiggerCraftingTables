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
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.HugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapedHugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapelessHugeRecipe;
import wanion.lib.common.MineTweakerHelper;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.List;

@ZenClass("mods.biggercraftingtables.Huge")
public final class HugeCrafting
{
	private HugeCrafting() {}

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
		MineTweakerAPI.apply(new Add(new ShapedHugeRecipe(MineTweakerHelper.toStack(output), RecipeHelper.rawShapeToShape(input, 7))));
	}

	@ZenMethod
	public static void addShapeless(@Nonnull final IItemStack output, @Nonnull final IIngredient[] inputs)
	{
		MineTweakerAPI.apply(new Add(new ShapelessHugeRecipe(MineTweakerHelper.toStack(output), MineTweakerHelper.toObjects(inputs))));
	}

	@ZenMethod
	public static void remove(final IItemStack target)
	{
		MineTweakerAPI.apply(new Remove(MineTweakerHelper.toStack(target)));
	}

	private static class Add implements IUndoableAction
	{
		private final HugeRecipe recipe;

		public Add(@Nonnull final HugeRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			HugeRecipeRegistry.instance.addRecipe(recipe);
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
			HugeRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding HugeRecipe for " + recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-adding HugeRecipe Recipe for " + recipe.getOutput().getDisplayName();
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
		private final HugeRecipe recipe;

		private Remove(@Nonnull final ItemStack itemStackToRemove)
		{
			this.itemStackToRemove = itemStackToRemove;
			HugeRecipe recipe = null;
			for (final List<HugeRecipe> hugeRecipeList : HugeRecipeRegistry.instance.shapedRecipes.valueCollection()) {
				if (hugeRecipeList == null)
					continue;
				for (final HugeRecipe hugeRecipe : hugeRecipeList) {
					if (hugeRecipe.getOutput().isItemEqual(itemStackToRemove)) {
						HugeRecipeRegistry.instance.removeRecipe(recipe = hugeRecipe);
						break;
					}
				}
			}
			if (recipe == null) {
				for (final List<HugeRecipe> hugeRecipeList : HugeRecipeRegistry.instance.shapelessRecipes.valueCollection()) {
					if (hugeRecipeList == null)
						continue;
					for (final HugeRecipe hugeRecipe : hugeRecipeList) {
						if (hugeRecipe.getOutput().isItemEqual(itemStackToRemove)) {
							HugeRecipeRegistry.instance.removeRecipe(recipe = hugeRecipe);
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
			HugeRecipeRegistry.instance.removeRecipe(recipe);
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
			HugeRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing HugeRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Un-removing HugeRecipe for " + itemStackToRemove.getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}
}