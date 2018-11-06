package wanion.biggercraftingtables.proxy;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityAutoBigCraftingTable;
import wanion.biggercraftingtables.block.big.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.giant.TileEntityAutoGiantCraftingTable;
import wanion.biggercraftingtables.block.giant.TileEntityGiantCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityAutoHugeCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityHugeCraftingTable;
import wanion.biggercraftingtables.core.GuiHandler;
import wanion.biggercraftingtables.network.BiggerAutoCraftingJeiTransfer;

import static wanion.biggercraftingtables.BiggerCraftingTables.networkWrapper;
import static wanion.biggercraftingtables.Reference.MOD_ID;

public class CommonProxy
{
	public final void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(BiggerCraftingTables.instance, GuiHandler.instance);
		GameRegistry.registerTileEntity(TileEntityBigCraftingTable.class, new ResourceLocation(MOD_ID, "bigtable"));
		GameRegistry.registerTileEntity(TileEntityAutoBigCraftingTable.class, new ResourceLocation(MOD_ID, "autobigTable"));
		GameRegistry.registerTileEntity(TileEntityHugeCraftingTable.class, new ResourceLocation(MOD_ID, "hugetable"));
		GameRegistry.registerTileEntity(TileEntityAutoHugeCraftingTable.class, new ResourceLocation(MOD_ID, "autohugetable"));
		GameRegistry.registerTileEntity(TileEntityGiantCraftingTable.class, new ResourceLocation(MOD_ID, "gianttable"));
		GameRegistry.registerTileEntity(TileEntityAutoGiantCraftingTable.class, new ResourceLocation(MOD_ID, "autogianttable"));
		networkWrapper.registerMessage(BiggerAutoCraftingJeiTransfer.Handler.class, BiggerAutoCraftingJeiTransfer.class, 0, Side.SERVER);
		networkWrapper.registerMessage(BiggerAutoCraftingJeiTransfer.Handler.class, BiggerAutoCraftingJeiTransfer.class, 1, Side.CLIENT);

		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, "4CraftingTableToBigCraftingTable"), null, new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1), "CC", "CC", 'C', Blocks.CRAFTING_TABLE);
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "bigCraftingTableToAutoBigCraftingTable"), null, new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE)));
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, "4BigCraftingTableToHugeCraftingTable"), null, new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1), "CC", "CC", 'C', new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1));
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "hugeCraftingTableToAutoHugeCraftingTable"), null, new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 1), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1)));
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, "4HugeCraftingTableToGiantCraftingTable"), null, new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 2), "CC", "CC", 'C', new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1));
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "giantCraftingTableToAutoGiantCraftingTable"), null, new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 2), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 2)));
	}

	public void init() {}

	public void postInit() {}

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
	public void modelRegistryEvent(final ModelRegistryEvent event)
	{
		modelInit();
	}

	public void modelInit() {}
}