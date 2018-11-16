package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.block.GuiBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiHugeCraftingTable extends GuiBiggerCraftingTable
{
	private static final ResourceLocation hugeCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/huge_crafting_table.png");

	public GuiHugeCraftingTable(@Nonnull final TileEntityHugeCraftingTable tileEntityHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityHugeCraftingTable, hugeCraftingTexture, new ContainerHugeCraftingTable(tileEntityHugeCraftingTable, inventoryPlayer));
		xSize = 176;
		ySize = 240;
	}
}