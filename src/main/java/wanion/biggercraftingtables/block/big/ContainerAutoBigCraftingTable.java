package wanion.biggercraftingtables.block.big;

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
import wanion.biggercraftingtables.recipe.big.BigRecipeRegistry;
import wanion.lib.inventory.slot.DeadSlot;
import wanion.lib.inventory.slot.ShapeSlot;
import wanion.lib.inventory.slot.SpecialSlot;

import javax.annotation.Nonnull;

public final class ContainerAutoBigCraftingTable extends ContainerAutoBiggerCraftingTable<TileEntityAutoBigCraftingTable>
{
	public ContainerAutoBigCraftingTable(@Nonnull final TileEntityAutoBigCraftingTable tileEntityAutoBiggerCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(slotList -> {
			for (int y = 0; y < 5; y++)
				for (int x = 0; x < 5; x++)
					slotList.add((new Slot(tileEntityAutoBiggerCraftingTable, y * 5 + x, 8 + (18 * x), 18 + (18 * y))));
			for (int y = 0; y < 5; y++)
				for (int x = 0; x < 5; x++)
					slotList.add((new ShapeSlot(tileEntityAutoBiggerCraftingTable, 25 + (y * 5 + x), 103 + (18 * x), 18 + (18 * y))));
			slotList.add((new DeadSlot(tileEntityAutoBiggerCraftingTable, 51, 202, 54)));
			slotList.add((new SpecialSlot(tileEntityAutoBiggerCraftingTable, 50, 202, 82)));
			for (int y = 0; y < 3; y++)
				for (int x = 0; x < 9; x++)
					slotList.add((new Slot(inventoryPlayer, 9 + y * 9 + x, 35 + (18 * x), 122 + (18 * y))));
			for (int i = 0; i < 9; i++)
				slotList.add((new Slot(inventoryPlayer, i, 35 + (18 * i), 180)));
		}, tileEntityAutoBiggerCraftingTable);
	}
}