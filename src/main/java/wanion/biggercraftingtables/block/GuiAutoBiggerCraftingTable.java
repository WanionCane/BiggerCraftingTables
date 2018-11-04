package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class GuiAutoBiggerCraftingTable extends GuiContainer
{
	public GuiAutoBiggerCraftingTable(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	protected final void drawClearRecipeToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		toolTip.add("");
		toolTip.add(TextFormatting.GOLD + I18n.format("bigger.clear.shape"));
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}
}