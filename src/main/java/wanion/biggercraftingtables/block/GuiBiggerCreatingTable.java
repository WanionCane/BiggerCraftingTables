package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.client.button.ClearShapeButton;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiBiggerCreatingTable extends GuiContainer
{
	private final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable;
	private final ResourceLocation guiTexture;
	private final Slot firstPlayerSlot = inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 36);
	private final Slot outputSlot = inventorySlots.getSlot(firstPlayerSlot.slotNumber - 1);
	private ClearShapeButton clearShapeButton;
	private List<String> description;

	public GuiBiggerCreatingTable(@Nonnull final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable, @Nonnull final ResourceLocation guiTexture, @Nonnull final Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.tileEntityBiggerCreatingTable = tileEntityBiggerCreatingTable;
		this.guiTexture = guiTexture;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		clearShapeButton = addButton(new ClearShapeButton(0, this, guiLeft + outputSlot.xPos + 3, guiTop + outputSlot.yPos + 24));
		description = Arrays.asList("",
				TextFormatting.GOLD + I18n.format("bigger.creating.usage"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line1"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line2"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line3"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line4"));
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
		fontRenderer.drawString(I18n.format(tileEntityBiggerCreatingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), firstPlayerSlot.xPos - 1, firstPlayerSlot.yPos - 11, 0x404040);
		for (final GuiButton guibutton : this.buttonList)
			if (guibutton.isMouseOver())
				guibutton.drawButtonForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		final boolean smallGui = xSize < 256 && ySize < 256;
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize, smallGui ? 256 : xSize > ySize ? xSize : ySize);
	}

	@Override
	protected final void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		if (super.isPointInRegion(outputSlot.xPos, outputSlot.yPos, 16, 16, x, y))
			drawOutputSlotToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}

	private void drawOutputSlotToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		toolTip.addAll(description);
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if ((mouseButton == 0 || mouseButton == 1) && clearShapeButton.mousePressed(this.mc, mouseX, mouseY))
			clearShapeButton.action(mouseButton == 0);
		else super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}