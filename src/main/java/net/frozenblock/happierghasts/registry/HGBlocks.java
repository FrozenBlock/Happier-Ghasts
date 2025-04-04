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

import java.util.function.Function;
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.block.DriedGhastBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class HGBlocks {

	public static final Block DRIED_GHAST = register("dried_ghast",
		DriedGhastBlock::new,
		BlockBehaviour.Properties.of()
			.pushReaction(PushReaction.DESTROY)
			.strength(0.5F)
			.destroyTime(0.75F)
			.randomTicks()
			.noOcclusion(),
		new Item.Properties()
			.rarity(Rarity.UNCOMMON)
	);

	public static void init() {
	}

	private static <T extends Block> @NotNull T registerWithoutItem(String path, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
		ResourceLocation id = HGConstants.id(path);
		return doRegister(id, makeBlock(block, properties, id));
	}

	private static <T extends Block> @NotNull T register(String path, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
		T registered = registerWithoutItem(path, block, properties);
		Items.registerBlock(registered, itemProperties);
		return registered;
	}

	private static <T extends Block> @NotNull T doRegister(ResourceLocation id, T block) {
		if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) {
			return Registry.register(BuiltInRegistries.BLOCK, id, block);
		}
		throw new IllegalArgumentException("Block with id " + id + " is already in the block registry.");
	}

	private static <T extends Block> T makeBlock(@NotNull Function<BlockBehaviour.Properties, T> function, BlockBehaviour.@NotNull Properties properties, ResourceLocation id) {
		return function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
	}

}
