package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import wanion.biggercraftingtables.block.ContainerBiggerCreatingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.recipe.big.BigRecipeRegistry.IBigRecipe;

public final class ContainerBigCreatingTable extends ContainerBiggerCreatingTable<IBigRecipe>
{
	public ContainerBigCreatingTable(@Nonnull final TileEntityBigCreatingTable tileEntityBigCreatingTable, final InventoryPlayer inventoryPlayer)
	{
		super(44, 18, 8, 122, 147, 54, tileEntityBigCreatingTable, inventoryPlayer);
	}
}