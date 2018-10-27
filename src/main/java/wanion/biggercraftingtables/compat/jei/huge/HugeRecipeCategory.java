package wanion.biggercraftingtables.compat.jei.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.compat.jei.BiggerRecipeWrapper;
import wanion.biggercraftingtables.compat.jei.BiggerCraftingTablesJEIPlugin;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import java.util.Collections;
import java.util.List;

public class HugeRecipeCategory implements IRecipeCategory<BiggerRecipeWrapper>
{
	private final ICraftingGridHelper craftingGridHelper;
	private final int size;

	private final IDrawable texture;
	private final String localizedName;

	public HugeRecipeCategory(final IGuiHelper guiHelper)
	{
		craftingGridHelper = guiHelper.createCraftingGridHelper(0, this.size = 49);
		localizedName = I18n.format("crafting.huge");
		texture = guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/huge_recipe_jei.png"), 0, 0, 160, 125);
	}

	@Override
	public String getUid()
	{
		return BiggerCraftingTablesJEIPlugin.HUGE_CRAFTING;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public String getModName()
	{
		return Reference.MOD_NAME;
	}

	@Override
	public IDrawable getBackground()
	{
		return texture;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BiggerRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		final IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
		guiItemStackGroup.init(size, false, 139, 54);
		final IAdvancedRecipe advancedRecipe = recipeWrapper.advancedRecipe;
		final List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		final List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
		for (int x = 0; x < 7; x++)
			for (int y = 0; y < 7; y++)
				guiItemStackGroup.init(x + (7 * y), true, 18 * x, 18 * y);
		final List<List<ItemStack>> newInputs = NonNullList.withSize(49, Collections.emptyList());
		if (advancedRecipe.isShaped()) {
			for (int x = 0; x < advancedRecipe.getWidth(); x++)
				for (int y = 0; y < advancedRecipe.getHeight(); y++)
					newInputs.set(x + (7 * y), inputs.get(x + (advancedRecipe.getHeight() * y)));
			craftingGridHelper.setInputs(guiItemStackGroup, newInputs, 7, 7);
		} else {
			for (int i = 0; i < inputs.size(); i++)
				newInputs.set(i, inputs.get(i));
			craftingGridHelper.setInputs(guiItemStackGroup, newInputs, 7, 7);
			recipeLayout.setShapeless();
		}
		guiItemStackGroup.set(size, outputs.get(0));
	}
}