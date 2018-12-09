package wanion.biggercraftingtables.client.button;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.network.ClearShapeMessage;
import wanion.lib.Reference;
import wanion.lib.common.IClickAction;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public final class ClearShapeButton extends GuiButton implements IClickAction
{
	private final GuiContainer guiContainer;
	private final ResourceLocation resourceLocation = Reference.GUI_TEXTURES;

	public ClearShapeButton(final int buttonId, @Nonnull final GuiContainer guiContainer, final int x, final int y)
	{
		super(buttonId, x, y, 10, 9, Strings.EMPTY);
		this.guiContainer = guiContainer;
	}

	public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (!this.visible)
			return;
		mc.getTextureManager().bindTexture(resourceLocation);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		drawModalRectWithCustomSizedTexture(x, y, !isMouseOver() ? 108 : 118, 0, 10, 9, 128, 128);
	}

	public void drawButtonForegroundLayer(final int mouseX, final int mouseY)
	{
		guiContainer.drawHoveringText(TextFormatting.GOLD + I18n.format("bigger.clear.shape"), mouseX - guiContainer.getGuiLeft(), mouseY - guiContainer.getGuiTop());
	}

	@Override
	public void action(boolean b)
	{
		this.playPressSound(this.guiContainer.mc.getSoundHandler());
		BiggerCraftingTables.networkWrapper.sendToServer(new ClearShapeMessage(guiContainer.inventorySlots.windowId));
	}
}