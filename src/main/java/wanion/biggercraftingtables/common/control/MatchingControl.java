package wanion.biggercraftingtables.common.control;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;
import wanion.lib.common.control.IControl;
import wanion.lib.common.control.IControlNameable;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MatchingControl implements IControl<MatchingControl>, IControlNameable
{
	private final NonNullList<ItemStack> itemStacks;
	private final int number;
	private final String stringNumber;
	private IMatcher matcher = new ItemStackMatcher();

	public MatchingControl(@Nonnull final NonNullList<ItemStack> itemStacks, int number)
	{
		this.itemStacks = itemStacks;
		this.stringNumber = Integer.toString(this.number = number);
	}

	public void resetMatcher()
	{
		this.matcher = new ItemStackMatcher();
	}

	public void nextMatcher()
	{
		this.matcher = matcher.next();
	}

	public IMatcher getMatcher()
	{
		return matcher;
	}

	@Override
	public void writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		if (!nbtTagCompound.hasKey("Matching"))
			nbtTagCompound.setTag("Matching", new NBTTagCompound());
		final NBTTagCompound subCompound = nbtTagCompound.getCompoundTag("Matching");
		final NBTTagCompound matchingCompound = new NBTTagCompound();
		matchingCompound.setInteger("matcherType", matcher.getMatcherEnum().ordinal());
		matcher.writeToNBT(matchingCompound);
		subCompound.setTag(stringNumber, matchingCompound);
	}

	@Override
	public void readFromNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		if (nbtTagCompound.hasKey("Matching")) {
			final NBTTagCompound subCompound = nbtTagCompound.getCompoundTag("Matching");
			if (subCompound.hasKey(stringNumber)) {
				final NBTTagCompound matchingCompound = subCompound.getCompoundTag(stringNumber);
				final MatcherEnum matcherEnum = MatcherEnum.values()[matchingCompound.getInteger("matcherType")];
				final IMatcher matcher = matcherEnum.getMatcher(this);
				matcher.readFromNBT(matchingCompound);
				this.matcher = matcher.validate();
			}
		}
	}

	@Nonnull
	@Override
	public MatchingControl copy()
	{
		final MatchingControl matchingControl = new MatchingControl(itemStacks, number);
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		matchingControl.readFromNBT(nbtTagCompound);
		return matchingControl;
	}

	@Override
	public int hashCode()
	{
		return number;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
			return true;
		else if (obj instanceof MatchingControl) {
			final MatchingControl matchingControl = (MatchingControl) obj;
			if (matchingControl.number == this.number)
				return this.matcher.equals(matchingControl.matcher);
			else return false;
		} else return false;
	}

	@Nonnull
	@Override
	public String getControlName()
	{
		return "bigger.creating.matching.control";
	}

	private enum MatcherEnum
	{
		ITEM_STACK(ItemStackMatcher.class),
		ANY_DAMAGE(AnyDamageMatcher.class),
		ORE_DICT(OreDictMatcher.class);

		final Class<? extends IMatcher> matcherClass;

		MatcherEnum(@Nonnull final Class<? extends IMatcher> matcherClass)
		{
			this.matcherClass = matcherClass;
		}

		@Nonnull
		private IMatcher getMatcher(@Nonnull final MatchingControl matchingControl)
		{
			try {
				final Constructor<? extends IMatcher> constructor = matcherClass.getDeclaredConstructor(MatchingControl.class);
				return constructor.newInstance(matchingControl);
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				throw new RuntimeException("This Should never happen: " + e);
			}
		}
	}

	public interface IMatcher
	{
		default void writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound) {}

		default void readFromNBT(@Nonnull final NBTTagCompound nbtTagCompound) {}

		@Nonnull
		MatcherEnum getMatcherEnum();

		@Nonnull
		IMatcher validate();

		@Nonnull
		IMatcher next();

		boolean matches(@Nonnull ItemStack otherItemStack);

		@Nonnull
		String format();

		@Nonnull
		default String getDescription()
		{
			return I18n.format("bigger.creating.matching.matcher." + getMatcherEnum().name().toLowerCase());
		}
	}

	public class ItemStackMatcher implements IMatcher
	{
		@Nonnull
		@Override
		public MatcherEnum getMatcherEnum()
		{
			return MatcherEnum.ITEM_STACK;
		}

		@Nonnull
		@Override
		public IMatcher validate()
		{
			return this;
		}

		@Nonnull
		@Override
		public IMatcher next()
		{
			final ItemStack itemStack = itemStacks.get(number);
			if (!itemStack.getHasSubtypes() && !itemStack.isItemStackDamageable()) {
				final int[] ores = OreDictionary.getOreIDs(itemStack);
				return ores.length == 0 ? this : new OreDictMatcher();
			} else if (itemStack.getHasSubtypes() || itemStack.isItemStackDamageable())
				return new AnyDamageMatcher();
			else return this;
		}

		@Override
		public boolean matches(@Nonnull final ItemStack otherItemStack)
		{
			return itemStacks.get(number).isItemEqual(otherItemStack);
		}

		@Nonnull
		@Override
		public String format()
		{
			final ItemStack itemStack = itemStacks.get(number);
			return itemStack.getItemDamage() > 0 ? "<" + itemStack.getItem().getRegistryName() + ":" + itemStack.getItemDamage() + ">" : "<" + itemStack.getItem().getRegistryName() + ">";
		}

		@Override
		public boolean equals(final Object obj)
		{
			return obj == this || obj instanceof ItemStackMatcher;
		}
	}

	public class AnyDamageMatcher implements IMatcher
	{
		@Nonnull
		@Override
		public MatcherEnum getMatcherEnum()
		{
			return MatcherEnum.ANY_DAMAGE;
		}

		@Nonnull
		@Override
		public IMatcher validate()
		{
			final ItemStack itemStack = itemStacks.get(number);
			return itemStack.getHasSubtypes() || itemStack.isItemStackDamageable() ? this : new ItemStackMatcher();
		}

		@Nonnull
		@Override
		public IMatcher next()
		{
			final int[] ores = OreDictionary.getOreIDs(itemStacks.get(number));
			return ores.length == 0 ? new ItemStackMatcher() : new OreDictMatcher();
		}

		@Override
		public boolean matches(@Nonnull final ItemStack otherItemStack)
		{
			final ItemStack itemStack = itemStacks.get(number);
			return !itemStack.isEmpty() && itemStack.getItem() == otherItemStack.getItem();
		}

		@Nonnull
		@Override
		public String format()
		{
			final ItemStack itemStack = itemStacks.get(number);
			return "<" + itemStack.getItem().getRegistryName() + ":*>";
		}

		@Override
		public boolean equals(final Object obj)
		{
			return obj == this || obj instanceof AnyDamageMatcher;
		}
	}

	public class OreDictMatcher implements IMatcher
	{
		final int[] ores = !itemStacks.get(number).isEmpty() ? OreDictionary.getOreIDs(itemStacks.get(number)) : new int[0];
		int actualOre = 0;

		@Override
		public void writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound)
		{
			nbtTagCompound.setInteger("actualOre", actualOre);
		}

		@Override
		public void readFromNBT(@Nonnull final NBTTagCompound nbtTagCompound)
		{
			actualOre = nbtTagCompound.getInteger("actualOre");
		}

		@Nonnull
		@Override
		public MatcherEnum getMatcherEnum()
		{
			return MatcherEnum.ORE_DICT;
		}

		@Nonnull
		@Override
		public IMatcher validate()
		{
			return ores.length > 0 && actualOre < ores.length ? this : new ItemStackMatcher();
		}

		@Nonnull
		@Override
		public IMatcher next()
		{
			return ++actualOre < ores.length ? this : new ItemStackMatcher();
		}

		@Override
		public boolean matches(@Nonnull final ItemStack otherItemStack)
		{
			final int[] ores = OreDictionary.getOreIDs(otherItemStack);
			return ores.length != 0 && matches(ores);
		}

		@Nonnull
		@Override
		public String getDescription()
		{
			return I18n.format("bigger.creating.matching.matcher." + getMatcherEnum().name().toLowerCase()) + " " + TextFormatting.GOLD + OreDictionary.getOreName(ores[actualOre]);
		}

		@Nonnull
		@Override
		public String format()
		{
			return "<ore:" + OreDictionary.getOreName(ores[actualOre]) + ">";
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (obj == this)
				return true;
			else if (obj instanceof OreDictMatcher) {
				final OreDictMatcher oreDictMatcher = (OreDictMatcher) obj;
				if (this.actualOre != oreDictMatcher.actualOre || ores.length != oreDictMatcher.ores.length)
					return false;
				for (int i = 0; i < ores.length; i++)
					if (ores[i] != oreDictMatcher.ores[i])
						return false;
				return true;
			} else return false;
		}

		private boolean matches(final int[] ores)
		{
			final int actualOre = this.ores[this.actualOre];
			for (int ore : ores)
				if (ore == actualOre)
					return true;
			return false;
		}
	}
}