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
import wanion.biggercraftingtables.block.GuiAutoBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoHugeCraftingTable extends GuiAutoBiggerCraftingTable<TileEntityAutoHugeCraftingTable>
{
	private static final ResourceLocation autoHugeCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/auto_huge_crafting_table.png");

	public GuiAutoHugeCraftingTable(@Nonnull final TileEntityAutoHugeCraftingTable tileEntityAutoHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoHugeCraftingTable(tileEntityAutoHugeCraftingTable, inventoryPlayer), autoHugeCraftingTexture, 302, 240);
	}
}