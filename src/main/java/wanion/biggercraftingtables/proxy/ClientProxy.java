package wanion.biggercraftingtables.proxy;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.BlockAutoBiggerCraftingTable;
import wanion.biggercraftingtables.block.BlockBiggerCraftingTable;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockBiggerCraftingTable.INSTANCE), 0, new ModelResourceLocation(Reference.MOD_ID + ":biggercraftingtable", "inventory,tabletypes=big"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockBiggerCraftingTable.INSTANCE), 1, new ModelResourceLocation(Reference.MOD_ID + ":biggercraftingtable", "inventory,tabletypes=huge"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockAutoBiggerCraftingTable.INSTANCE), 0, new ModelResourceLocation(Reference.MOD_ID + ":autobiggercraftingtable", "inventory,tabletypes=big"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockAutoBiggerCraftingTable.INSTANCE), 1, new ModelResourceLocation(Reference.MOD_ID + ":autobiggercraftingtable", "inventory,tabletypes=huge"));
	}
}