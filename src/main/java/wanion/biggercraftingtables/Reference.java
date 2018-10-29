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

import javax.annotation.Nonnull;
import java.util.Random;

public final class Reference
{
	public static final String MOD_ID = "biggercraftingtables";
	public static final String MOD_NAME = "Bigger Crafting Tables";
	public static final String MOD_VERSION = "1.12.2-1.2";
	public static final String DEPENDENCIES = "required-after:wanionlib@[1.12.2-1.7,)";
	public static final String TARGET_MC_VERSION = "[1.12,]";
	public static final String CLIENT_PROXY = "wanion.biggercraftingtables.proxy.ClientProxy";
	public static final String SERVER_PROXY = "wanion.biggercraftingtables.proxy.CommonProxy";
	public static final Random RANDOM = new Random();

	public static final PropertyEnum<TableTypes> TABLE_TYPES = PropertyEnum.create("tabletypes", TableTypes.class);

	public enum TableTypes implements IStringSerializable, Comparable<TableTypes>
	{
		BIG("big", 0),
		HUGE("huge", 1);
		public final String name;
		public final int metadata;

		TableTypes(@Nonnull final String name, final int metadata)
		{
			this.name = name;
			this.metadata = metadata;
		}

		public static String getName(final int metadata)
		{
			for (final TableTypes tableType : TableTypes.values())
				if (tableType.metadata == metadata)
					return tableType.name;
			return BIG.name;
		}

		public static TableTypes getValue(final int metadata)
		{
			for (final TableTypes tableType : TableTypes.values())
				if (tableType.metadata == metadata)
					return tableType;
			return BIG;
		}

		@Override
		@Nonnull
		public String getName()
		{
			return name;
		}

		public int getMetadata()
		{
			return metadata;
		}
	}
}