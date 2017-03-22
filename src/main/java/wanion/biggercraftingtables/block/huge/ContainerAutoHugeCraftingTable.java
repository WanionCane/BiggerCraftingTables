package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;
import wanion.biggercraftingtables.inventory.DeadSlot;
import wanion.biggercraftingtables.inventory.ShapeSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoHugeCraftingTable extends ContainerAutoBiggerCraftingTable
{
	public ContainerAutoHugeCraftingTable(@Nonnull TileEntityAutoHugeCraftingTable tileEntityAutoHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoHugeCraftingTable);
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new Slot(tileEntityAutoHugeCraftingTable, y * 7 + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 7; y++)
			for (int x = 0; x < 7; x++)
				addSlotToContainer(new ShapeSlot(tileEntityAutoHugeCraftingTable, 49 + (y * 7 + x), 139 + (18 * x), 18 + (18 * y)));
		addSlotToContainer(new DeadSlot(tileEntityAutoHugeCraftingTable, 99, 274, 72));
		addSlotToContainer(new Slot(tileEntityAutoHugeCraftingTable, 98, 274, 100));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, 8 + (18 * x), 158 + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (18 * i), 216));
	}
}