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
import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.big.GuiBigCraftingTable;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.biggercraftingtables.recipe.big.BigRecipe;
import wanion.biggercraftingtables.recipe.big.ShapedBigRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class BigShapedRecipeHandler extends ShapedRecipeHandler
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
		return StatCollector.translateToLocal("crafting.big.shaped");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("big") && getClass() == BigShapedRecipeHandler.class)
			BigRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.forEach(iBigRecipe -> {
				final CachedShapedBigRecipe cachedShapedBigRecipe = new CachedShapedBigRecipe((ShapedBigRecipe) iBigRecipe);
				cachedShapedBigRecipe.computeVisuals();
				arecipes.add(cachedShapedBigRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<BigRecipe> matchingRecipes = new ArrayList<>();
		BigRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.stream().filter(iBigRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iBigRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iBigRecipe -> {
			final CachedShapedBigRecipe cachedShapedBigRecipe = new CachedShapedBigRecipe((ShapedBigRecipe) iBigRecipe);
			cachedShapedBigRecipe.computeVisuals();
			arecipes.add(cachedShapedBigRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		BigRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iBigRecipes -> iBigRecipes.forEach(iBigRecipe -> {
			final CachedShapedBigRecipe cachedShapedBigRecipe = new CachedShapedBigRecipe((ShapedBigRecipe) iBigRecipe);
			cachedShapedBigRecipe.computeVisuals();
			if (cachedShapedBigRecipe.contains(cachedShapedBigRecipe.inputs, ingredient)) {
				cachedShapedBigRecipe.setIngredientPermutation(cachedShapedBigRecipe.inputs, ingredient);
				arecipes.add(cachedShapedBigRecipe);
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

	private class CachedShapedBigRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapedBigRecipe(@Nonnull final ShapedBigRecipe shapedBigRecipe)
		{
			this.output = new PositionedStack(shapedBigRecipe.getOutput(), 127, 56);
			for (int x = 0; x < shapedBigRecipe.width; x++) {
				for (int y = 0; y < shapedBigRecipe.height; y++) {
					final Object input = shapedBigRecipe.inputs[y * shapedBigRecipe.width + x];
					if (input == null)
						continue;
					final PositionedStack positionedStack = new PositionedStack(input, 24 + x * 18, 20 + y * 18);
					positionedStack.setMaxSize(1);
					this.inputs.add(positionedStack);
				}
			}
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(BigShapedRecipeHandler.this.cycleticks / 20, inputs);
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