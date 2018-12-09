package wanion.biggercraftingtables.compat.jei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.big.ContainerBigCraftingTable;
import wanion.biggercraftingtables.block.big.GuiAutoBigCraftingTable;
import wanion.biggercraftingtables.block.big.GuiBigCraftingTable;
import wanion.biggercraftingtables.block.big.GuiBigCreatingTable;
import wanion.biggercraftingtables.block.giant.ContainerGiantCraftingTable;
import wanion.biggercraftingtables.block.giant.GuiAutoGiantCraftingTable;
import wanion.biggercraftingtables.block.giant.GuiGiantCraftingTable;
import wanion.biggercraftingtables.block.giant.GuiGiantCreatingTable;
import wanion.biggercraftingtables.block.huge.ContainerHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.GuiAutoHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.GuiHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.GuiHugeCreatingTable;
import wanion.biggercraftingtables.compat.jei.big.AutoBigRecipeTransferHandler;
import wanion.biggercraftingtables.compat.jei.big.BigRecipeCategory;
import wanion.biggercraftingtables.compat.jei.big.BigRecipeCreatingTransferHandler;
import wanion.biggercraftingtables.compat.jei.giant.AutoGiantRecipeTransferHandler;
import wanion.biggercraftingtables.compat.jei.giant.GiantRecipeCategory;
import wanion.biggercraftingtables.compat.jei.giant.GiantRecipeCreatingTransferHandler;
import wanion.biggercraftingtables.compat.jei.huge.AutoHugeRecipeTransferHandler;
import wanion.biggercraftingtables.compat.jei.huge.HugeRecipeCategory;
import wanion.biggercraftingtables.compat.jei.huge.HugeRecipeCreatingTransferHandler;
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.biggercraftingtables.recipe.big.ShapedBigRecipe;
import wanion.biggercraftingtables.recipe.big.ShapelessBigRecipe;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.biggercraftingtables.recipe.giant.ShapedGiantRecipe;
import wanion.biggercraftingtables.recipe.giant.ShapelessGiantRecipe;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.biggercraftingtables.recipe.huge.ShapedHugeRecipe;
import wanion.biggercraftingtables.recipe.huge.ShapelessHugeRecipe;

@JEIPlugin
public final class BiggerCraftingTablesJEIPlugin implements IModPlugin
{
	public static final String BIG_CRAFTING = "biggerct.big";
	public static final String HUGE_CRAFTING = "biggerct.huge";
	public static final String GIANT_CRAFTING = "biggerct.giant";
	static IJeiHelpers jeiHelpers;

	@Override
	public void registerCategories(final IRecipeCategoryRegistration recipeCategoryRegistration)
	{
		recipeCategoryRegistration.addRecipeCategories(new BigRecipeCategory(recipeCategoryRegistration.getJeiHelpers().getGuiHelper()));
		recipeCategoryRegistration.addRecipeCategories(new HugeRecipeCategory(recipeCategoryRegistration.getJeiHelpers().getGuiHelper()));
		recipeCategoryRegistration.addRecipeCategories(new GiantRecipeCategory(recipeCategoryRegistration.getJeiHelpers().getGuiHelper()));
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
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 0), BIG_CRAFTING);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerBigCraftingTable.class, BIG_CRAFTING, 0, 25, 26, 36);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new AutoBigRecipeTransferHandler(), BIG_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiAutoBigCraftingTable.class, new AutoBiggerCraftingGhostHandler<>());
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new BigRecipeCreatingTransferHandler(), BIG_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiBigCreatingTable.class, new BiggerRecipeCreatingGhostHandler<>());
		modRegistry.addRecipeClickArea(GuiBigCraftingTable.class, 136, 59, 3, 6, BIG_CRAFTING);
		modRegistry.addRecipeClickArea(GuiAutoBigCraftingTable.class, 195, 59, 3, 6, BIG_CRAFTING);
		modRegistry.addRecipeClickArea(GuiBigCreatingTable.class, 136, 59, 3, 6, BIG_CRAFTING);

		// Huge
		modRegistry.addRecipes(HugeRecipeRegistry.INSTANCE.getAllRecipes(), HUGE_CRAFTING);
		modRegistry.handleRecipes(ShapedHugeRecipe.class, BiggerRecipeWrapper::new, HUGE_CRAFTING);
		modRegistry.handleRecipes(ShapelessHugeRecipe.class, BiggerRecipeWrapper::new, HUGE_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1), HUGE_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 1), HUGE_CRAFTING);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerHugeCraftingTable.class, HUGE_CRAFTING, 0, 49, 50, 36);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new AutoHugeRecipeTransferHandler(), HUGE_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiAutoHugeCraftingTable.class, new AutoBiggerCraftingGhostHandler<>());
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new HugeRecipeCreatingTransferHandler(), HUGE_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiHugeCreatingTable.class, new BiggerRecipeCreatingGhostHandler<>());
		modRegistry.addRecipeClickArea(GuiHugeCraftingTable.class, 136, 77, 3, 6, HUGE_CRAFTING);
		modRegistry.addRecipeClickArea(GuiAutoHugeCraftingTable.class, 267, 77, 3, 6, HUGE_CRAFTING);
		modRegistry.addRecipeClickArea(GuiHugeCreatingTable.class, 136, 77, 3, 6, HUGE_CRAFTING);

		// Giant
		modRegistry.addRecipes(GiantRecipeRegistry.INSTANCE.getAllRecipes(), GIANT_CRAFTING);
		modRegistry.handleRecipes(ShapedGiantRecipe.class, BiggerRecipeWrapper::new, GIANT_CRAFTING);
		modRegistry.handleRecipes(ShapelessGiantRecipe.class, BiggerRecipeWrapper::new, GIANT_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 2), GIANT_CRAFTING);
		modRegistry.addRecipeCatalyst(new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 2), GIANT_CRAFTING);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerGiantCraftingTable.class, GIANT_CRAFTING, 0, 81, 82, 36);
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new AutoGiantRecipeTransferHandler(), GIANT_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiAutoGiantCraftingTable.class, new AutoBiggerCraftingGhostHandler<>());
		modRegistry.getRecipeTransferRegistry().addRecipeTransferHandler(new GiantRecipeCreatingTransferHandler(), GIANT_CRAFTING);
		modRegistry.addGhostIngredientHandler(GuiGiantCreatingTable.class, new BiggerRecipeCreatingGhostHandler<>());
		modRegistry.addRecipeClickArea(GuiGiantCraftingTable.class, 172, 95, 3, 6, GIANT_CRAFTING);
		modRegistry.addRecipeClickArea(GuiAutoGiantCraftingTable.class, 339, 95, 3, 6, GIANT_CRAFTING);
		modRegistry.addRecipeClickArea(GuiGiantCreatingTable.class, 172, 95, 3, 6, GIANT_CRAFTING);
	}
}