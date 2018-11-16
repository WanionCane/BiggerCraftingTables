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
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.block.ContainerAutoBiggerCraftingTable;

public final class BiggerAutoCraftingSyncEnergy implements IMessage
{
	private int energy;

	public BiggerAutoCraftingSyncEnergy(final int energy)
	{
		this.energy = energy;
	}

	@SuppressWarnings("unused")
	public BiggerAutoCraftingSyncEnergy() {}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.energy = ByteBufUtils.readVarInt(buf, 4);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, energy, 4);
	}

	public static class Handler implements IMessageHandler<BiggerAutoCraftingSyncEnergy, IMessage>
	{
		@Override
		public IMessage onMessage(final BiggerAutoCraftingSyncEnergy message, final MessageContext ctx)
		{
			final EntityPlayer entityPlayer = BiggerCraftingTables.proxy.getEntityPlayerFromContext(ctx);
			if (entityPlayer != null && entityPlayer.openContainer instanceof ContainerAutoBiggerCraftingTable)
				((ContainerAutoBiggerCraftingTable) entityPlayer.openContainer).getTile().energyControl.setEnergyStored(message.energy);
			return null;
		}
	}
}