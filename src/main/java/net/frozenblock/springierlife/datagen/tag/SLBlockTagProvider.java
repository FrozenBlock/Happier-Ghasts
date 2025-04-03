/*
 * Copyright 2025 FrozenBlock
 * This file is part of Springier Life.
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

package net.frozenblock.springierlife.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.springierlife.tag.SLBlockTags;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class SLBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public SLBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(SLBlockTags.WIND_FALLING_LEAVES)
			.add(Blocks.CHERRY_LEAVES)
			.add(WWBlocks.YELLOW_MAPLE_LEAVES)
			.add(WWBlocks.ORANGE_MAPLE_LEAVES)
			.add(WWBlocks.RED_MAPLE_LEAVES);

		this.getOrCreateTagBuilder(SLBlockTags.CREAKING_COPPER)
			.add(Blocks.COPPER_BLOCK)
			.add(Blocks.EXPOSED_COPPER)
			.add(Blocks.WEATHERED_COPPER)
			.add(Blocks.OXIDIZED_COPPER)
			.add(Blocks.WAXED_COPPER_BLOCK)
			.add(Blocks.WAXED_EXPOSED_COPPER)
			.add(Blocks.WAXED_WEATHERED_COPPER)
			.add(Blocks.WAXED_OXIDIZED_COPPER)

			.add(Blocks.CUT_COPPER)
			.add(Blocks.EXPOSED_CUT_COPPER)
			.add(Blocks.WEATHERED_CUT_COPPER)
			.add(Blocks.OXIDIZED_CUT_COPPER)
			.add(Blocks.WAXED_CUT_COPPER)
			.add(Blocks.WAXED_EXPOSED_CUT_COPPER)
			.add(Blocks.WAXED_WEATHERED_CUT_COPPER)
			.add(Blocks.WAXED_OXIDIZED_CUT_COPPER)

			.add(Blocks.CUT_COPPER_STAIRS)
			.add(Blocks.EXPOSED_CUT_COPPER_STAIRS)
			.add(Blocks.WEATHERED_CUT_COPPER_STAIRS)
			.add(Blocks.OXIDIZED_CUT_COPPER_STAIRS)
			.add(Blocks.WAXED_CUT_COPPER_STAIRS)
			.add(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS)
			.add(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS)
			.add(Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)

			.add(Blocks.CUT_COPPER_SLAB)
			.add(Blocks.EXPOSED_CUT_COPPER_SLAB)
			.add(Blocks.WEATHERED_CUT_COPPER_SLAB)
			.add(Blocks.OXIDIZED_CUT_COPPER_SLAB)
			.add(Blocks.WAXED_CUT_COPPER_SLAB)
			.add(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)
			.add(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)
			.add(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)

			.add(Blocks.COPPER_GRATE)
			.add(Blocks.EXPOSED_COPPER_GRATE)
			.add(Blocks.WEATHERED_COPPER_GRATE)
			.add(Blocks.OXIDIZED_COPPER_GRATE)
			.add(Blocks.WAXED_COPPER_GRATE)
			.add(Blocks.WAXED_EXPOSED_COPPER_GRATE)
			.add(Blocks.WAXED_WEATHERED_COPPER_GRATE)
			.add(Blocks.WAXED_OXIDIZED_COPPER_GRATE)

			.add(Blocks.COPPER_BULB)
			.add(Blocks.EXPOSED_COPPER_BULB)
			.add(Blocks.WEATHERED_COPPER_BULB)
			.add(Blocks.OXIDIZED_COPPER_BULB)
			.add(Blocks.WAXED_COPPER_BULB)
			.add(Blocks.WAXED_EXPOSED_COPPER_BULB)
			.add(Blocks.WAXED_WEATHERED_COPPER_BULB)
			.add(Blocks.WAXED_OXIDIZED_COPPER_BULB)

			.add(Blocks.CHISELED_COPPER)
			.add(Blocks.EXPOSED_CHISELED_COPPER)
			.add(Blocks.WEATHERED_CHISELED_COPPER)
			.add(Blocks.OXIDIZED_CHISELED_COPPER)
			.add(Blocks.WAXED_CHISELED_COPPER)
			.add(Blocks.WAXED_EXPOSED_CHISELED_COPPER)
			.add(Blocks.WAXED_WEATHERED_CHISELED_COPPER)
			.add(Blocks.WAXED_OXIDIZED_CHISELED_COPPER);
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

}
