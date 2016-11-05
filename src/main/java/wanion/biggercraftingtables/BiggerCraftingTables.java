package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTables;

import java.util.Map;

import static wanion.biggercraftingtables.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, acceptedMinecraftVersions = TARGET_MC_VERSION)
public class BiggerCraftingTables
{
	@Mod.Instance
	public static BiggerCraftingTables instance;

	public static final int GUI_ID_BIG_CRAFTING_TABLE = 0, GUI_ID_HUGE_CRAFTING_TABLE = 1;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs creativeTabs = new CreativeTabs(MOD_ID)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(BlockBiggerCraftingTables.instance);
		}
	};

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event)
	{
		proxy.preInit();
		GameRegistry.addShapedRecipe(new ItemStack(BlockBiggerCraftingTables.instance, 0, 1), "CC", "CC", 'C', Blocks.crafting_table);
		GameRegistry.addShapedRecipe(new ItemStack(BlockBiggerCraftingTables.instance, 1, 1), "CC", "CC", 'C', new ItemStack(BlockBiggerCraftingTables.instance, 0, 1));
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}

	@NetworkCheckHandler
	public boolean matchModVersions(final Map<String, String> remoteVersions, final Side side)
	{
		return side == Side.CLIENT ? remoteVersions.containsKey(MOD_ID) : !remoteVersions.containsKey(MOD_ID) || remoteVersions.get(MOD_ID).equals(MOD_VERSION);
	}
}