package wanion.biggercraftingtables.nei;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import codechicken.nei.api.API;

public final class NEI
{
	private NEI() {}

	public static void init()
	{
		final BigShapedRecipeHandler bigShapedRecipeHandler = new BigShapedRecipeHandler();
		final BigShapelessRecipeHandler bigShapelessRecipeHandler = new BigShapelessRecipeHandler();
		final HugeShapedRecipeHandler hugeShapedRecipeHandler = new HugeShapedRecipeHandler();
		final HugeShapelessRecipeHandler hugeShapelessRecipeHandler = new HugeShapelessRecipeHandler();
		API.registerRecipeHandler(bigShapedRecipeHandler);
		API.registerUsageHandler(bigShapedRecipeHandler);
		API.registerUsageHandler(bigShapelessRecipeHandler);
		API.registerUsageHandler(bigShapelessRecipeHandler);
		API.registerRecipeHandler(hugeShapedRecipeHandler);
		API.registerUsageHandler(hugeShapedRecipeHandler);
		API.registerRecipeHandler(hugeShapelessRecipeHandler);
		API.registerUsageHandler(hugeShapelessRecipeHandler);
	}
}