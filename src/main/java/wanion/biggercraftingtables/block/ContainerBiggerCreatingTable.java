package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import wanion.biggercraftingtables.common.IGhostAcceptorContainer;
import wanion.biggercraftingtables.common.IShapedContainer;
import wanion.biggercraftingtables.inventory.slot.ShapeSlot;
import wanion.lib.common.control.Controls;
import wanion.lib.common.control.ControlsContainer;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class ContainerBiggerCreatingTable<R extends IAdvancedRecipe> extends ControlsContainer implements IShapedContainer, IGhostAcceptorContainer
{
	private final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable;
	private int playerInventoryEnds, playerInventoryStarts, result, root;

	public ContainerBiggerCreatingTable(final int inventoryStartsX, final int inventoryStartsY, final int playerStartsX, final int playerStartsY, final int resultX, final int resultY, @Nonnull final TileEntityBiggerCreatingTable tileEntityBiggerCreatingTable, final InventoryPlayer inventoryPlayer)
	{
		super(tileEntityBiggerCreatingTable.getControls());
		this.tileEntityBiggerCreatingTable = tileEntityBiggerCreatingTable;
		this.root = tileEntityBiggerCreatingTable.getRoot();
		for (int y = 0; y < root; y++)
			for (int x = 0; x < root; x++)
				addSlotToContainer(new ShapeSlot(tileEntityBiggerCreatingTable, y * root + x, inventoryStartsX + (18 * x), inventoryStartsY + (18 * y)));
		addSlotToContainer(new ShapeSlot(tileEntityBiggerCreatingTable, root * root, resultX, resultY));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, playerStartsX + (18 * x), playerStartsY + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, playerStartsX + (18 * i), playerStartsY + 58));
		playerInventoryEnds = inventorySlots.size();
		playerInventoryStarts = playerInventoryEnds - 36;
		result = playerInventoryStarts - 1;
	}

	@Nonnull
	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = inventorySlots.get(slot);
		if (slot > result && actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (!mergeItemStack(itemstack1, playerInventoryStarts, playerInventoryEnds, true))
				return ItemStack.EMPTY;
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public final ItemStack slotClick(final int slot, final int mouseButton, final ClickType clickType, final EntityPlayer entityPlayer)
	{
		if (slot >= 0 && slot < result) {
			final Slot actualSlot = inventorySlots.get(slot);
			if (clickType == ClickType.QUICK_MOVE) {
				actualSlot.putStack(ItemStack.EMPTY);
			} else if (clickType == ClickType.PICKUP) {
				final ItemStack playerStack = entityPlayer.inventory.getItemStack();
				final boolean slotHasStack = actualSlot.getHasStack();
				if (!playerStack.isEmpty() && !slotHasStack) {
					final ItemStack newSlotStack = playerStack.copy();
					newSlotStack.setCount(1);
					actualSlot.putStack(newSlotStack);
				} else if (playerStack.isEmpty() && slotHasStack || !playerStack.isEmpty() && playerStack.isItemEqual(actualSlot.getStack()))
					actualSlot.putStack(ItemStack.EMPTY);
			}
			return ItemStack.EMPTY;
		} else if (slot == result) {
			final Slot actualSlot = inventorySlots.get(slot);
			final boolean slotHasStack = actualSlot.getHasStack();
			final ItemStack playerStack = entityPlayer.inventory.getItemStack();
			final boolean playerHasStack = !playerStack.isEmpty();
			if (clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) {
				if (playerHasStack && !slotHasStack) {
					final ItemStack newSlotStack = playerStack.copy();
					actualSlot.putStack(newSlotStack);
				} else if (slotHasStack && !playerHasStack) {
					final ItemStack slotStack = actualSlot.getStack();
					if (mouseButton == 1) {
						slotStack.setCount(clickType == ClickType.PICKUP ? slotStack.getCount() - 1 : slotStack.getCount() - 16);
						if (!actualSlot.getHasStack())
							actualSlot.putStack(ItemStack.EMPTY);
					}
					if (mouseButton == 0) {
						if (slotStack.getCount() < slotStack.getMaxStackSize())
							slotStack.setCount(MathHelper.clamp(clickType == ClickType.PICKUP ? slotStack.getCount() + 1 : slotStack.getCount() + 16, 1, slotStack.getMaxStackSize()));
					}
				} else if (playerStack.isItemEqual(actualSlot.getStack()))
					actualSlot.putStack(ItemStack.EMPTY);
			}
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, mouseButton, clickType, entityPlayer);
	}

	public final void defineShape(final short key, @Nonnull final ItemStack output)
	{
		final IAdvancedRecipe advancedRecipe = tileEntityBiggerCreatingTable.getRecipeRegistry().findRecipeByKeyAndOutput(key, output);
		if (advancedRecipe == null)
			return;
		clearShape();
		final List<Object> inputs = advancedRecipe.getInputs();
		if (advancedRecipe.isShaped()) {
			int i = 0;
			for (int y = 0; y < root; y++) {
				for (int x = 0; x < root; x++) {
					if (i >= inputs.size() || x >= advancedRecipe.getWidth() || y >= advancedRecipe.getHeight())
						continue;
					final Slot slot = inventorySlots.get(x + (root * y));
					final ItemStack stackInput = getStackInput(inputs.get(i++));
					if (stackInput != null)
						slot.putStack(stackInput);
				}
			}
		} else {
			for (int i = 0; i < inputs.size() && i < root * root; i++) {
				final Slot slot = inventorySlots.get(i);
				final ItemStack stackInput = getStackInput(inputs.get(i));
				if (stackInput != null)
					slot.putStack(stackInput);
			}
		}
		inventorySlots.get(result).putStack(advancedRecipe.getOutput());
		detectAndSendChanges();
	}

	@Override
	public void clearShape()
	{
		clearShape(root * root);
	}

	private void clearShape(final int endsIn)
	{
		for (int i = 0; i <= endsIn; i++)
			inventorySlots.get(i).putStack(ItemStack.EMPTY);
	}

	@Override
	public void accept(final int slot, @Nonnull final ItemStack itemStack)
	{
		if (slot < inventorySlots.size() - 36) {
			inventorySlots.get(slot).putStack(itemStack);
			detectAndSendChanges();
		}
	}

	@Nonnull
	@Override
	public Controls getControls()
	{
		return tileEntityBiggerCreatingTable.getControls();
	}

	private static ItemStack getStackInput(final Object input)
	{
		return input instanceof ItemStack ? ((ItemStack) input).copy() : input instanceof List ? ((ItemStack) ((List) input).get(0)).copy() : null;
	}

	@Override
	public final boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityBiggerCreatingTable.isUsableByPlayer(entityPlayer);
	}
}