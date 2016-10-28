package wanion.biggercraftingtables.minetweaker;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class Tweaker
{
	private Tweaker()
	{
	}

	public static void init()
	{
		MineTweakerAPI.registerClass(BigCrafting.class);
		MineTweakerAPI.registerClass(HugeCrafting.class);
	}

	static ItemStack toStack(final IItemStack item)
	{
		if (item == null)
			return null;
		final Object internal = item.getInternal();
		if (internal == null || !(internal instanceof ItemStack))
			MineTweakerAPI.getLogger().logError("Not a valid item stack: " + item);
		return internal instanceof ItemStack ? (ItemStack) internal : null;
	}

	static Object[] toObjects(final IIngredient[] list)
	{
		if (list == null)
			return null;
		Object[] ingredients = new Object[list.length];
		for (int x = 0; x < list.length; x++) {
			ingredients[x] = toObject(list[x]);
		}
		return ingredients;
	}

	static Object toActualObject(final IIngredient ingredient)
	{
		if (ingredient == null) return null;
		else {
			if (ingredient instanceof IOreDictEntry) {
				return OreDictionary.getOres(toString((IOreDictEntry) ingredient));
			} else if (ingredient instanceof IItemStack) {
				return toStack((IItemStack) ingredient);
			} else return null;
		}
	}

	private static Object toObject(final IIngredient ingredient)
	{
		if (ingredient == null)
			return null;
		if (ingredient instanceof IOreDictEntry) {
			return toString((IOreDictEntry) ingredient);
		} else if (ingredient instanceof IItemStack) {
			return toStack((IItemStack) ingredient);
		} else return null;
	}

	private static String toString(final IOreDictEntry entry)
	{
		return entry.getName();
	}
}