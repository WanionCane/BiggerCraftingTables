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
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class ShapelessHugeRecipe implements IHugeRecipe
{
	private final ItemStack output;
	private final int recipeSize;
	public final List<Object> inputs = new ArrayList<>();

	public ShapelessHugeRecipe(@Nonnull final ItemStack output, @Nonnull final Object... inputs)
	{
		this.output = output.copy();
		int recipeSize = 0;
		for (final Object input : inputs) {
			if (input instanceof ItemStack) {
				if (((ItemStack) input).getItem() == null)
					continue;
				final ItemStack newInput = ((ItemStack) input).copy();
				newInput.stackSize = 0;
				this.inputs.add(newInput);
			} else if (input instanceof String) {
				final List<ItemStack> oreList = OreDictionary.getOres((String) input, false);
				if (oreList != null && !oreList.isEmpty())
					this.inputs.add(oreList);
			} else if (input instanceof List) {
				if (!((List) input).isEmpty())
					this.inputs.add(input);
			} else continue;
			recipeSize++;
		}
		if (recipeSize == 0 || recipeSize > 49)
			throw new RuntimeException("Invalid ShapelessHugeRecipe");
		this.recipeSize = recipeSize;
	}

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
		return output.copy();
	}
}