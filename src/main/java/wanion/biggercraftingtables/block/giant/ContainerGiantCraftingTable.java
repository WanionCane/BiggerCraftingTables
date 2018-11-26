package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry.IGiantRecipe;

public final class ContainerGiantCraftingTable extends ContainerBiggerCraftingTable<IGiantRecipe>
{
	public ContainerGiantCraftingTable(@Nonnull final TileEntityGiantCraftingTable tileEntityGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(9, 8, 18, 8, 194, 183, 90, tileEntityGiantCraftingTable, inventoryPlayer);
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IGiantRecipe> getRecipeRegistry()
	{
		return GiantRecipeRegistry.INSTANCE;
	}
}