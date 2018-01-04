package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.TileEntityBiggerCraftingTable;

public final class TileEntityHugeCraftingTable extends TileEntityBiggerCraftingTable
{
	@Override
	public int getSizeInventory()
	{
		return 50;
	}

	@Override
	public String getName()
	{
		return "container.hugecraftingtable";
	}
}