package wanion.biggercraftingtables.recipe.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.item.ItemStack;
import wanion.lib.recipe.advanced.AbstractShapedAdvancedRecipe;

import javax.annotation.Nonnull;

public final class ShapedHugeRecipe extends AbstractShapedAdvancedRecipe implements HugeRecipeRegistry.IHugeRecipe
{
	public ShapedHugeRecipe(@Nonnull final ItemStack output, @Nonnull final Object... inputs)
	{
		super(7, output, inputs);
	}
}