package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTable;
import wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry;
import wanion.lib.recipe.advanced.AbstractRecipeRegistry;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.huge.HugeRecipeRegistry.IHugeRecipe;

public final class ContainerHugeCraftingTable extends ContainerBiggerCraftingTable<IHugeRecipe>
{
	public ContainerHugeCraftingTable(@Nonnull final TileEntityHugeCraftingTable tileEntityHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(7, 8, 18, 8, 158, 147, 72, tileEntityHugeCraftingTable, inventoryPlayer);
	}

	@Nonnull
	@Override
	public AbstractRecipeRegistry<IHugeRecipe> getRecipeRegistry()
	{
		return HugeRecipeRegistry.INSTANCE;
	}
}