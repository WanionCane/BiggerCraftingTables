package wanion.biggercraftingtables.recipe.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class ShapedHugeRecipe implements IHugeRecipe
{
	private final int recipeKey = 0;
	private final int recipeSize = 0;

	public ShapedHugeRecipe()
	{

	}

	@Override
	public int getRecipeKey()
	{
		return recipeKey;
	}

	@Override
	public int getRecipeSize()
	{
		return recipeSize;
	}

	@Override
	public ItemStack recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offsetX, final int offsetY)
	{
		return null;
	}

	@Nonnull
	@Override
	public ItemStack getOutput()
	{
		return null;
	}
}