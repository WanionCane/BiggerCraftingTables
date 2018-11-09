package wanion.biggercraftingtables.block.giant;

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
import wanion.biggercraftingtables.block.GuiAutoBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoGiantCraftingTable extends GuiAutoBiggerCraftingTable
{
	private static final ResourceLocation autoGiantCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/auto_giant_crafting_table.png");

	public GuiAutoGiantCraftingTable(@Nonnull final TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityAutoGiantCraftingTable, autoGiantCraftingTexture, new ContainerAutoGiantCraftingTable(tileEntityAutoGiantCraftingTable, inventoryPlayer));
		xSize = 374;
		ySize = 276;
	}
}