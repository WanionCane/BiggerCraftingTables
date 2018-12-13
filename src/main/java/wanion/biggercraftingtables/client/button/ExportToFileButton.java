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
import org.apache.commons.io.FileUtils;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.ContainerBiggerCreatingTable;
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;
import wanion.biggercraftingtables.common.CTUtils;
import wanion.lib.common.IClickAction;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static wanion.biggercraftingtables.Reference.SLASH;

@SideOnly(Side.CLIENT)
public final class ExportToFileButton extends GuiButton implements IClickAction
{
	private final GuiBiggerCreatingTable guiBiggerCreatingTable;
	private final ResourceLocation resourceLocation = Reference.GUI_TEXTURES;
	private final File scriptFile;
	private final String scriptName;
	private String script = Strings.EMPTY;


	public ExportToFileButton(final int buttonId, @Nonnull final GuiBiggerCreatingTable guiBiggerCreatingTable, final int x, final int y)
	{
		super(buttonId, x, y, 9, 9, Strings.EMPTY);
		this.guiBiggerCreatingTable = guiBiggerCreatingTable;
		this.scriptName = "scripts" + SLASH + guiBiggerCreatingTable.getTileEntityBiggerCreatingTable().getTableType().getName() + "Recipes.zs";
		this.scriptFile = new File("." + SLASH + scriptName);
	}

	public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (!this.visible)
			return;
		mc.getTextureManager().bindTexture(resourceLocation);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		drawModalRectWithCustomSizedTexture(x, y, !isMouseOver() ? 0 : 9, 38, width, height, 128, 128);
	}

	public void drawButtonForegroundLayer(final int mouseX, final int mouseY)
	{
		guiBiggerCreatingTable.drawHoveringText(TextFormatting.RED + I18n.format("bigger.creating.export") + " " + TextFormatting.GOLD + scriptName, mouseX - guiBiggerCreatingTable.getGuiLeft(), mouseY - guiBiggerCreatingTable.getGuiTop());
	}

	@Override
	public void action(boolean b)
	{
		final String script = CTUtils.toCTScript(((ContainerBiggerCreatingTable) guiBiggerCreatingTable.inventorySlots).getTileEntityBiggerCreatingTable());
		if (this.script != null && script != null && !this.script.equals(script)) {
			this.playPressSound(this.guiBiggerCreatingTable.mc.getSoundHandler());
			this.script = script;
			try {
				FileUtils.writeStringToFile(scriptFile, script, Charset.defaultCharset(), true);
			} catch (IOException ignored) {}
		}
	}
}