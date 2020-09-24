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
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;
import wanion.biggercraftingtables.block.TileEntityBiggerCreatingTable;
import wanion.biggercraftingtables.common.CTUtils;
import wanion.lib.client.ClientHelper;
import wanion.lib.common.IClickAction;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public final class CopyToClipBoardButton extends GuiButton implements IClickAction
{
	private final ResourceLocation resourceLocation = Reference.GUI_TEXTURES;
	private final TileEntityBiggerCreatingTable<?> tileEntityBiggerCreatingTable;
	private final GuiBiggerCreatingTable<? extends TileEntityBiggerCreatingTable<?>> guiBiggerCreatingTable;
	private final List<String> baseDescription = new ArrayList<>();
	private final Slot outputSlot;
	private boolean success = false;

	public CopyToClipBoardButton(final int buttonId, @Nonnull final GuiBiggerCreatingTable<? extends TileEntityBiggerCreatingTable<?>> guiBiggerCreatingTable, final int x, final int y)
	{
		super(buttonId, x, y, 8, 9, Strings.EMPTY);
		tileEntityBiggerCreatingTable = guiBiggerCreatingTable.getContainer().getTile();
		this.guiBiggerCreatingTable = guiBiggerCreatingTable;
		this.baseDescription.add(TextFormatting.GOLD + I18n.format("bigger.creating.copy"));
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
		drawModalRectWithCustomSizedTexture(x, y, !isMouseOver() ? 0 : 8, 47, width, height, 128, 128);
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
			if (!problems.isEmpty()) {
				description.add(ClientHelper.getProblemFound(problems.size() > 1));
				description.addAll(problems);
			}
		} else
			description.add(ClientHelper.getSuccess());
		guiBiggerCreatingTable.drawHoveringText(description, mouseX - guiBiggerCreatingTable.getGuiLeft(), mouseY - guiBiggerCreatingTable.getGuiTop());
	}

	@Override
	public void action(boolean b)
	{
		if (isEmpty() || !outputSlot.getHasStack())
			return;
		this.playPressSound(this.guiBiggerCreatingTable.mc.getSoundHandler());
		success = true;
		BiggerCraftingTables.proxy.getThreadListener().addScheduledTask(() -> {
			final StringSelection stringSelection = new StringSelection(CTUtils.toCTScript(tileEntityBiggerCreatingTable));
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		});
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