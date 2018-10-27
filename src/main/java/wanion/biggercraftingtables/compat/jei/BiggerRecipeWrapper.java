package wanion.biggercraftingtables.compat.jei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import java.util.List;

public final class BiggerRecipeWrapper implements IRecipeWrapper
{
	public final IAdvancedRecipe advancedRecipe;

	public BiggerRecipeWrapper(final IAdvancedRecipe advancedRecipe)
	{
		this.advancedRecipe = advancedRecipe;
	}

	@Override
	public void getIngredients(final IIngredients ingredients)
	{
		ingredients.setInputLists(VanillaTypes.ITEM, getRecipeInputs(advancedRecipe));
		ingredients.setOutput(VanillaTypes.ITEM, advancedRecipe.getOutput());
	}

	private List<List<ItemStack>> getRecipeInputs(final IAdvancedRecipe advancedRecipe)
	{
		return BiggerCraftingTablesJEIPlugin.jeiHelpers.getStackHelper().expandRecipeItemStackInputs(advancedRecipe.getInputs());
	}
}