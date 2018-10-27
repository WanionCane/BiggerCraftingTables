package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.ItemBlockBiggerCraftingTable;
import wanion.biggercraftingtables.proxy.CommonProxy;

import java.util.Map;

import static wanion.biggercraftingtables.Reference.*;

@SuppressWarnings("unused")
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = DEPENDENCIES, acceptedMinecraftVersions = TARGET_MC_VERSION)
public class BiggerCraftingTables
{
	@Mod.Instance
	public static BiggerCraftingTables instance;

	public static final int GUI_ID_BIG_CRAFTING_TABLE = 0, GUI_ID_HUGE_CRAFTING_TABLE = 1, GUI_ID_AUTO_BIG_CRAFTING_TABLE = 2, GUI_ID_AUTO_HUGE_CRAFTING_TABLE = 3;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs creativeTabs = new CreativeTabs(MOD_ID)
	{
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlockBiggerCraftingTable.INSTANCE, 1, 0);
		}
	};

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event)
	{
		proxy.preInit();
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, "4CraftingTableToBigCraftingTable"), null, new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1), "CC", "CC", 'C', Blocks.CRAFTING_TABLE);
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, "4BigCraftingTableToHugeCraftingTable"), null, new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1), "CC", "CC", 'C', new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1));
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "bigCraftingTableToAutoBigCraftingTable"), null, new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE)));
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "hugeCraftingTableToAutoHugeCraftingTable"), null, new ItemStack(ItemBlockAutoBiggerCraftingTable.INSTANCE, 1, 1), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(ItemBlockBiggerCraftingTable.INSTANCE, 1, 1)));
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event)
	{
		proxy.init();
	}

	@NetworkCheckHandler
	public boolean matchModVersions(final Map<String, String> remoteVersions, final Side side)
	{
		return side == Side.CLIENT ? remoteVersions.containsKey(MOD_ID) : !remoteVersions.containsKey(MOD_ID) || remoteVersions.get(MOD_ID).equals(MOD_VERSION);
	}
}