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
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;

public class BiggerAutoCraftingJeiTransfer implements IMessage
{
	private short recipeKey;
	private ItemStack output;

	public BiggerAutoCraftingJeiTransfer(short recipeKey, final ItemStack output)
	{
		this.recipeKey = recipeKey;
		this.output = output;
	}

	public BiggerAutoCraftingJeiTransfer() {}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		recipeKey = (short) ByteBufUtils.readVarShort(buf);
		output = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarShort(buf, recipeKey);
		ByteBufUtils.writeItemStack(buf, output);
	}

	public static class Handler implements IMessageHandler<BiggerAutoCraftingJeiTransfer, IMessage>
	{
		@Override
		public IMessage onMessage(final BiggerAutoCraftingJeiTransfer message, final MessageContext ctx)
		{
			final EntityPlayer entityPlayer = ctx.getServerHandler().player;
			if (entityPlayer != null && entityPlayer.openContainer instanceof ContainerAutoBiggerCraftingTable)
				((ContainerAutoBiggerCraftingTable) entityPlayer.openContainer).defineShape(message.recipeKey, message.output);
			return null;
		}
	}
}