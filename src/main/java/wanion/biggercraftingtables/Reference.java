package wanion.biggercraftingtables;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Reference
{
	public static final String MOD_ID = "biggercraftingtables";
	public static final String MOD_NAME = "Bigger Crafting Tables";
	public static final String MOD_VERSION = "1.7.10-1.4";
	public static final String DEPENDENCIES = "required-after:wanionlib@[1.7.10-1.4,)";
	public static final String TARGET_MC_VERSION = "[1.7.10]";
	public static final String CLIENT_PROXY = "wanion.biggercraftingtables.client.ClientProxy";
	public static final String SERVER_PROXY = "wanion.biggercraftingtables.CommonProxy";
	public static final Random RANDOM = new Random();
	public static final List<String> TYPES = Arrays.asList("Big", "Huge");
}