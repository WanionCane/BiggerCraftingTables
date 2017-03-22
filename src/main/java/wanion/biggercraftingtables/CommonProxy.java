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
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityAutoBigCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityAutoHugeCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityHugeCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.core.GuiHandler;
import wanion.biggercraftingtables.minetweaker.Tweaker;

import static wanion.biggercraftingtables.Reference.MOD_ID;

public class CommonProxy
{
	public final void preInit()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(BiggerCraftingTables.instance, GuiHandler.instance);
		GameRegistry.registerBlock(BlockBiggerCraftingTable.instance, ItemBlockBiggerCraftingTable.class, "BiggerCraftingTables");
		GameRegistry.registerBlock(BlockAutoBiggerCraftingTable.instance, ItemBlockAutoBiggerCraftingTable.class, "AutoBiggerCraftingTables");
		GameRegistry.registerTileEntity(TileEntityBigCraftingTable.class, MOD_ID + ":BigTable");
		GameRegistry.registerTileEntity(TileEntityHugeCraftingTable.class, MOD_ID + ":HugeTable");
		GameRegistry.registerTileEntity(TileEntityAutoBigCraftingTable.class, MOD_ID + ":AutoBigTable");
		GameRegistry.registerTileEntity(TileEntityAutoHugeCraftingTable.class, MOD_ID + ":AutoHugeTable");
	}

	public void postInit()
	{
		if (Loader.isModLoaded("MineTweaker3"))
			Tweaker.init();
	}
}