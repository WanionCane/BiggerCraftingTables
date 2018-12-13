package wanion.biggercraftingtables.common.control;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import wanion.lib.common.control.IControlNameable;
import wanion.lib.common.control.IState;
import wanion.lib.common.control.IStateNameable;
import wanion.lib.common.control.IStateProvider;

import javax.annotation.Nonnull;

public class ShapeControl implements IStateProvider<ShapeControl, ShapeControl.ShapeState>, IControlNameable
{
	private ShapeState state;

	public ShapeControl()
	{
		this(ShapeState.SHAPED);
	}

	public ShapeControl(@Nonnull final ShapeState state)
	{
		this.state = state;
	}

	@Override
	public void writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		nbtTagCompound.setInteger("ShapeControl", state.ordinal());
	}

	@Override
	public void readFromNBT(@Nonnull final NBTTagCompound nbtTagCompound)
	{
		if (nbtTagCompound.hasKey("ShapeControl"))
			state = ShapeState.values()[MathHelper.clamp(nbtTagCompound.getInteger("ShapeControl"), 0, ShapeState.values().length - 1)];
	}

	@Nonnull
	@Override
	public ShapeControl copy()
	{
		return new ShapeControl(state);
	}

	@Override
	@Nonnull
	public ShapeState getState()
	{
		return state;
	}

	@Override
	public void setState(@Nonnull final ShapeState shapeState)
	{
		this.state = shapeState;
	}

	@Override
	public void writeToNBT(@Nonnull final NBTTagCompound nbtTagCompound, @Nonnull final ShapeState shapeState)
	{
		nbtTagCompound.setInteger("ShapeControl", shapeState.ordinal());
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj == this || (obj instanceof ShapeControl && this.state == ((ShapeControl) obj).state);
	}

	@Nonnull
	@Override
	public String getControlName()
	{
		return "bigger.creating.shape.control";
	}

	public enum ShapeState implements IState<ShapeState>, IStateNameable
	{
		SHAPED,
		SHAPELESS;

		@Nonnull
		@Override
		public ShapeState getNextState()
		{
			final int nextState = ordinal() + 1;
			return nextState > values().length - 1 ? values()[0] : values()[nextState];
		}

		@Nonnull
		@Override
		public ShapeState getPreviousState()
		{
			final int previousState = ordinal() - 1;
			return previousState >= 0 ? values()[previousState] : values()[values().length - 1];
		}

		@Override
		public Pair<Integer, Integer> getTexturePos(boolean b)
		{
			return new ImmutablePair<>(!b ? 0 : 19, 19 * ordinal());
		}

		@Nonnull
		@Override
		public String getStateName()
		{
			return "bigger.creating.shape.control.state." + name().toLowerCase();
		}

		@Override
		public String getStateDescription()
		{
			return null;
		}
	}
}