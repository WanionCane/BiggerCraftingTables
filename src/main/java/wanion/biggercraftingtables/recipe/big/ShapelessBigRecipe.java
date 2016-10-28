package wanion.biggercraftingtables.recipe.big;

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

public final class ShapelessBigRecipe implements IBigRecipe
{
	private final int recipeSize = 0;

	@Override
	public long getRecipeKey()
	{
		return 0;
	}

	@Override
	public int getRecipeSize()
	{
		return recipeSize;
	}

	@Override
	public ItemStack recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offSetX, final int offSetY)
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