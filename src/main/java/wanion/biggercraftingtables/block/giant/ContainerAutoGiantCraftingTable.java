package wanion.biggercraftingtables.block.giant;

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
import wanion.biggercraftingtables.recipe.giant.GiantRecipeRegistry;
import wanion.lib.inventory.slot.DeadSlot;
import wanion.lib.inventory.slot.ShapeSlot;
import wanion.lib.inventory.slot.SpecialSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoGiantCraftingTable extends ContainerAutoBiggerCraftingTable<TileEntityAutoGiantCraftingTable>
{
	public ContainerAutoGiantCraftingTable(@Nonnull final TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(slotList -> {
			for (int y = 0; y < 9; y++)
				for (int x = 0; x < 9; x++)
					slotList.add((new Slot(tileEntityAutoGiantCraftingTable, y * 9 + x, 8 + (18 * x), 18 + (18 * y))));
			for (int y = 0; y < 9; y++)
				for (int x = 0; x < 9; x++)
					slotList.add((new ShapeSlot(tileEntityAutoGiantCraftingTable, 81 + (y * 9 + x), 175 + (18 * x), 18 + (18 * y))));
			slotList.add((new DeadSlot(tileEntityAutoGiantCraftingTable, 163, 346, 90)));
			slotList.add((new SpecialSlot(tileEntityAutoGiantCraftingTable, 162, 346, 118)));
			for (int y = 0; y < 3; y++)
				for (int x = 0; x < 9; x++)
					slotList.add((new Slot(inventoryPlayer, 9 + y * 9 + x, 107 + (18 * x), 194 + (18 * y))));
			for (int i = 0; i < 9; i++)
				slotList.add((new Slot(inventoryPlayer, i, 107 + (18 * i), 252)));
		}, tileEntityAutoGiantCraftingTable);
	}
}