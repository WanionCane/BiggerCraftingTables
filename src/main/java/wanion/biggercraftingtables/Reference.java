package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.Random;

import static java.io.File.separatorChar;

public final class Reference
{
	public static final String MOD_VERSION = "1.12.2-2.3";
	public static final String DEPENDENCIES = "required-before:crafttweaker;required-after:wanionlib@[1.12.2-2.3,)";
	public static final String TARGET_MC_VERSION = "[1.12,]";
	public static final String CLIENT_PROXY = "wanion.biggercraftingtables.proxy.ClientProxy";
	public static final String SERVER_PROXY = "wanion.biggercraftingtables.proxy.CommonProxy";
	public static final String MOD_ID = "biggercraftingtables";
	public static final String MOD_NAME = "Bigger Crafting Tables";
	public static final Random RANDOM = new Random();
	public static final PropertyEnum<TableTypes> TABLE_TYPES = PropertyEnum.create("tabletypes", TableTypes.class);
	public static final ResourceLocation GUI_TEXTURES = new ResourceLocation(MOD_ID, "textures/gui/gui_textures.png");
	public static final char SLASH = separatorChar;

	public enum TableTypes implements IStringSerializable, Comparable<TableTypes>
	{
		BIG,
		HUGE,
		GIANT;

		final int root;

		TableTypes()
		{
			this.root = 5 + (2 * ordinal());
		}

		@Nonnull
		@Override
		public String getName()
		{
			return toString().toLowerCase();
		}

		public int getMetadata()
		{
			return ordinal();
		}

		public int getRoot()
		{
			return root;
		}

		public static String getName(final int metadata)
		{
			return getByValue(metadata).getName();
		}

		public static TableTypes getByValue(int metadata)
		{
			metadata = MathHelper.clamp(metadata, 0, TableTypes.values().length - 1);
			return TableTypes.values()[metadata];
		}
	}
}