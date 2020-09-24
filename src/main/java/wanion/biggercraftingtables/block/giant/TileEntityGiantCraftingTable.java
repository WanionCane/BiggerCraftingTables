package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityBiggerCraftingTable;

import javax.annotation.Nonnull;

public final class TileEntityGiantCraftingTable extends TileEntityBiggerCraftingTable
{
	@Nonnull
	@Override
	public String getDefaultName()
	{
		return "container.giantcraftingtable.name";
	}

	@Override
	public int getSizeInventory()
	{
		return 82;
	}
}