package wanion.biggercraftingtables.block.giant;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.GuiAutoBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoGiantCraftingTable extends GuiAutoBiggerCraftingTable
{
	private static final ResourceLocation autoGiantCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/auto_giant_crafting_table.png");
	private final TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable;

	public GuiAutoGiantCraftingTable(@Nonnull final TileEntityAutoGiantCraftingTable tileEntityAutoGiantCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoGiantCraftingTable(tileEntityAutoGiantCraftingTable, inventoryPlayer));
		this.tileEntityAutoGiantCraftingTable = tileEntityAutoGiantCraftingTable;
		xSize = 374;
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
		mc.getTextureManager().bindTexture(autoGiantCraftingTexture);
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
		fontRenderer.drawString(I18n.format(tileEntityAutoGiantCraftingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), 106, 183, 0x404040);
	}

	@Override
	protected void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		if (super.isPointInRegion(346, 90, 16, 16, x, y))
			drawClearRecipeToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}
}