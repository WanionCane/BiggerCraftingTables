package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import wanion.biggercraftingtables.Reference;

public final class ItemBlockBiggerCraftingTables extends ItemBlockWithMetadata
{
	public ItemBlockBiggerCraftingTables(final Block block)
	{
		super(block, block);
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemStack)
	{
		return "tile." + Reference.MOD_ID + ":" + BlockBiggerCraftingTables.types.get(MathHelper.clamp_int(getDamage(itemStack), 0, BlockBiggerCraftingTables.types.size()));
	}
}