package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.lib.client.gui.WGuiContainer;
import wanion.lib.common.WContainer;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public abstract class GuiBiggerCraftingTable<T extends TileEntityBiggerCraftingTable> extends WGuiContainer<T>
{
	public GuiBiggerCraftingTable(@Nonnull final WContainer<T> container, @Nonnull final ResourceLocation guiTexture)
	{
		super(container, guiTexture);
	}
}