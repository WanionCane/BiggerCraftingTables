package wanion.biggercraftingtables.block;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import wanion.biggercraftingtables.common.IGhostAcceptorContainer;
import wanion.biggercraftingtables.common.IShapedContainer;
import wanion.lib.common.ISlotCreator;
import wanion.lib.common.control.Controls;
import wanion.lib.common.control.ControlsContainer;
import wanion.lib.common.control.redstone.IRedstoneControlProvider;
import wanion.lib.common.control.redstone.RedstoneControl;
import wanion.lib.recipe.advanced.IAdvancedRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ContainerAutoBiggerCraftingTable extends ControlsContainer implements IRedstoneControlProvider, IShapedContainer, IGhostAcceptorContainer
{
	private final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable;
	private final int playerInventoryEnds, playerInventoryStarts, inventoryFull, shapeEnds, result;

	public ContainerAutoBiggerCraftingTable(@Nonnull final ISlotCreator slotCreator, @Nonnull final TileEntityAutoBiggerCraftingTable tileEntityAutoBiggerCraftingTable)
	{
		super(tileEntityAutoBiggerCraftingTable.getControls());
		this.tileEntityAutoBiggerCraftingTable = tileEntityAutoBiggerCraftingTable;
		final List<Slot> slotList = new ArrayList<>();
		slotCreator.create(slotList);
		slotList.forEach(this::addSlotToContainer);
		final int inventorySize = inventorySlots.size();
		playerInventoryEnds = inventorySize;
		playerInventoryStarts = inventorySize - 36;
		inventoryFull = (playerInventoryStarts - 2) / 2;
		shapeEnds = inventoryFull * 2;
		result = shapeEnds + 1;
	}

	@Nonnull
	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot >= playerInventoryStarts) {
				if (!mergeItemStack(itemstack1, 0, inventoryFull, false))
					return ItemStack.EMPTY;
			} else if (slot <= inventoryFull || slot == result) {
				if (!mergeItemStack(itemstack1, playerInventoryStarts, playerInventoryEnds, true))
					return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0)
				actualSlot.putStack(ItemStack.EMPTY);
			actualSlot.onSlotChanged();
		}
		return itemstack != null ? itemstack : ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public final ItemStack slotClick(final int slot, final int mouseButton, final ClickType clickType, final EntityPlayer entityPlayer)
	{
		if (slot >= inventoryFull && slot < shapeEnds) {
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
			tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			return ItemStack.EMPTY;
		} else if (slot == shapeEnds) {
			if (inventorySlots.get(slot).getHasStack()) {
				clearShape(tileEntityAutoBiggerCraftingTable.half, tileEntityAutoBiggerCraftingTable.full);
				tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			}
			return ItemStack.EMPTY;
		} else return super.slotClick(slot, mouseButton, clickType, entityPlayer);
	}

	@Override
	public boolean canInteractWith(@Nonnull final EntityPlayer entityPlayer)
	{
		return tileEntityAutoBiggerCraftingTable.isUsableByPlayer(entityPlayer);
	}

	public final void defineShape(final short key, @Nonnull final ItemStack output)
	{
		final IAdvancedRecipe advancedRecipe = tileEntityAutoBiggerCraftingTable.getRecipeRegistry().findRecipeByKeyAndOutput(key, output);
		if (advancedRecipe == null)
			return;
		final int slotCount = inventorySlots.size();
		final int startsIn = ((slotCount - 36) / 2) - 1, endsIn = slotCount - 38;
		final int root = (int) Math.sqrt(endsIn - startsIn + 1);
		clearShape(startsIn, endsIn);
		final List<Object> inputs = advancedRecipe.getInputs();
		if (advancedRecipe.isShaped()) {
			int i = 0;
			for (int y = 0; y < root; y++) {
				for (int x = 0; x < root; x++) {
					if (i >= inputs.size() || x >= advancedRecipe.getWidth() || y >= advancedRecipe.getHeight())
						continue;
					final Slot slot = inventorySlots.get(startsIn + (x + (root * y)));
					final ItemStack stackInput = getStackInput(inputs.get(i++));
					if (stackInput != null)
						slot.putStack(stackInput);
				}
			}
		} else {
			for (int i = 0; i < inputs.size() && i < root * root; i++) {
				final Slot slot = inventorySlots.get(startsIn + i);
				final ItemStack stackInput = getStackInput(inputs.get(i));
				if (stackInput != null)
					slot.putStack(stackInput);
			}
		}
		tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
		detectAndSendChanges();
	}

	@Override
	public void clearShape()
	{
		final int slotCount = inventorySlots.size();
		final int startsIn = ((slotCount - 36) / 2) - 1, endsIn = slotCount - 38;
		clearShape(startsIn, endsIn);
	}

	private void clearShape(final int startsIn, final int endsIn)
	{
		for (int i = startsIn; i < endsIn; i++)
			inventorySlots.get(i).putStack(ItemStack.EMPTY);
	}

	@Nonnull
	public final TileEntityAutoBiggerCraftingTable getTile()
	{
		return tileEntityAutoBiggerCraftingTable;
	}

	@Nonnull
	@Override
	public RedstoneControl getRedstoneControl()
	{
		return tileEntityAutoBiggerCraftingTable.redstoneControl;
	}

	@Nonnull
	@Override
	public Controls getControls()
	{
		return tileEntityAutoBiggerCraftingTable.getControls();
	}

	@Override
	public void accept(final int slot, @Nonnull ItemStack itemStack)
	{
		if (slot >= inventoryFull && slot < shapeEnds) {
			final Slot actualSlot = inventorySlots.get(slot);
			if (itemStack.isItemEqual(actualSlot.getStack()))
				actualSlot.putStack(ItemStack.EMPTY);
			else
				actualSlot.putStack(itemStack);
			tileEntityAutoBiggerCraftingTable.recipeShapeChanged();
			detectAndSendChanges();
		}
	}

	private static ItemStack getStackInput(final Object input)
	{
		return input instanceof ItemStack ? ((ItemStack) input).copy() : input instanceof List ? ((ItemStack) ((List) input).get(0)).copy() : null;
	}
}