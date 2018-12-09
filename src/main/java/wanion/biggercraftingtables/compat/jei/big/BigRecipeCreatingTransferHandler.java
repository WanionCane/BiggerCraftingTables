package wanion.biggercraftingtables.compat.jei.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.block.big.ContainerBigCreatingTable;
import wanion.biggercraftingtables.compat.jei.BiggerRecipeCreatingTransferHandler;

import javax.annotation.Nonnull;

public class BigRecipeCreatingTransferHandler extends BiggerRecipeCreatingTransferHandler<ContainerBigCreatingTable>
{
	@Nonnull
	@Override
	public Class<ContainerBigCreatingTable> getContainerClass()
	{
		return ContainerBigCreatingTable.class;
	}
}