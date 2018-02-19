package wanion.biggercraftingtables.proxy;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityAutoBigCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityAutoHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityHugeCraftingTable;
import wanion.biggercraftingtables.core.GuiHandler;

import static wanion.biggercraftingtables.Reference.MOD_ID;

public class CommonProxy
{
	public final void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(BiggerCraftingTables.instance, GuiHandler.instance);
		GameRegistry.registerTileEntity(TileEntityBigCraftingTable.class, MOD_ID + ":bigtable");
		GameRegistry.registerTileEntity(TileEntityHugeCraftingTable.class, MOD_ID + ":hugetable");
		GameRegistry.registerTileEntity(TileEntityAutoBigCraftingTable.class, MOD_ID + ":autobigTable");
		GameRegistry.registerTileEntity(TileEntityAutoHugeCraftingTable.class, MOD_ID + ":autohugetable");
	}

	public void init() {}

	public void postInit()
	{
	}

	@SubscribeEvent
	public void registerItems(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemBlockBiggerCraftingTable.INSTANCE, ItemBlockAutoBiggerCraftingTable.INSTANCE);
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockBiggerCraftingTable.INSTANCE, BlockAutoBiggerCraftingTable.INSTANCE);
	}

	@SubscribeEvent
	public void modelRegistryEvent(final ModelRegistryEvent event) {
		modelInit();
	}

	public void modelInit() {}

}