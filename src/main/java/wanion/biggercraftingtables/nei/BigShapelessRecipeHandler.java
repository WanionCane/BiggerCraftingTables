package wanion.biggercraftingtables.nei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.big.GuiBigCraftingTable;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.biggercraftingtables.recipe.big.BigRecipe;
import wanion.biggercraftingtables.recipe.big.ShapelessBigRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class BigShapelessRecipeHandler extends ShapelessRecipeHandler
{
	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiBigCraftingTable.class;
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("crafting.big.shapeless");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("big") && getClass() == BigShapelessRecipeHandler.class)
			BigRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.forEach(iBigRecipe -> {
				final CachedShapelessBigRecipe cachedShapelessBigRecipe = new CachedShapelessBigRecipe((ShapelessBigRecipe) iBigRecipe);
				cachedShapelessBigRecipe.computeVisuals();
				arecipes.add(cachedShapelessBigRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<BigRecipe> matchingRecipes = new ArrayList<>();
		BigRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.stream().filter(iBigRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iBigRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iBigRecipe -> {
			final CachedShapelessBigRecipe cachedShapelessBigRecipe = new CachedShapelessBigRecipe((ShapelessBigRecipe) iBigRecipe);
			cachedShapelessBigRecipe.computeVisuals();
			arecipes.add(cachedShapelessBigRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		BigRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.forEach(iBigRecipe -> {
			final CachedShapelessBigRecipe cachedShapelessBigRecipe = new CachedShapelessBigRecipe((ShapelessBigRecipe) iBigRecipe);
			cachedShapelessBigRecipe.computeVisuals();
			if (cachedShapelessBigRecipe.contains(cachedShapelessBigRecipe.inputs, ingredient)) {
				cachedShapelessBigRecipe.setIngredientPermutation(cachedShapelessBigRecipe.inputs, ingredient);
				arecipes.add(cachedShapelessBigRecipe);
			}
		}));
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(116, 61, 3, 6), "big"));
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "big";
	}

	@Override
	public boolean hasOverlay(final GuiContainer guiContainer, final Container container, final int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(guiContainer, "big");
	}

	@Override
	public String getGuiTexture()
	{
		return "biggercraftingtables:textures/gui/bigRecipe.png";
	}

	@Override
	public void drawBackground(final int recipe)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 109);
	}

	private class CachedShapelessBigRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapelessBigRecipe(@Nonnull final ShapelessBigRecipe shapedBigRecipe)
		{
			this.output = new PositionedStack(shapedBigRecipe.getOutput(), 127, 56);
			for (int i = 0; i < shapedBigRecipe.inputs.size(); i++) {
				final PositionedStack stack = new PositionedStack(shapedBigRecipe.inputs.get(i), 24 + (i % 5) * 18, 20 + (i / 5) * 18);
				stack.setMaxSize(1);
				inputs.add(stack);
			}
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 20, inputs);
		}

		@Override
		public PositionedStack getResult()
		{
			return output;
		}

		private void computeVisuals()
		{
			inputs.forEach(PositionedStack::generatePermutations);
		}
	}
}