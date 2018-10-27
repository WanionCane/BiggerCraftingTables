package wanion.biggercraftingtables.compat.jei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.big.ContainerBigCraftingTable;
import wanion.biggercraftingtables.block.big.GuiBigCraftingTable;
import wanion.biggercraftingtables.block.huge.ContainerHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.GuiHugeCraftingTable;
import wanion.biggercraftingtables.compat.jei.big.BigRecipeCategory;
import wanion.biggercraftingtables.compat.jei.huge.HugeRecipeCategory;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.biggercraftingtables.recipe.big.ShapedBigRecipe;
import wanion.biggercraftingtables.recipe.big.ShapelessBigRecipe;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.ShapedHugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapelessHugeRecipe;

@JEIPlugin
public final class BiggerCraftingTablesJEIPlugin implements IModPlugin
{
	public static final String BIG_CRAFTING = "biggerct.big";
	public static final String HUGE_CRAFTING = "biggerct.huge";
	public static IJeiHelpers jeiHelpers;

	@Override
	public void registerCategories(final IRecipeCategoryRegistration recipeCategoryRegistration)
	{
		recipeCategoryRegistration.addRecipeCategories(new BigRecipeCategory(recipeCategoryRegistration.getJeiHelpers().getGuiHelper()));
		recipeCategoryRegistration.addRecipeCategories(new HugeRecipeCategory(recipeCategoryRegistration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(final IModRegistry modRegistry)
	{
		jeiHelpers = modRegistry.getJeiHelpers();

		// Big
		modRegistry.addRecipes(BigRecipeRegistry.INSTANCE.getAllRecipes(), BIG_CRAFTING);
		modRegistry.handleRecipes(ShapedBigRecipe.class, BiggerRecipeWrapper::new, BIG_CRAFTING);
		modRegistry.handleRecipes(ShapelessBigRecipe.class, BiggerRecipeWrapper::new, BIG_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 0), BIG_CRAFTING);
		//modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 0), BIG_CRAFTING);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerBigCraftingTable.class, BIG_CRAFTING, 0, 25, 26, 36);
		modRegistry.addRecipeClickArea(GuiBigCraftingTable.class, 136, 59, 3, 6, BIG_CRAFTING);

		// Huge
		modRegistry.addRecipes(HugeRecipeRegistry.INSTANCE.getAllRecipes(), HUGE_CRAFTING);
		modRegistry.handleRecipes(ShapedHugeRecipe.class, BiggerRecipeWrapper::new, HUGE_CRAFTING);
		modRegistry.handleRecipes(ShapelessHugeRecipe.class, BiggerRecipeWrapper::new, HUGE_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1), HUGE_CRAFTING);
		//modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 1), HUGE_CRAFTING);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerHugeCraftingTable.class, HUGE_CRAFTING, 0, 49, 50, 36);
		modRegistry.addRecipeClickArea(GuiHugeCraftingTable.class, 136, 77, 3, 6, HUGE_CRAFTING);
	}
}