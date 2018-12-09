package wanion.biggercraftingtables.client.button;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.gui.inventory.GuiContainer;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.common.control.ShapeControl;
import wanion.lib.client.button.ActionButton;

import javax.annotation.Nonnull;

public final class ShapeControlButton extends ActionButton<ShapeControl, ShapeControl.ShapeState>
{
	public ShapeControlButton(@Nonnull final GuiContainer guiContainer, final int x, final int y, @Nonnull final ShapeControl control, int buttonId)
	{
		super(guiContainer, control, Reference.GUI_TEXTURES, buttonId, x, y, 19, 19);
	}
}