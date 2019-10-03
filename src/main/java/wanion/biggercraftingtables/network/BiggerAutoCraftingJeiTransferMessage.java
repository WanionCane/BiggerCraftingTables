package wanion.biggercraftingtables.network;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.lib.common.IShapedContainer;

public class BiggerAutoCraftingJeiTransferMessage implements IMessage
{
	private int windowId;
	private short recipeKey;
	private ItemStack output;

	public BiggerAutoCraftingJeiTransferMessage(final int windowId, final short recipeKey, final ItemStack output)
	{
		this.windowId = windowId;
		this.recipeKey = recipeKey;
		this.output = output;
	}

	@SuppressWarnings("unused")
	public BiggerAutoCraftingJeiTransferMessage() {}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.windowId = ByteBufUtils.readVarInt(buf, 5);
		recipeKey = (short) ByteBufUtils.readVarShort(buf);
		output = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, windowId, 5);
		ByteBufUtils.writeVarShort(buf, recipeKey);
		ByteBufUtils.writeItemStack(buf, output);
	}

	public static class Handler implements IMessageHandler<BiggerAutoCraftingJeiTransferMessage, IMessage>
	{
		@Override
		public IMessage onMessage(final BiggerAutoCraftingJeiTransferMessage message, final MessageContext ctx)
		{
			BiggerCraftingTables.proxy.getThreadListener().addScheduledTask(() -> {
				final EntityPlayer entityPlayer = BiggerCraftingTables.proxy.getEntityPlayerFromContext(ctx);
				if (entityPlayer != null && entityPlayer.openContainer instanceof IShapedContainer && entityPlayer.openContainer.windowId == message.windowId)
					((IShapedContainer) entityPlayer.openContainer).defineShape(message.recipeKey, message.output);
			});
			return null;
		}
	}
}