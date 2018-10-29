package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class ContainerAutoBiggerCraftingTable extends Container
{
	protected final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable;

	public ContainerAutoBiggerCraftingTable(@Nonnull final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable)
	{
		this.tileEntityAutoBiggerCraftingTable = tileEntityAutoBiggerCraftingTable;
	}

	@Override
	public boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityAutoBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}

	public final void defineShape(final short key, @Nonnull ItemStack output)
	{
		final IAdvancedRecipe advancedRecipe = tileEntityAutoBiggerCraftingTable.getRecipeRegistry().findRecipeByKeyAndOutput(key, output);
		if (advancedRecipe == null)
			return;
		final int slotCount = inventorySlots.size();
		final int startsIn = ((slotCount - 36) / 2) - 1, endsIn = slotCount - 38;
		final int root = (int) Math.sqrt(endsIn - startsIn + 1);
		for (int i = startsIn; i < endsIn; i++) {
			final Slot slot = inventorySlots.get(i);
			slot.putStack(ItemStack.EMPTY);
		}
		final List<Object> inputs = advancedRecipe.getInputs();
		if (advancedRecipe.isShaped()) {
			int i = 0;
			for (int y = 0; y < root; y++) {
				for (int x = 0; x < root; x++) {
					if (i >= inputs.size() || x >= advancedRecipe.getWidth() || y >= advancedRecipe.getHeight())
						continue;
					final Slot slot = inventorySlots.get(startsIn + (x + (root * y)));
					final ItemStack stackInput = getStackInput(inputs.get(i++));
					if (stackInput != null)
						slot.putStack(stackInput);
				}
			}
		} else {
			for (int i = 0; i < inputs.size() && i < root * root; i++) {
				final Slot slot = inventorySlots.get(startsIn + i);
				final ItemStack stackInput = getStackInput(inputs.get(i));
				if (stackInput != null)
					slot.putStack(stackInput);
			}
		}
		tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
		detectAndSendChanges();
	}

	private static ItemStack getStackInput(final Object input)
	{
		return input instanceof ItemStack ? ((ItemStack) input).copy() : input instanceof List ? ((ItemStack) ((List) input).get(0)).copy() : null;
	}
}