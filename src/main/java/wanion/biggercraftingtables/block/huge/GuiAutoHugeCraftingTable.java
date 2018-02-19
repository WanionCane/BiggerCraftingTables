package wanion.biggercraftingtables.block.huge;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoHugeCraftingTable extends GuiContainer
{
	private static final ResourceLocation autoHugeCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/auto_huge_crafting_table.png");
	private final TileEntityAutoHugeCraftingTable tileEntityAutoHugeCraftingTable;

	public GuiAutoHugeCraftingTable(@Nonnull final TileEntityAutoHugeCraftingTable tileEntityAutoHugeCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoHugeCraftingTable(tileEntityAutoHugeCraftingTable, inventoryPlayer));
		this.tileEntityAutoHugeCraftingTable = tileEntityAutoHugeCraftingTable;
		xSize = 302;
		ySize = 240;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(autoHugeCraftingTexture);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(guiLeft, guiTop, 0.0D).tex(0.0D, 0).endVertex();
		bufferbuilder.pos(guiLeft, guiTop + xSize, 0.0D).tex(0, 1).endVertex();
		bufferbuilder.pos(guiLeft + xSize, guiTop + xSize, 0.0D).tex(1, 1).endVertex();
		bufferbuilder.pos(guiLeft + xSize, guiTop, 0.0D).tex(1, 0).endVertex();
		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		fontRenderer.drawString(I18n.format(tileEntityAutoHugeCraftingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), 7, 147, 0x404040);
	}
}