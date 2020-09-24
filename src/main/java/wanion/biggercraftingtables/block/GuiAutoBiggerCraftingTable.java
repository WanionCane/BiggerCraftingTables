package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.lib.client.gui.EnergyElement;
import wanion.lib.client.gui.WGuiContainer;
import wanion.lib.common.WContainer;
import wanion.lib.common.control.energy.EnergyControl;
import wanion.lib.common.control.redstone.RedstoneControl;
import wanion.lib.common.control.redstone.RedstoneControlWButton;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiAutoBiggerCraftingTable<T extends TileEntityAutoBiggerCraftingTable<?>> extends WGuiContainer<T>
{
	private final int x, y;

	public GuiAutoBiggerCraftingTable(@Nonnull final WContainer<T> container, @Nonnull final ResourceLocation guiTexture, final int width, final int height)
	{
		super(container, guiTexture);
		xSize = width;
		ySize = height;
		final Slot slot = inventorySlots.getSlot(container.getTile().full);
		x = slot.xPos;
		y = slot.yPos;
		addElement(new RedstoneControlWButton(getControl(RedstoneControl.class),this, getGuiLeft() + getXSize() - 25, getGuiTop() + getYSize() - 25));
		addElement(new EnergyElement(getControl(EnergyControl.class),this, getGuiLeft() + getXSize() - 25, getGuiTop() + getYSize() - 83));
	}

	@Override
	protected final void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		if (super.isPointInRegion(this.x, this.y, 16, 16, x, y))
			drawClearRecipeToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}

	private void drawClearRecipeToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		final FontRenderer font = stack.getItem().getFontRenderer(stack);
		GuiUtils.preItemToolTip(stack);
		final List<String> toolTip = this.getItemToolTip(stack);
		toolTip.addAll(Arrays.asList("", TextFormatting.GOLD + I18n.format("bigger.clear.recipe")));
		drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
		GuiUtils.postItemToolTip();
	}
}