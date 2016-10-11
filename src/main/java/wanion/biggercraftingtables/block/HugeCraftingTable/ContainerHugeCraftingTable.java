package wanion.biggercraftingtables.block.HugeCraftingTable;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import wanion.biggercraftingtables.block.ContainerBiggerCraftingTables;

import javax.annotation.Nonnull;

public final class ContainerHugeCraftingTable extends ContainerBiggerCraftingTables
{
	public ContainerHugeCraftingTable(@Nonnull final TileEntityHugeCraftingTable tileEntityHugeCraftingTablee, final InventoryPlayer inventoryPlayer)
	{
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new Slot(tileEntityHugeCraftingTablee, y * 7 + x, 8 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, tileEntityHugeCraftingTablee, tileEntityHugeCraftingTablee, 49, 147, 72));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 158 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 216));
	}
}