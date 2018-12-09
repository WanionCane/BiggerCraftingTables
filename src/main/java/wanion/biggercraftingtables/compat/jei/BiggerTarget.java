package wanion.biggercraftingtables.compat.jei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.network.BiggerGhostTransferMessage;

import javax.annotation.Nonnull;
import java.awt.*;

public class BiggerTarget<I> implements IGhostIngredientHandler.Target<I>
{
	private final int slot;
	private final Rectangle rectangle;
	private final GuiContainer guiContainer;

	public BiggerTarget(final int slot, @Nonnull final Rectangle rectangle, @Nonnull final GuiContainer guiContainer)
	{
		this.slot = slot;
		this.rectangle = rectangle;
		this.guiContainer = guiContainer;
	}

	@Override
	@Nonnull
	public Rectangle getArea()
	{
		return rectangle;
	}

	@Override
	public void accept(@Nonnull final I ingredient)
	{
		if (ingredient instanceof ItemStack)
			BiggerCraftingTables.networkWrapper.sendToServer(new BiggerGhostTransferMessage(guiContainer.inventorySlots.windowId, slot, (ItemStack) ingredient));
	}
}