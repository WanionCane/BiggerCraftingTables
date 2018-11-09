package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;
import wanion.biggercraftingtables.proxy.CommonProxy;

import java.util.Map;

import static wanion.biggercraftingtables.Reference.*;

@SuppressWarnings("unused")
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = DEPENDENCIES, acceptedMinecraftVersions = TARGET_MC_VERSION)
public class BiggerCraftingTables
{
	public static final int
			GUI_ID_BIG_CRAFTING_TABLE = 0,
			GUI_ID_AUTO_BIG_CRAFTING_TABLE = 1,
			GUI_ID_HUGE_CRAFTING_TABLE = 2,
			GUI_ID_AUTO_HUGE_CRAFTING_TABLE = 3,
			GUI_ID_GIANT_CRAFTING_TABLE = 4,
			GUI_ID_AUTO_GIANT_CRAFTING_TABLE = 5;

	public static final CreativeTabs creativeTabs = new CreativeTabs(MOD_ID)
	{
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlockBiggerCraftingTable.INSTANCE, 1, 0);
		}
	};

	@Mod.Instance
	public static BiggerCraftingTables instance;
	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper networkWrapper;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event)
	{
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
		proxy.preInit();
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