package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import joptsimple.internal.Strings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.client.button.ClearShapeButton;
import wanion.biggercraftingtables.client.button.CopyToClipBoardButton;
import wanion.biggercraftingtables.client.button.ExportToFileButton;
import wanion.biggercraftingtables.client.button.ShapeControlWButton;
import wanion.biggercraftingtables.common.control.ShapeControl;
import wanion.lib.client.ClientHelper;
import wanion.lib.client.gui.WGuiContainer;
import wanion.lib.common.IClickAction;
import wanion.lib.common.WContainer;
import wanion.lib.common.matching.AbstractMatching;
import wanion.lib.common.matching.Matching;
import wanion.lib.inventory.slot.MatchingSlot;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiBiggerCreatingTable<T extends TileEntityBiggerCreatingTable<?>> extends WGuiContainer<T>
{
	private final Slot firstPlayerSlot = inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 36);
	private final Slot outputSlot = inventorySlots.getSlot(firstPlayerSlot.slotNumber - 1);
	private final List<String> matchingDescription;
	private final List<String> outputDescription;
	private final ShapeControlWButton shapeControlWButton;

	public GuiBiggerCreatingTable(@Nonnull final WContainer<T> container, @Nonnull final ResourceLocation guiTexture)
	{
		super(container, guiTexture);
		matchingDescription = Arrays.asList(
				ClientHelper.getHowToUse(),
				TextFormatting.GRAY + I18n.format("wanionlib.matching.desc"));
		outputDescription = Arrays.asList("",
				TextFormatting.GOLD + I18n.format("bigger.creating.usage"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line1"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line2"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line3"),
				TextFormatting.GRAY + I18n.format("bigger.creating.usage.desc.line4"));
		addElement((shapeControlWButton = new ShapeControlWButton(getControl(ShapeControl.class), this, guiLeft + outputSlot.xPos - 10, guiTop + outputSlot.yPos - 28)));
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addButton(new ClearShapeButton(1, this, shapeControlWButton.getUsableX() + 22, shapeControlWButton.getUsableY() + 5));
		addButton(new ExportToFileButton(2, this, guiLeft + outputSlot.xPos - 1, guiTop + outputSlot.yPos + 23));
		addButton(new CopyToClipBoardButton(3, this, guiLeft + outputSlot.xPos + 9, guiTop + outputSlot.yPos + 23));
	}

	@Override
	protected final void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final MatchingSlot matchingSlot = findSlot(x, y);
		if (matchingSlot != null)
			drawMatchingTooltip(matchingSlot, stack, x, y);
		else if (super.isPointInRegion(outputSlot.xPos, outputSlot.yPos, 16, 16, x, y))
			drawOutputSlotToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}

	private MatchingSlot findSlot(final int x, final int y)
	{
		final int max = inventorySlots.inventorySlots.size() - 37;
		for (int i = 0; i < max; i++) {
			final Slot slot = inventorySlots.getSlot(i);
			if (slot instanceof MatchingSlot && super.isPointInRegion(slot.xPos, slot.yPos, 16, 16, x, y))
				return (MatchingSlot) slot;
		}
		return null;
	}

	private void drawMatchingTooltip(@Nonnull final MatchingSlot matchingSlot, @Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		final AbstractMatching<?> matching = matchingSlot.getMatching();
		toolTip.add(Strings.EMPTY);
		toolTip.add(TextFormatting.RED + I18n.format(matching.getControlName()) + ": " + TextFormatting.WHITE + matching.getMatcher().getDescription());
		toolTip.addAll(matchingDescription);
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}

	private void drawOutputSlotToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		toolTip.addAll(outputDescription);
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if (mouseButton == 0 || mouseButton == 1)
			buttonList.stream().filter(IClickAction.class::isInstance).forEach(button -> {
				if (button.mousePressed(this.mc, mouseX, mouseY))
					((IClickAction) button).action(mouseButton == 0);
			});
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}