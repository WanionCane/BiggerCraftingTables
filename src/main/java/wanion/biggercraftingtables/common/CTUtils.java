package wanion.biggercraftingtables.common;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;
import wanion.biggercraftingtables.Reference;
import wanion.biggercraftingtables.block.TileEntityBiggerCreatingTable;
import wanion.biggercraftingtables.common.control.MatchingControl;
import wanion.biggercraftingtables.common.control.ShapeControl;

import javax.annotation.Nonnull;

public final class CTUtils
{
	private static final String NEW_LINE = System.lineSeparator();
	private static final String TAB = "\t";

	private CTUtils() {}

	public static String toCTScript(@Nonnull final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable)
	{
		final int outputSlot = tileEntityBiggerCreatingTable.getSizeInventory() - 1;
		final ItemStack outputStack = tileEntityBiggerCreatingTable.getStackInSlot(outputSlot);
		if (outputStack.isEmpty())
			return null;
		final StringBuilder scriptBuilder = new StringBuilder();
		final Reference.TableTypes tableType = tileEntityBiggerCreatingTable.getTableType();
		final ShapeControl.ShapeState shapeState = tileEntityBiggerCreatingTable.getShapeControl().getState();
		scriptBuilder.append("mods.biggercraftingtables.");
		scriptBuilder.append(WordUtils.capitalizeFully(tableType.getName())).append(".add");
		scriptBuilder.append(shapeState == ShapeControl.ShapeState.SHAPED ? "Shaped" : "Shapeless").append("(<");
		scriptBuilder.append(outputStack.getItem().getRegistryName());
		if (outputStack.getItemDamage() > 0)
			scriptBuilder.append(':').append(outputStack.getItemDamage());
		scriptBuilder.append('>');
		if (outputStack.getCount() > 1)
			scriptBuilder.append(" * ").append(outputStack.getCount());
		scriptBuilder.append(", [");
		if (shapeState == ShapeControl.ShapeState.SHAPED) {
			final int root = tileEntityBiggerCreatingTable.getRoot();
			final int last = root - 1;
			for (int y = 0; y < root; y++) {
				boolean hasNextY = y < last;
				scriptBuilder.append(NEW_LINE);
				scriptBuilder.append(TAB).append('[');
				for (int x = 0; x < root; x++) {
					boolean hasNextX = x < last;
					final int actualSlot = y * root + x;
					if (!tileEntityBiggerCreatingTable.getStackInSlot(actualSlot).isEmpty()) {
						final MatchingControl matchingControl = tileEntityBiggerCreatingTable.getMatchingControl(actualSlot);
						scriptBuilder.append(matchingControl.getMatcher().format());
						if (hasNextX)
							scriptBuilder.append(", ");
					} else scriptBuilder.append(hasNextX ? "null, " : "null");
				}
				scriptBuilder.append(hasNextY ? "]," : ']');
			}
			scriptBuilder.append(NEW_LINE);
			scriptBuilder.append(']');
		} else {
			final int intLastInventorySlot = outputSlot - 1;
			int charPerLineCount = 0;
			for (int i = 0; i < outputSlot; i++) {
				final boolean hasNext = i < intLastInventorySlot;
				if (tileEntityBiggerCreatingTable.getStackInSlot(i).isEmpty())
					continue;
				final MatchingControl matchingControl = tileEntityBiggerCreatingTable.getMatchingControl(i);
				final String format = matchingControl.getMatcher().format();
				charPerLineCount += format.length();
				if (charPerLineCount >= 512 && hasNext) {
					charPerLineCount = 0;
					scriptBuilder.deleteCharAt(scriptBuilder.length() - 1);
					scriptBuilder.append(NEW_LINE).append(TAB);
				}
				scriptBuilder.append(format).append(hasNext ? ", " : ']');
			}
		}
		scriptBuilder.append(");");
		scriptBuilder.append(NEW_LINE).append(NEW_LINE);
		return scriptBuilder.toString();
	}
}