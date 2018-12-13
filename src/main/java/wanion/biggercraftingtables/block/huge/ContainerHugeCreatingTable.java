package wanion.biggercraftingtables.block.huge;

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

public final class ContainerHugeCreatingTable extends ContainerBiggerCreatingTable
{
	public ContainerHugeCreatingTable(@Nonnull final TileEntityHugeCreatingTable tileEntityHugeCreatingTable, final InventoryPlayer inventoryPlayer)
	{
		super(8, 18, 8, 158, 147, 72, tileEntityHugeCreatingTable, inventoryPlayer);
	}
}