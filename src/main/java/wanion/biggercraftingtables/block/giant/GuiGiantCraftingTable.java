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
import wanion.biggercraftingtables.block.GuiBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiGiantCraftingTable extends GuiBiggerCraftingTable
{
	private static final ResourceLocation giantCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/giant_crafting_table.png");

	public GuiGiantCraftingTable(@Nonnull final TileEntityGiantCraftingTable tileEntityGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityGiantCraftingTable, giantCraftingTexture, new ContainerGiantCraftingTable(tileEntityGiantCraftingTable, inventoryPlayer));
		xSize = 211;
		ySize = 276;
	}
}