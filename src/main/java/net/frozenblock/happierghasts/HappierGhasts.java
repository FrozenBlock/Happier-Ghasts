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

package net.frozenblock.happierghasts;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.ModInitializer;
import net.frozenblock.happierghasts.block.DriedGhastBlock;
import net.frozenblock.happierghasts.registry.HGBlocks;
import net.frozenblock.happierghasts.registry.HGCreativeInventorySorting;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.frozenblock.happierghasts.registry.HGItems;
import net.frozenblock.happierghasts.registry.HGSounds;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.lib.worldgen.structure.api.WeightedProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.WeightedRuleProcessor;
import net.minecraft.core.Direction;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

public final class HappierGhasts implements ModInitializer {

	@Override
	public void onInitialize() {
		HGEntityTypes.init();
		HGBlocks.init();
		HGItems.init();
		HGSounds.init();

		HGCreativeInventorySorting.init();

		StructureProcessorApi.addProcessor(
			BuiltinStructures.NETHER_FOSSIL.location(),
			new WeightedRuleProcessor(
				ImmutableList.of(
					new WeightedProcessorRule(
						new BlockMatchTest(HGBlocks.DRIED_GHAST),
						AlwaysTrueTest.INSTANCE,
						WeightedList.<BlockState>builder()
							.add(Blocks.AIR.defaultBlockState(), 30)
							.add(HGBlocks.DRIED_GHAST.defaultBlockState().setValue(DriedGhastBlock.FACING, Direction.NORTH), 1)
							.add(HGBlocks.DRIED_GHAST.defaultBlockState().setValue(DriedGhastBlock.FACING, Direction.EAST), 1)
							.add(HGBlocks.DRIED_GHAST.defaultBlockState().setValue(DriedGhastBlock.FACING, Direction.SOUTH), 1)
							.add(HGBlocks.DRIED_GHAST.defaultBlockState().setValue(DriedGhastBlock.FACING, Direction.WEST), 1)
							.build()
					)
				)
			)
		);
	}

}
