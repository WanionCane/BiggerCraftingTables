package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class GuiBiggerCraftingTable extends GuiContainer
{
	private final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable;
	private final ResourceLocation guiTexture;
	private final int x, y;

	public GuiBiggerCraftingTable(@Nonnull final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable, @Nonnull final ResourceLocation guiTexture, @Nonnull final Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.tileEntityBiggerCraftingTable = tileEntityBiggerCraftingTable;
		this.guiTexture = guiTexture;
		final Slot slot = inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 36);
		this.x = slot.xPos - 1;
		this.y = slot.yPos - 11;
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		fontRenderer.drawString(I18n.format(tileEntityBiggerCraftingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), x, y, 0x404040);
	}

	@Override
	protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		final boolean smallGui = xSize < 256 && ySize < 256;
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize);
	}
}