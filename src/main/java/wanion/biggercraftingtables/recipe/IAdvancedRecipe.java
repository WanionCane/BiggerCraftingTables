package wanion.biggercraftingtables.recipe;

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

public interface IAdvancedRecipe
{
	int getRecipeKey();

	int getRecipeSize();

	ItemStack recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offsetX, final int offsetY);

	@Nonnull
	ItemStack getOutput();
}