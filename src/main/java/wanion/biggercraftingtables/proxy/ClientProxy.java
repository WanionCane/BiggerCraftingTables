package wanion.biggercraftingtables.proxy;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		final ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		itemModelMesher.register(Item.getItemFromBlock(BlockBiggerCraftingTable.INSTANCE), 0, new ModelResourceLocation(Reference.MOD_ID + ":biggercraftingtable", "big"));
		itemModelMesher.register(Item.getItemFromBlock(BlockBiggerCraftingTable.INSTANCE), 1, new ModelResourceLocation(Reference.MOD_ID + ":biggercraftingtable", "huge"));
		itemModelMesher.register(Item.getItemFromBlock(BlockAutoBiggerCraftingTable.INSTANCE), 0, new ModelResourceLocation(Reference.MOD_ID + ":autobiggercraftingtable", "big"));
		itemModelMesher.register(Item.getItemFromBlock(BlockAutoBiggerCraftingTable.INSTANCE), 1, new ModelResourceLocation(Reference.MOD_ID + ":autobiggercraftingtable", "huge"));
	}
}