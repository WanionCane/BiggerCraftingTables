package wanion.biggercraftingtables.core;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.block.big.*;
import wanion.biggercraftingtables.block.huge.*;

public final class GuiHandler implements IGuiHandler
{
	public static GuiHandler instance = new GuiHandler();

	private GuiHandler() {}

	@Override
	public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if (tileEntity == null)
			return null;
		switch (ID) {
			case BiggerCraftingTables.GUI_ID_BIG_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityBigCraftingTable)
					return new ContainerBigCraftingTable((TileEntityBigCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_HUGE_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityHugeCraftingTable)
					return new ContainerHugeCraftingTable((TileEntityHugeCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_AUTO_BIG_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityAutoBigCraftingTable)
					return new ContainerAutoBigCraftingTable((TileEntityAutoBigCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_AUTO_HUGE_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityAutoHugeCraftingTable)
					return new ContainerAutoHugeCraftingTable((TileEntityAutoHugeCraftingTable) tileEntity, player.inventory);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if (tileEntity == null)
			return null;
		switch (ID) {
			case BiggerCraftingTables.GUI_ID_BIG_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityBigCraftingTable)
					return new GuiBigCraftingTable((TileEntityBigCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_HUGE_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityHugeCraftingTable)
					return new GuiHugeCraftingTable((TileEntityHugeCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_AUTO_BIG_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityAutoBigCraftingTable)
					return new GuiAutoBigCraftingTable((TileEntityAutoBigCraftingTable) tileEntity, player.inventory);
			case BiggerCraftingTables.GUI_ID_AUTO_HUGE_CRAFTING_TABLE:
				if (tileEntity instanceof TileEntityAutoHugeCraftingTable)
					return new GuiAutoHugeCraftingTable((TileEntityAutoHugeCraftingTable) tileEntity, player.inventory);
			default:
				return null;
		}
	}
}