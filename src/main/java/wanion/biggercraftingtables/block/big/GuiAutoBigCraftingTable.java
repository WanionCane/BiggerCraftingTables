package wanion.biggercraftingtables.block.big;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

import static wanion.biggercraftingtables.Reference.MOD_ID;

@SideOnly(Side.CLIENT)
public final class GuiAutoBigCraftingTable extends GuiContainer
{
	private static final ResourceLocation autoBigCraftingTexture = new ResourceLocation(MOD_ID, "textures/gui/autobigcraftingtable.png");
	private final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable;

	public GuiAutoBigCraftingTable(@Nonnull final TileEntityAutoBigCraftingTable tileEntityAutoBigCraftingTable, final InventoryPlayer inventoryPlayer)
	{
		super(new ContainerAutoBigCraftingTable(tileEntityAutoBigCraftingTable, inventoryPlayer));
		this.tileEntityAutoBigCraftingTable = tileEntityAutoBigCraftingTable;
		xSize = 230;
		ySize = 204;
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
		fontRenderer.drawString(I18n.format("container.inventory"), 7, 111, 0x404040);
	}
}