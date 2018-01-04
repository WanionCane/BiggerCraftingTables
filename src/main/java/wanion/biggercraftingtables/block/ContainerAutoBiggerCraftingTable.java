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

import javax.annotation.Nonnull;

public abstract class ContainerAutoBiggerCraftingTable extends Container
{
	private final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable;

	public ContainerAutoBiggerCraftingTable(@Nonnull final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable)
	{
		this.tileEntityAutoBiggerCraftingTable = tileEntityAutoBiggerCraftingTable;
	}

	@Override
	public boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityAutoBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}
}