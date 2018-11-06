package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
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
public final class GuiGiantCraftingTable extends GuiContainer
{
	private static final ResourceLocation giantCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/giant_crafting_table.png");
	private final TileEntityGiantCraftingTable tileEntityGiantCraftingTable;

	public GuiGiantCraftingTable(@Nonnull final TileEntityGiantCraftingTable tileEntityGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerGiantCraftingTable(tileEntityGiantCraftingTable, inventoryPlayer));
		this.tileEntityGiantCraftingTable = tileEntityGiantCraftingTable;
		xSize = 211;
		ySize = 276;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(giantCraftingTexture);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(guiLeft, guiTop, 0.0D).tex(0.0D, 0).endVertex();
		bufferbuilder.pos(guiLeft, guiTop + ySize, 0.0D).tex(0, 1).endVertex();
		bufferbuilder.pos(guiLeft + ySize, guiTop + ySize, 0.0D).tex(1, 1).endVertex();
		bufferbuilder.pos(guiLeft + ySize, guiTop, 0.0D).tex(1, 0).endVertex();
		tessellator.draw();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		fontRenderer.drawString(I18n.format(tileEntityGiantCraftingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), 7, 183, 0x404040);
	}
}