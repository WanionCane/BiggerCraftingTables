package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import wanion.biggercraftingtables.block.BigCraftingTable.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTables;
import wanion.biggercraftingtables.block.HugeCraftingTable.TileEntityHugeCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTables;
import wanion.biggercraftingtables.core.GuiHandler;

import static wanion.biggercraftingtables.Reference.MOD_ID;

public class CommonProxy
{
	public static boolean nei;
	public static boolean mineTweaker;

	public void preInit()
	{
		nei = Loader.isModLoaded("NotEnoughItems");
		mineTweaker = Loader.isModLoaded("MineTweaker3");
		NetworkRegistry.INSTANCE.registerGuiHandler(BiggerCraftingTables.instance, GuiHandler.instance);
		GameRegistry.registerBlock(BlockBiggerCraftingTables.instance, ItemBlockBiggerCraftingTables.class, "BiggerCraftingTables");
		GameRegistry.registerTileEntity(TileEntityBigCraftingTable.class, MOD_ID + ":BigTable");
		GameRegistry.registerTileEntity(TileEntityHugeCraftingTable.class, MOD_ID + ":HugeTable");
	}
}