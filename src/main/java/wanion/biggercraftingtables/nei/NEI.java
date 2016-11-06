package wanion.biggercraftingtables.nei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import codechicken.nei.api.API;
import codechicken.nei.recipe.*;
import wanion.biggercraftingtables.block.BigCraftingTable.GuiBigCraftingTable;
import wanion.biggercraftingtables.block.HugeCraftingTable.GuiHugeCraftingTable;

import javax.annotation.Nonnull;

public final class NEI
{
	private NEI() {}

	public static void init()
	{
		registerHandler(new BigShapedRecipeHandler());
		registerHandler(new BigShapelessRecipeHandler());
		API.setGuiOffset(GuiBigCraftingTable.class, 20, -2);
		registerHandler(new HugeShapedRecipeHandler());
		registerHandler(new HugeShapelessRecipeHandler());
		API.setGuiOffset(GuiHugeCraftingTable.class, 4, 13);
	}

	private static void registerHandler(@Nonnull final TemplateRecipeHandler templateRecipeHandler)
	{
		API.registerRecipeHandler(templateRecipeHandler);
		API.registerUsageHandler(templateRecipeHandler);
	}
}