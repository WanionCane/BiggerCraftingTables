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
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.ContainerBiggerCreatingTable;
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;
import wanion.biggercraftingtables.block.TileEntityBiggerCreatingTable;
import wanion.biggercraftingtables.common.CTUtils;
import wanion.lib.client.ClientHelper;
import wanion.lib.common.IClickAction;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static wanion.biggercraftingtables.Reference.SLASH;

@SideOnly(Side.CLIENT)
public final class ExportToFileButton extends GuiButton implements IClickAction
{
	private final ResourceLocation resourceLocation = Reference.GUI_TEXTURES;
	private final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable;
	private final GuiBiggerCreatingTable guiBiggerCreatingTable;
	private final File scriptFile;
	private final List<String> baseDescription = new ArrayList<>();
	private final Slot outputSlot;
	private String script = Strings.EMPTY;
	private boolean success = false;

	public ExportToFileButton(final int buttonId, @Nonnull final GuiBiggerCreatingTable guiBiggerCreatingTable, final int x, final int y)
	{
		super(buttonId, x, y, 9, 9, Strings.EMPTY);
		this.guiBiggerCreatingTable = guiBiggerCreatingTable;
		tileEntityBiggerCreatingTable = ((ContainerBiggerCreatingTable) guiBiggerCreatingTable.inventorySlots).getTileEntityBiggerCreatingTable();
		String scriptName = "scripts" + SLASH + guiBiggerCreatingTable.getTileEntityBiggerCreatingTable().getTableType().getName() + "Recipes.zs";
		this.scriptFile = new File("." + SLASH + scriptName);
		this.baseDescription.add(TextFormatting.RED + I18n.format("bigger.creating.export") + " " + TextFormatting.GOLD + "“" + scriptName.replace('\\', '/') + "”");
		this.outputSlot = guiBiggerCreatingTable.inventorySlots.getSlot(guiBiggerCreatingTable.inventorySlots.inventorySlots.size() - 37);
	}

	public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (!this.visible)
			return;
		mc.getTextureManager().bindTexture(resourceLocation);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		if (success && !this.hovered)
			success = false;
		drawModalRectWithCustomSizedTexture(x, y, !isMouseOver() ? 0 : 9, 38, width, height, 128, 128);
	}

	public void drawButtonForegroundLayer(final int mouseX, final int mouseY)
	{
		final List<String> description = new ArrayList<>(this.baseDescription);
		if (!success) {
			final List<String> problems = new ArrayList<>();
			if (isEmpty())
				problems.add(I18n.format("bigger.creating.export.empty"));
			if (!outputSlot.getHasStack())
				problems.add(I18n.format("bigger.creating.export.no-output"));
			final String script = CTUtils.toCTScript(((ContainerBiggerCreatingTable) guiBiggerCreatingTable.inventorySlots).getTileEntityBiggerCreatingTable());
			if (this.script.equals(script))
				problems.add(I18n.format("bigger.creating.export.no-changes"));
			if (!problems.isEmpty()) {
				description.add(ClientHelper.getProblemFound(problems.size() > 1));
				description.addAll(problems);
			}
		} else
			description.add(ClientHelper.getSuccess());
		guiBiggerCreatingTable.drawHoveringText(description, mouseX - guiBiggerCreatingTable.getGuiLeft(), mouseY - guiBiggerCreatingTable.getGuiTop());
	}

	@Override
	public void action(final boolean b)
	{
		if (!b)
			return;
		if (success) {
			success = false;
			return;
		}
		if (isEmpty())
			return;
		final String script = CTUtils.toCTScript(((ContainerBiggerCreatingTable) guiBiggerCreatingTable.inventorySlots).getTileEntityBiggerCreatingTable());
		if (this.script != null && script != null && !this.script.equals(script)) {
			this.playPressSound(this.guiBiggerCreatingTable.mc.getSoundHandler());
			this.script = script;
			try {
				FileUtils.writeStringToFile(scriptFile, script, Charset.defaultCharset(), true);
				success = true;
			} catch (IOException ignored) {}
		}
	}

	private boolean isEmpty()
	{
		final int max = tileEntityBiggerCreatingTable.getSizeInventory() - 1;
		for (int i = 0; i < max; i++)
			if (!tileEntityBiggerCreatingTable.getStackInSlot(i).isEmpty())
				return false;
		return true;
	}
}