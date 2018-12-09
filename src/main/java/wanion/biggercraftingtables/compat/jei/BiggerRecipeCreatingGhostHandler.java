package wanion.biggercraftingtables.compat.jei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.block.GuiBiggerCreatingTable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BiggerRecipeCreatingGhostHandler<B extends GuiBiggerCreatingTable> implements IGhostIngredientHandler<B>
{
	@Override
	@Nonnull
	public <I> List<Target<I>> getTargets(@Nonnull final B gui, @Nonnull final I ingredient, final boolean doStart)
	{
		List<Target<I>> targets = new ArrayList<>();
		if (ingredient instanceof ItemStack) {
			for (int i = 0; i < gui.inventorySlots.inventorySlots.size() - 37; i++) {
				final Slot slot = gui.inventorySlots.getSlot(i);
				targets.add(new BiggerTarget<>(slot.slotNumber, new Rectangle(gui.getGuiLeft() + slot.xPos - 1, gui.getGuiTop() + slot.yPos - 1, 18, 18), gui));
			}
		}
		final Slot outputSlot = gui.inventorySlots.getSlot(gui.inventorySlots.inventorySlots.size() - 37);
		targets.add(new BiggerTarget<>(outputSlot.slotNumber, new Rectangle(gui.getGuiLeft() + outputSlot.xPos, gui.getGuiTop() + outputSlot.yPos, 16, 16), gui));
		return targets;
	}

	@Override
	public final void onComplete() {}
}