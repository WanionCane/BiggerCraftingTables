package wanion.biggercraftingtables.recipe.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.recipe.AbstractShapelessAdvancedRecipe;

import javax.annotation.Nonnull;

public final class ShapelessHugeRecipe extends AbstractShapelessAdvancedRecipe implements HugeRecipeRegistry.IHugeRecipe
{
	public ShapelessHugeRecipe(@Nonnull ItemStack output, @Nonnull Object... inputs)
	{
		super(output, inputs);
	}

	@Override
	public String getRecipeType()
	{
		return "ShapelessHuge";
	}

	@Override
	public short getMaxRecipeSize()
	{
		return 49;
	}
}