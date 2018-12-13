package wanion.biggercraftingtables.network;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import io.netty.buffer.ByteBuf;
import joptsimple.internal.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.block.ContainerBiggerCreatingTable;
import wanion.biggercraftingtables.common.CTUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyToClipBoardMessage implements IMessage
{
	private int windowId;
	private String script;

	public CopyToClipBoardMessage() {}

	public CopyToClipBoardMessage(final int windowId)
	{
		this.windowId = windowId;
	}

	public CopyToClipBoardMessage(final int windowId, final String script)
	{
		this.windowId = windowId;
		this.script = script;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.windowId = ByteBufUtils.readVarInt(buf, 5);
		this.script = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, windowId, 5);
		ByteBufUtils.writeUTF8String(buf, script != null ? script : Strings.EMPTY);
	}

	public static class Handler implements IMessageHandler<CopyToClipBoardMessage, CopyToClipBoardMessage>
	{
		@Override
		public CopyToClipBoardMessage onMessage(final CopyToClipBoardMessage message, final MessageContext ctx)
		{
			final EntityPlayer entityPlayer = BiggerCraftingTables.proxy.getEntityPlayerFromContext(ctx);
			if (entityPlayer.openContainer instanceof ContainerBiggerCreatingTable && entityPlayer.openContainer.windowId == message.windowId) {
				if (ctx.side.isClient()) {
					BiggerCraftingTables.proxy.getThreadListener().addScheduledTask(() -> {
						final StringSelection stringSelection = new StringSelection(message.script);
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
					});
				}
				else if (entityPlayer instanceof EntityPlayerMP)
					return new CopyToClipBoardMessage(message.windowId, CTUtils.toCTScript(((ContainerBiggerCreatingTable) entityPlayer.openContainer).getTileEntityBiggerCreatingTable()));
			}
			return null;
		}
	}
}