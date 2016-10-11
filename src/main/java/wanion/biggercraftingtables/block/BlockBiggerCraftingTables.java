package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.block.BigCraftingTable.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.HugeCraftingTable.TileEntityHugeCraftingTable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static wanion.biggercraftingtables.Reference.MOD_ID;

public final class BlockBiggerCraftingTables extends BlockContainer
{
	public static final BlockBiggerCraftingTables instance = new BlockBiggerCraftingTables();
	public static final List<String> types = Arrays.asList("Big", "Huge");
	@SideOnly(Side.CLIENT)
	private static IIcon[][] textures;

	private BlockBiggerCraftingTables()
	{
		super(Material.wood);
		setCreativeTab(BiggerCraftingTables.creativeTabs);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata)
	{
		switch (metadata) {
			case 0:
				return new TileEntityBigCraftingTable();
			case 1:
				return new TileEntityHugeCraftingTable();
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(@Nonnull final IIconRegister iIconRegister)
	{
		textures = new IIcon[2][3];
		for (int i = 0; i < 2; i++) {
			textures[i][0] = iIconRegister.registerIcon(MOD_ID + ":" + types.get(i) + "CraftingTableTop");
			textures[i][1] = iIconRegister.registerIcon(MOD_ID + ":" + types.get(i) + "CraftingTableBottom");
			textures[i][2] = iIconRegister.registerIcon(MOD_ID + ":" + types.get(i) + "CraftingTableSides");
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, int metadata)
	{
		if (metadata < 0 || metadata > 1)
			metadata = 0;
		switch (side) {
			case 0:
				return textures[metadata][1];
			case 1:
				return textures[metadata][0];
			default:
				return textures[metadata][2];
		}
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final Item block, final CreativeTabs creativeTabs, final List list)
	{
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(block, 1, i));
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer entityPlayer, final int side, final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote) {
			final TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityBigCraftingTable)
				FMLNetworkHandler.openGui(entityPlayer, BiggerCraftingTables.instance, BiggerCraftingTables.GUI_ID_BIG_CRAFTING_TABLE, world, x, y, z);
			else if (tileEntity instanceof TileEntityHugeCraftingTable)
				FMLNetworkHandler.openGui(entityPlayer, BiggerCraftingTables.instance, BiggerCraftingTables.GUI_ID_HUGE_CRAFTING_TABLE, world, x, y, z);
			else
				return false;
		}
		return true;
	}
}