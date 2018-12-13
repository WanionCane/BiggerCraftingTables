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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.ContainerBiggerCreatingTable;
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;
import wanion.biggercraftingtables.common.CTUtils;
import wanion.lib.common.IClickAction;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

@SideOnly(Side.CLIENT)
public final class CopyToClipBoardButton extends GuiButton implements IClickAction
{
	private final GuiBiggerCreatingTable guiBiggerCreatingTable;
	private final ResourceLocation resourceLocation = Reference.GUI_TEXTURES;

	public CopyToClipBoardButton(final int buttonId, @Nonnull final GuiBiggerCreatingTable guiBiggerCreatingTable, final int x, final int y)
	{
		super(buttonId, x, y, 8, 9, Strings.EMPTY);
		this.guiBiggerCreatingTable = guiBiggerCreatingTable;
	}

	public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (!this.visible)
			return;
		mc.getTextureManager().bindTexture(resourceLocation);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		drawModalRectWithCustomSizedTexture(x, y, !isMouseOver() ? 0 : 8, 47, width, height, 128, 128);
	}

	public void drawButtonForegroundLayer(final int mouseX, final int mouseY)
	{
		guiBiggerCreatingTable.drawHoveringText(TextFormatting.GOLD + I18n.format("bigger.creating.copy"), mouseX - guiBiggerCreatingTable.getGuiLeft(), mouseY - guiBiggerCreatingTable.getGuiTop());
	}

	@Override
	public void action(boolean b)
	{
		this.playPressSound(this.guiBiggerCreatingTable.mc.getSoundHandler());
		BiggerCraftingTables.proxy.getThreadListener().addScheduledTask(() -> {
			final StringSelection stringSelection = new StringSelection(CTUtils.toCTScript(((ContainerBiggerCreatingTable) guiBiggerCreatingTable.inventorySlots).getTileEntityBiggerCreatingTable()));
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		});
	}
}