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
import wanion.biggercraftingtables.block.huge.GuiHugeCraftingTable;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.HugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapedHugeRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class HugeShapedRecipeHandler extends ShapedRecipeHandler
{
	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiHugeCraftingTable.class;
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("crafting.huge.shaped");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("huge") && getClass() == HugeShapedRecipeHandler.class)
			HugeRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.forEach(iHugeRecipe -> {
				final CachedShapedHugeRecipe cachedShapedHugeRecipe = new CachedShapedHugeRecipe((ShapedHugeRecipe) iHugeRecipe);
				cachedShapedHugeRecipe.computeVisuals();
				arecipes.add(cachedShapedHugeRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<HugeRecipe> matchingRecipes = new ArrayList<>();
		HugeRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.stream().filter(iHugeRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iHugeRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iHugeRecipe -> {
			final CachedShapedHugeRecipe cachedShapedHugeRecipe = new CachedShapedHugeRecipe((ShapedHugeRecipe) iHugeRecipe);
			cachedShapedHugeRecipe.computeVisuals();
			arecipes.add(cachedShapedHugeRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		HugeRecipeRegistry.instance.shapedRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.forEach(iHugeRecipe -> {
			final CachedShapedHugeRecipe cachedShapedHugeRecipe = new CachedShapedHugeRecipe((ShapedHugeRecipe) iHugeRecipe);
			cachedShapedHugeRecipe.computeVisuals();
			if (cachedShapedHugeRecipe.contains(cachedShapedHugeRecipe.inputs, ingredient)) {
				cachedShapedHugeRecipe.setIngredientPermutation(cachedShapedHugeRecipe.inputs, ingredient);
				arecipes.add(cachedShapedHugeRecipe);
			}
		}));
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(132, 64, 3, 6), "huge"));
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "huge";
	}

	@Override
	public boolean hasOverlay(final GuiContainer guiContainer, final Container container, final int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(guiContainer, "huge");
	}

	@Override
	public String getGuiTexture()
	{
		return "biggercraftingtables:textures/gui/hugeRecipe.png";
	}

	@Override
	public void drawBackground(final int recipe)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 130);
	}

	private class CachedShapedHugeRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapedHugeRecipe(@Nonnull final ShapedHugeRecipe shapedHugeRecipe)
		{
			this.output = new PositionedStack(shapedHugeRecipe.getOutput(), 143, 59);
			for (int x = 0; x < shapedHugeRecipe.width; x++) {
				for (int y = 0; y < shapedHugeRecipe.height; y++) {
					final Object input = shapedHugeRecipe.inputs[y * shapedHugeRecipe.width + x];
					if (input == null)
						continue;
					final PositionedStack positionedStack = new PositionedStack(input, 4 + x * 18, 5 + y * 18);
					positionedStack.setMaxSize(1);
					this.inputs.add(positionedStack);
				}
			}
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(HugeShapedRecipeHandler.this.cycleticks / 20, inputs);
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