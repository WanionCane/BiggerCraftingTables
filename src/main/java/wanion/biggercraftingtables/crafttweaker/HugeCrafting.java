package wanion.biggercraftingtables.crafttweaker;

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
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.ShapedHugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapelessHugeRecipe;
import wanion.lib.common.CraftTweakerHelper;
import wanion.lib.common.Util;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.List;

import static wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry.IHugeRecipe;

@SuppressWarnings("unused")
@ZenRegister
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
				input[y][x] = CraftTweakerHelper.toActualObject(inputs[y][x]);
		CraftTweakerAPI.apply(new Add(new ShapedHugeRecipe(CraftTweakerHelper.toStack(output), RecipeHelper.rawShapeToShape(Util.treeDimArrayToTwoDimArray(input)).actualShape)));
	}

	@ZenMethod
	public static void addShapeless(@Nonnull final IItemStack output, @Nonnull final IIngredient[] inputs)
	{
		CraftTweakerAPI.apply(new Add(new ShapelessHugeRecipe(CraftTweakerHelper.toStack(output), CraftTweakerHelper.toObjects(inputs))));
	}

	@ZenMethod
	public static void remove(final IItemStack target)
	{
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toStack(target)));
	}

	private static class Add implements IAction
	{
		private final IHugeRecipe recipe;

		public Add(@Nonnull final IHugeRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			HugeRecipeRegistry.instance.addRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding HugeRecipe for " + recipe.getOutput().getDisplayName();
		}
	}

	private static class Remove implements IAction
	{
		private final ItemStack itemStackToRemove;
		private final IHugeRecipe recipe;

		private Remove(@Nonnull final ItemStack itemStackToRemove)
		{
			this.itemStackToRemove = itemStackToRemove;
			IHugeRecipe recipe = null;
			for (final List<IHugeRecipe> hugeRecipeList : HugeRecipeRegistry.instance.shapedRecipes.valueCollection()) {
				if (hugeRecipeList == null)
					continue;
				for (final IHugeRecipe hugeRecipe : hugeRecipeList) {
					if (hugeRecipe.getOutput().isItemEqual(itemStackToRemove)) {
						HugeRecipeRegistry.instance.removeRecipe(recipe = hugeRecipe);
						break;
					}
				}
			}
			if (recipe == null) {
				for (final List<IHugeRecipe> hugeRecipeList : HugeRecipeRegistry.instance.shapelessRecipes.valueCollection()) {
					if (hugeRecipeList == null)
						continue;
					for (final IHugeRecipe hugeRecipe : hugeRecipeList) {
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
			HugeRecipeRegistry.instance.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing HugeRecipe for " + itemStackToRemove.getDisplayName();
		}
	}
}