package wanion.biggercraftingtables.recipe.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class ShapedBigRecipe implements IBigRecipe
{
	private final ItemStack output;
	private final Object[] inputs;
	private final int recipeKey;
	private final int recipeSize;

	public ShapedBigRecipe(@Nonnull final ItemStack output, @Nonnull final Object... inputs)
	{
		this.output = output;
		int dictionaryIndex = 0, height = 0, width = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (!(inputs[i] instanceof String)) {
				dictionaryIndex = i;
				break;
			} else
				height++;
		}
		if (dictionaryIndex == 0 || dictionaryIndex >= 6)
			throw new RuntimeException("Invalid ShapedBigRecipe");
		final TCharObjectMap<Object> charDictionary = new TCharObjectHashMap<>();
		for (int i = dictionaryIndex; i < inputs.length; i++)
			charDictionary.put((char) inputs[i], inputs[++i]);
		final boolean[][] existingOnes = new boolean[5][5];
		for (int y = 0; y < height; y++) {
			final String row = ((String) inputs[y]);
			for (int x = 0; x < ((String) inputs[y]).length(); x++) {
				final Object input = charDictionary.get(row.charAt(x));
				if (input != null && (input instanceof ItemStack || input instanceof String || input instanceof List)) {
					if (input instanceof String) {
						final List<ItemStack> oreList = OreDictionary.getOres((String) input, false);
						if (oreList == null || oreList.isEmpty())
							continue;
					}
					existingOnes[y][x] = true;
				}
			}
		}
		for (int y = height = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++){
				if (!existingOnes[y][x]){

				}
			}

		}
		int recipeKey = 0, recipeSize = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < ((String) inputs[y]).length(); x++) {
				final Object input = charDictionary.get(((String) inputs[y]).charAt(x));
				if (input != null && (input instanceof ItemStack || input instanceof String || input instanceof List)) {
					if (input instanceof String) {
						final List<ItemStack> oreList = OreDictionary.getOres((String) input, false);
						if (oreList == null || oreList.isEmpty())
							continue;
					}
					recipeKey |= 1 << (5 * y + x);
					recipeSize++;
					if (width < x)
						width = x - width;
				}
			}
		}
		this.recipeKey = recipeKey;
		this.recipeSize = recipeSize;
		this.inputs = new Object[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

			}
		}
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
		return output;
	}
}