package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wanion.biggercraftingtables.Reference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public final class ItemBlockBiggerCreatingTable extends ItemBlock
{
	public static final ItemBlockBiggerCreatingTable INSTANCE = new ItemBlockBiggerCreatingTable();

	public ItemBlockBiggerCreatingTable()
	{
		super(BlockBiggerCreatingTable.INSTANCE);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "biggercreatingtable"));
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemStack)
	{
		return "container." + Reference.TableTypes.getName(getDamage(itemStack)) + "creatingtable";
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull final ItemStack stack, @Nullable final World worldIn, @Nonnull final List<String> tooltip, @Nonnull final ITooltipFlag flagIn)
	{
		if (!stack.isEmpty())
			tooltip.add(I18n.format("bigger.creating.tooltip", I18n.format("bigger.creating.tooltip." + Reference.TableTypes.getName(stack.getMetadata()) + ".name")));
		tooltip.add(TextFormatting.GOLD + I18n.format("bigger.creating.creative-only"));
	}

	@Override
	public int getMetadata(final int damage)
	{
		return MathHelper.clamp(damage, 0, Reference.TableTypes.values().length);
	}
}