package wanion.biggercraftingtables.client.button;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import wanion.biggercraftingtables.common.control.ShapeControl;
import wanion.lib.client.gui.WGuiContainer;
import wanion.lib.client.gui.button.ControlWButton;

import javax.annotation.Nonnull;

public final class ShapeControlWButton extends ControlWButton<ShapeControl, ShapeControl.ShapeState>
{
	public ShapeControlWButton(@Nonnull final ShapeControl shapeControl, @Nonnull final WGuiContainer<?> wGuiContainer, final int x, final int y)
	{
		super(shapeControl, wGuiContainer, x, y, 19, 19);
	}
}