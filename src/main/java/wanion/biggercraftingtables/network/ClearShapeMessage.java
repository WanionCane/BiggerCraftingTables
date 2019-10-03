package wanion.biggercraftingtables.network;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.lib.common.IShapedContainer;
import wanion.lib.network.EmptyMessage;

public class ClearShapeMessage extends EmptyMessage
{
	public ClearShapeMessage() {}

	public ClearShapeMessage(final int windowId)
	{
		super(windowId);
	}

	public static class Handler implements IMessageHandler<ClearShapeMessage, IMessage>
	{
		@Override
		public IMessage onMessage(final ClearShapeMessage message, final MessageContext ctx)
		{
			BiggerCraftingTables.proxy.getThreadListener().addScheduledTask(() -> {
				final EntityPlayer entityPlayer = BiggerCraftingTables.proxy.getEntityPlayerFromContext(ctx);
				if (entityPlayer != null && entityPlayer.openContainer instanceof IShapedContainer && entityPlayer.openContainer.windowId == message.windowId)
					((IShapedContainer) entityPlayer.openContainer).clearShape();
			});
			return null;
		}
	}
}