package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.google.common.collect.Lists;
import joptsimple.internal.Strings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;
import wanion.lib.Reference;
import wanion.lib.WanionLib;
import wanion.lib.client.Button.RedstoneControlButton;
import wanion.lib.network.RedstoneControlButtonClick;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public abstract class GuiAutoBiggerCraftingTable extends GuiContainer
{
	private final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable;
	private final ResourceLocation guiTexture;
	private final int x, y;
	private RedstoneControlButton redstoneControlButton;

	public GuiAutoBiggerCraftingTable(@Nonnull final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable, @Nonnull final ResourceLocation guiTexture, @Nonnull final Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.tileEntityAutoBiggerCraftingTable = tileEntityAutoBiggerCraftingTable;
		this.guiTexture = guiTexture;
		final Slot slot = inventorySlots.getSlot(tileEntityAutoBiggerCraftingTable.full);
		x = slot.xPos;
		y = slot.yPos;
	}

	public void initGui()
	{
		super.initGui();
		redstoneControlButton = addButton(new RedstoneControlButton(this, getGuiLeft() + getXSize() - 25, getGuiTop() + getYSize() - 25, tileEntityAutoBiggerCraftingTable, 0));
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
		fontRenderer.drawString(I18n.format(tileEntityAutoBiggerCraftingTable.getName()), 7, 7, 0x404040);
		final Slot slot = inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 36);
		fontRenderer.drawString(I18n.format("container.inventory"), slot.xPos - 1, slot.yPos - 11, 0x404040);
		if (super.isPointInRegion(xSize - 25, ySize - 83, 18, 54, mouseX, mouseY))
			drawHoveringText(Lists.newArrayList(tileEntityAutoBiggerCraftingTable.getEnergyStored() + " / " + tileEntityAutoBiggerCraftingTable.getEnergyCapacity() + " FE", Strings.EMPTY, TextFormatting.GOLD + I18n.format("bigger.crafting.consumes", tileEntityAutoBiggerCraftingTable.powerConsumption), TextFormatting.GOLD + I18n.format("bigger.crafting.operation")), mouseX - guiLeft, mouseY - guiTop);
		for (final GuiButton guibutton : this.buttonList)
			if (guibutton.isMouseOver())
				guibutton.drawButtonForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		boolean smallGui = xSize < 256 && ySize < 256;
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize);
		mc.getTextureManager().bindTexture(Reference.GUI_TEXTURES);
		drawModalRectWithCustomSizedTexture(guiLeft + xSize - 25, guiTop + ySize - 83, 0, 0, 18, 54, 128, 128);
		final int size = scalePowerCentage();
		if (size != 0)
			drawModalRectWithCustomSizedTexture(guiLeft + xSize - 25, guiTop + ySize - 29 - size, 18, 54 - size, 18, size, 128, 128);
	}

	@Override
	protected final void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		if (super.isPointInRegion(this.x, this.y, 16, 16, x, y))
			drawClearRecipeToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}


	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if ((mouseButton == 0 || mouseButton == 1) && redstoneControlButton.mousePressed(this.mc, mouseX, mouseY))
			actionPerformed(mouseButton == 0);
		else super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	private void actionPerformed(final boolean leftClick)
	{
		redstoneControlButton.playPressSound(this.mc.getSoundHandler());
		WanionLib.networkWrapper.sendToServer(new RedstoneControlButtonClick(leftClick));
	}

	private void drawClearRecipeToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		toolTip.add("");
		toolTip.add(TextFormatting.GOLD + I18n.format("bigger.clear.shape"));
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}

	private int scalePowerCentage()
	{
		final int energyStored = tileEntityAutoBiggerCraftingTable.getEnergyStored();
		return energyStored != 0 ? (int) (54 * energyStored / (double) tileEntityAutoBiggerCraftingTable.getEnergyCapacity()) : 0;
	}
}