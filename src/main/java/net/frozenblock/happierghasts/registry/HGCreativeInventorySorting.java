/*
 * Copyright 2025 FrozenBlock
 * This file is part of Happier Ghasts.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.happierghasts.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class HGCreativeInventorySorting {

	public static void init() {
		addBeforeInNaturalBlocks(Items.BONE_BLOCK, HGBlocks.DRIED_GHAST);

		addAfterInSpawnEggs(Items.GUARDIAN_SPAWN_EGG, HGItems.HAPPY_GHAST_SPAWN_EGG);

		addAfterInToolsAndUtilities(Items.SADDLE, HGItems.WHITE_HARNESS);
		addAfterInToolsAndUtilities(HGItems.WHITE_HARNESS, HGItems.LIGHT_GRAY_HARNESS);
		addAfterInToolsAndUtilities(HGItems.LIGHT_GRAY_HARNESS, HGItems.GRAY_HARNESS);
		addAfterInToolsAndUtilities(HGItems.GRAY_HARNESS, HGItems.BLACK_HARNESS);
		addAfterInToolsAndUtilities(HGItems.BLACK_HARNESS, HGItems.BROWN_HARNESS);
		addAfterInToolsAndUtilities(HGItems.BROWN_HARNESS, HGItems.RED_HARNESS);
		addAfterInToolsAndUtilities(HGItems.RED_HARNESS, HGItems.ORANGE_HARNESS);
		addAfterInToolsAndUtilities(HGItems.ORANGE_HARNESS, HGItems.YELLOW_HARNESS);
		addAfterInToolsAndUtilities(HGItems.YELLOW_HARNESS, HGItems.LIME_HARNESS);
		addAfterInToolsAndUtilities(HGItems.LIME_HARNESS, HGItems.GREEN_HARNESS);
		addAfterInToolsAndUtilities(HGItems.GREEN_HARNESS, HGItems.CYAN_HARNESS);
		addAfterInToolsAndUtilities(HGItems.CYAN_HARNESS, HGItems.LIGHT_BLUE_HARNESS);
		addAfterInToolsAndUtilities(HGItems.LIGHT_BLUE_HARNESS, HGItems.BLUE_HARNESS);
		addAfterInToolsAndUtilities(HGItems.BLUE_HARNESS, HGItems.PURPLE_HARNESS);
		addAfterInToolsAndUtilities(HGItems.PURPLE_HARNESS, HGItems.MAGENTA_HARNESS);
		addAfterInToolsAndUtilities(HGItems.MAGENTA_HARNESS, HGItems.PINK_HARNESS);
	}

	private static void addBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}
	private static void addAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

}
