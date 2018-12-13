package wanion.biggercraftingtables.block.giant;

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

public final class ContainerGiantCreatingTable extends ContainerBiggerCreatingTable
{
	public ContainerGiantCreatingTable(@Nonnull final TileEntityGiantCreatingTable tileEntityGiantCreatingTable, final InventoryPlayer inventoryPlayer)
	{
		super(8, 18, 8, 194, 183, 90, tileEntityGiantCreatingTable, inventoryPlayer);
	}
}