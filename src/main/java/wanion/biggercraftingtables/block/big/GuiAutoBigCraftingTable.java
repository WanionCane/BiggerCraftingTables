package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

public final class GuiAutoBigCraftingTable extends GuiContainer
{
	private static final ResourceLocation autoBigCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/autoBigCraftingTable.png");
	private final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable;

	public GuiAutoBigCraftingTable(@Nonnull final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoBigCraftingTable(tileEntityAutoBigCraftingTable, inventoryPlayer));
		this.tileEntityAutoBigCraftingTable = tileEntityAutoBigCraftingTable;
		xSize = 230;
		ySize = 204;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(autoBigCraftingTexture);
		drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int p_146979_1_, final int p_146979_2_)
	{
		fontRendererObj.drawString(I18n.format(tileEntityAutoBigCraftingTable.getInventoryName()), 7, 7, 0x404040);
		fontRendererObj.drawString(I18n.format("container.inventory"), 7, 111, 0x404040);
	}
}