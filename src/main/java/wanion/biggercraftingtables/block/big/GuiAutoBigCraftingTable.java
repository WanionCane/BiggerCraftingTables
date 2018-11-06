package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import wanion.biggercraftingtables.block.GuiAutoBiggerCraftingTable;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoBigCraftingTable extends GuiAutoBiggerCraftingTable
{
	private static final ResourceLocation autoBigCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/auto_big_crafting_table.png");
	private final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable;

	public GuiAutoBigCraftingTable(@Nonnull final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoBigCraftingTable(tileEntityAutoBigCraftingTable, inventoryPlayer));
		this.tileEntityAutoBigCraftingTable = tileEntityAutoBigCraftingTable;
		xSize = 230;
		ySize = 204;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(autoBigCraftingTexture);
		drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		fontRenderer.drawString(I18n.format(tileEntityAutoBigCraftingTable.getName()), 7, 7, 0x404040);
		fontRenderer.drawString(I18n.format("container.inventory"), 34, 111, 0x404040);
	}

	@Override
	protected void renderToolTip(@Nonnull final ItemStack stack, final int x, final int y)
	{
		if (super.isPointInRegion(202, 54, 16, 16, x, y))
			drawClearRecipeToolTip(stack, x, y);
		else super.renderToolTip(stack, x, y);
	}
}