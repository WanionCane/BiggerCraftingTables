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
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ShapelessBigRecipe implements IBigRecipe
{
	private final ItemStack output;
	private final int recipeSize;
	public final List<Object> inputs = new ArrayList<>();

	public ShapelessBigRecipe(@Nonnull final ItemStack output, @Nonnull final Object... inputs)
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
				else
					continue;
			} else if (input instanceof List)
				if (!((List) input).isEmpty())
					this.inputs.add(input);
				else
					continue;
			else
				continue;
			recipeSize++;
		}
		if (recipeSize == 0 || recipeSize > 25)
			throw new RuntimeException("Invalid ShapelessBigRecipe");
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

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offSetX, final int offSetY)
	{
		final List<Object> inputs = new ArrayList<>(this.inputs);
		final List<ItemStack> slotItemStacks = new ArrayList<>();
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			final ItemStack slotItemStack = inventoryCrafting.getStackInSlot(i);
			if (slotItemStack != null)
				slotItemStacks.add(slotItemStack);
		}
		for (final Iterator<Object> inputsIterator = inputs.iterator(); inputsIterator.hasNext(); ) {
			final Object input = inputsIterator.next();
			boolean found = false;
			if (input instanceof ItemStack) {
				for (final Iterator<ItemStack> slotItemStackIterator = slotItemStacks.iterator(); !found && slotItemStackIterator.hasNext(); ) {
					final ItemStack slotItemStack = slotItemStackIterator.next();
					if (((ItemStack) input).isItemEqual(slotItemStack)) {
						slotItemStackIterator.remove();
						found = true;
					}
				}
				if (found)
					break;
			} else if (input instanceof List) {
				final List<ItemStack> oreDict = (List<ItemStack>) input;
				for (final ItemStack entry : oreDict) {
					for (final Iterator<ItemStack> slotItemStackIterator = slotItemStacks.iterator(); !found && slotItemStackIterator.hasNext(); ) {
						final ItemStack slotItemStack = slotItemStackIterator.next();
						if (entry.isItemEqual(slotItemStack)) {
							slotItemStackIterator.remove();
							found = true;
						}
					}
					if (found)
						break;
				}
			}
			if (found)
				inputsIterator.remove();
			else
				break;
		}
		return inputs.isEmpty() && slotItemStacks.isEmpty() ? getOutput() : null;
	}

	@Nonnull
	@Override
	public ItemStack getOutput()
	{
		return output.copy();
	}
}