package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import wanion.biggercraftingtables.BiggerCraftingTables;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.big.TileEntityBigCraftingTable;
import wanion.biggercraftingtables.block.huge.TileEntityHugeCraftingTable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public final class BlockBiggerCraftingTable extends BlockContainer
{
	public static final BlockBiggerCraftingTable INSTANCE = new BlockBiggerCraftingTable();

	private BlockBiggerCraftingTable()
	{
		super(Material.WOOD);
		setHardness(2.5F).setCreativeTab(BiggerCraftingTables.creativeTabs);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "biggercraftingtable"));
		setDefaultState(blockState.getBaseState().withProperty(Reference.TABLE_TYPES, Reference.TableTypes.BIG));
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull final World world, final int metadata)
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

	@Override
	public void getSubBlocks(final CreativeTabs creativeTabs, final NonNullList<ItemStack> items)
	{
		if (creativeTabs == this.getCreativeTabToDisplayOn()){
			for (int i = 0; i < 2; i++)
				items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState state, final EntityPlayer entityPlayer, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		if (world != null && !world.isRemote) {
			final TileEntity tileEntity = world.getTileEntity(blockPos);
			if (tileEntity instanceof TileEntityBigCraftingTable)
				FMLNetworkHandler.openGui(entityPlayer, BiggerCraftingTables.instance, BiggerCraftingTables.GUI_ID_BIG_CRAFTING_TABLE, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
			else if (tileEntity instanceof TileEntityHugeCraftingTable)
				FMLNetworkHandler.openGui(entityPlayer, BiggerCraftingTables.instance, BiggerCraftingTables.GUI_ID_HUGE_CRAFTING_TABLE, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
			else
				return false;
		}
		return true;
	}

	@Override
	@Nonnull
	public Item getItemDropped(final IBlockState blockState, final Random random, final int fortune)
	{
		return Items.AIR;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	@Nonnull
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		return SoundType.WOOD;
	}

	@Override
	public void breakBlock(final World world, @Nonnull final BlockPos blockPos, @Nonnull final IBlockState blockState)
	{
		if (world == null)
			return;
		final TileEntityBiggerCraftingTable tileEntityBiggerCraftingTable = (TileEntityBiggerCraftingTable) world.getTileEntity(blockPos);
		if (tileEntityBiggerCraftingTable != null) {
			final ItemStack droppedStack = new ItemStack(this, 1, getMetaFromState(blockState));
			final NBTTagCompound nbtTagCompound = tileEntityBiggerCraftingTable.writeCustomNBT(new NBTTagCompound());
			if (nbtTagCompound.getTagList("Contents", 10).tagCount() > 0)
				droppedStack.setTagCompound(nbtTagCompound);
			world.spawnEntity(new EntityItem(world, blockPos.getX() + Reference.RANDOM.nextFloat() * 0.8F + 0.1F, blockPos.getY() + Reference.RANDOM.nextFloat() * 0.8F + 0.1F, blockPos.getZ() + Reference.RANDOM.nextFloat() * 0.8F + 0.1F, droppedStack));
		}
		super.breakBlock(world, blockPos, blockState);
	}

	@Override
	public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack)
	{
		if (world == null)
			return;
		final TileEntity tileEntity = world.getTileEntity(blockPos);
		if (tileEntity instanceof TileEntityBiggerCraftingTable && itemStack.hasTagCompound())
			((TileEntityBiggerCraftingTable) tileEntity).readCustomNBT(itemStack.getTagCompound());
	}

	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, Reference.TABLE_TYPES);
	}

	@Override
	public int getMetaFromState(final IBlockState blockState)
	{
		return blockState.getValue(Reference.TABLE_TYPES).metadata;
	}

	@Override
	public IBlockState getStateFromMeta(final int metadata)
	{
		return getDefaultState().withProperty(Reference.TABLE_TYPES, Reference.TableTypes.getValue(metadata));
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}