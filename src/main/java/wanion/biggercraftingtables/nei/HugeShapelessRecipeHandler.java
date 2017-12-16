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
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.huge.GuiHugeCraftingTable;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.ShapelessHugeRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry.IHugeRecipe;

public final class HugeShapelessRecipeHandler extends ShapelessRecipeHandler
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
		return StatCollector.translateToLocal("crafting.huge.shapeless");
	}

	@Override
	public void loadCraftingRecipes(@Nonnull final String outputId, Object... results)
	{
		if (outputId.equals("huge") && getClass() == HugeShapelessRecipeHandler.class)
			HugeRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.forEach(iHugeRecipe -> {
				final CachedShapelessHugeRecipe cachedShapelessHugeRecipe = new CachedShapelessHugeRecipe((ShapelessHugeRecipe) iHugeRecipe);
				cachedShapelessHugeRecipe.computeVisuals();
				arecipes.add(cachedShapelessHugeRecipe);
			}));
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result)
	{
		final List<IHugeRecipe> matchingRecipes = new ArrayList<>();
		HugeRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.stream().filter(iHugeRecipe -> NEIServerUtils.areStacksSameTypeCrafting(iHugeRecipe.getOutput(), result)).forEach(matchingRecipes::add));
		matchingRecipes.forEach(iHugeRecipe -> {
			final CachedShapelessHugeRecipe cachedShapelessHugeRecipe = new CachedShapelessHugeRecipe((ShapelessHugeRecipe) iHugeRecipe);
			cachedShapelessHugeRecipe.computeVisuals();
			arecipes.add(cachedShapelessHugeRecipe);
		});
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient)
	{
		HugeRecipeRegistry.instance.shapelessRecipes.valueCollection().forEach(iHugeRecipes -> iHugeRecipes.forEach(iHugeRecipe -> {
			final CachedShapelessHugeRecipe cachedShapelessHugeRecipe = new CachedShapelessHugeRecipe((ShapelessHugeRecipe) iHugeRecipe);
			cachedShapelessHugeRecipe.computeVisuals();
			if (cachedShapelessHugeRecipe.contains(cachedShapelessHugeRecipe.inputs, ingredient)) {
				cachedShapelessHugeRecipe.setIngredientPermutation(cachedShapelessHugeRecipe.inputs, ingredient);
				arecipes.add(cachedShapelessHugeRecipe);
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
	public boolean hasOverlay(final GuiContainer guiContainer, final net.minecraft.inventory.Container container, final int recipe)
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

	private class CachedShapelessHugeRecipe extends CachedRecipe
	{
		private final List<PositionedStack> inputs = new ArrayList<>();
		private final PositionedStack output;

		private CachedShapelessHugeRecipe(@Nonnull final ShapelessHugeRecipe shapelessHugeRecipe)
		{
			this.output = new PositionedStack(shapelessHugeRecipe.getOutput(), 143, 59);
			for (int i = 0; i < shapelessHugeRecipe.inputs.size(); i++) {
				final PositionedStack stack = new PositionedStack(shapelessHugeRecipe.inputs.get(i), 4 + (i % 7) * 18, 5 + (i / 7) * 18);
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