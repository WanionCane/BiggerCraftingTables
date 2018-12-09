package wanion.biggercraftingtables.compat.jei.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.huge.ContainerAutoHugeCraftingTable;
import wanion.biggercraftingtables.compat.jei.AutoBiggerRecipeTransferHandler;

import javax.annotation.Nonnull;

public class AutoHugeRecipeTransferHandler extends AutoBiggerRecipeTransferHandler<ContainerAutoHugeCraftingTable>
{
	@Nonnull
	@Override
	public Class<ContainerAutoHugeCraftingTable> getContainerClass()
	{
		return ContainerAutoHugeCraftingTable.class;
	}
}