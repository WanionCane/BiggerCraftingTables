package wanion.biggercraftingtables.recipe;

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
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractShapedAdvancedRecipe implements IAdvancedRecipe
{
	private boolean removed = false;
	private final ItemStack output;
	private final short recipeKey, recipeSize;
	public final short width, height;
	public final Object[] inputs;
	public final int squareRoot;

	public AbstractShapedAdvancedRecipe(final int squareRoot, @Nonnull final ItemStack output, @Nonnull final Object... inputs)
	{
		this.squareRoot = squareRoot;
		this.output = output.copy();
		int dictionaryIndex = 0;
		short height = 0, width = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (!(inputs[i] instanceof String)) {
				dictionaryIndex = i;
				break;
			} else
				height++;
		}
		if (dictionaryIndex == 0 || dictionaryIndex > squareRoot)
			throw new RuntimeException("dictionary index is either zero or above the Square Root.");
		final TCharObjectMap<Object> charDictionary = new TCharObjectHashMap<>();
		for (int i = dictionaryIndex; i < inputs.length; i++)
			charDictionary.put((char) inputs[i], inputs[++i]);
		int offSetX = 0;
		{
			boolean found = false;
			for (int x = 0; !found && x < squareRoot; x++) {
				for (int y = 0; !found && y < height; y++) {
					final String code = (String) inputs[y];
					if (x < code.length() && charDictionary.get(code.charAt(x)) != null)
						found = true;
				}
				if (found)
					offSetX = x;
			}
		}
		int offSetY = 0;
		{
			boolean found = false;
			for (int y = 0; !found && y < height; y++) {
				for (int x = 0; x < squareRoot; x++) {
					final String code = (String) inputs[y];
					if (x < code.length() && charDictionary.get(code.charAt(x)) != null)
						found = true;
					if (found)
						offSetY = y;
				}
			}
		}
		final int oldHeight = height;
		height = 0;
		for (int y = 0; y < oldHeight; y++) {
			int x = 0;
			while (true) {
				final int actualY = offSetY + y;
				if (actualY < oldHeight) {
					final String code = (String) inputs[actualY];
					final int actualX = offSetX + x++;
					if (actualX < code.length()) {
						if (charDictionary.get(code.charAt(actualX)) == null)
							continue;
						final int xDifference = actualX - (offSetX - 1);
						final int yDifference = actualY - (offSetY - 1);
						if (xDifference > width)
							width = (short) xDifference;
						if (yDifference > height)
							height = (short) yDifference;
					} else break;
				} else break;
			}
		}
		this.inputs = new Object[height * width];
		this.width = width;
		this.height = height;
		short recipeSize = 0;
		for (int y = 0; y < height; y++) {
			final int actualY = offSetY + y;
			final String code = (String) inputs[actualY];
			for (int x = 0; x < width; x++) {
				final int actualX = offSetX + x;
				if (actualX < code.length()) {
					final Object input = charDictionary.get(code.charAt(actualX));
					if (input != null && (input instanceof ItemStack || input instanceof String || input instanceof List)) {
						final int pos = width * y + x;
						if (input instanceof ItemStack) {
							if (((ItemStack) input).getItem() != null)
								((ItemStack) (this.inputs[pos] = ((ItemStack) input).copy())).stackSize = 1;
						} else if (input instanceof String) {
							final List<ItemStack> oreList = OreDictionary.getOres((String) input, false);
							if (oreList != null && !oreList.isEmpty())
								this.inputs[pos] = oreList;
						} else if (!((List) input).isEmpty() && ((List) input).get(0) instanceof ItemStack)
							this.inputs[pos] = input;
						if (this.inputs[pos] != null)
							recipeSize++;
					}
				}
			}
		}
		this.recipeKey = (short) ((this.recipeSize = recipeSize) | (width << 8) | (height << 12));
	}

	@Override
	public final boolean removed()
	{
		return removed;
	}

	@Override
	public final void setRemoved(final boolean removed)
	{
		this.removed = removed;
	}

	@Override
	public short getRecipeKey()
	{
		return recipeKey;
	}

	@Override
	public short getRecipeSize()
	{
		return recipeSize;
	}

	@Override
	public boolean recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offSetX, final int offSetY)
	{
		return recipeMatch(inventoryCrafting, offSetX, offSetY, false) || recipeMatch(inventoryCrafting, offSetX, offSetY, true);
	}

	private boolean recipeMatch(@Nonnull final InventoryCrafting inventoryCrafting, final int offSetX, final int offSetY, final boolean mirror)
	{
		boolean matches = true;
		for (int y = 0; matches && y < height; y++) {
			final int actualY = offSetY + y;
			for (int x = 0; matches && x < width; x++) {
				final int actualX = mirror ? (offSetX + x) : (offSetX + width - x - 1);
				final Object input = inputs[y * width + x];
				final ItemStack slotItemStack = inventoryCrafting.getStackInSlot(actualY * squareRoot + actualX);
				if ((slotItemStack == null && input != null) || (slotItemStack != null && input == null))
					matches = false;
				if (!matches || slotItemStack == input)
					continue;
				if (input instanceof List) {
					boolean found = false;
					final List inputList = (List) input;
					for (final Object object : inputList) {
						if (found)
							break;
						if (object instanceof ItemStack && (((ItemStack) object).getItem() == slotItemStack.getItem() && (((ItemStack) object).getItemDamage() == OreDictionary.WILDCARD_VALUE || ((ItemStack) object).getItemDamage() == slotItemStack.getItemDamage())))
							found = true;
						else break;
					}
					if (found)
						continue;
				} else if (input instanceof ItemStack) {
					if (((ItemStack) input).hasTagCompound() && ((ItemStack) input).getItem() == slotItemStack.getItem() && (!((ItemStack) input).getHasSubtypes() || (((ItemStack) input).getItemDamage() == slotItemStack.getItemDamage())) && ItemStack.areItemStackTagsEqual(((ItemStack) input), slotItemStack))
						continue;
					else if (((ItemStack) input).getItem() == slotItemStack.getItem() && (!((ItemStack) input).getHasSubtypes() || ((ItemStack) input).getItemDamage() == OreDictionary.WILDCARD_VALUE || (((ItemStack) input).getItemDamage() == slotItemStack.getItemDamage())))
						continue;
				}
				matches = false;
			}
		}
		return matches;
	}

	@Nonnull
	@Override
	public ItemStack getOutput()
	{
		return output.copy();
	}
}