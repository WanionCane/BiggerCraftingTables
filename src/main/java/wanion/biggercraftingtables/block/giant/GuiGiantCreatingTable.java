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
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiGiantCreatingTable extends GuiBiggerCreatingTable<TileEntityGiantCreatingTable>
{
	private static final ResourceLocation giantCreatingTexture = new ResourceLocation(MOD_ID, "textures/gui/giant_crafting_table.png");

	public GuiGiantCreatingTable(@Nonnull final TileEntityGiantCreatingTable tileEntityGiantCreatingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerGiantCreatingTable(tileEntityGiantCreatingTable, inventoryPlayer), giantCreatingTexture);
		xSize = 211;
		ySize = 276;
	}
}