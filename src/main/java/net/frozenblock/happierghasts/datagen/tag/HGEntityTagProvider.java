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

package net.frozenblock.happierghasts.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.happierghasts.tag.HGEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class HGEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public HGEntityTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(HGEntityTypeTags.GHASTLING_FOLLOWS)
			.add(EntityType.PLAYER)
			.add(EntityType.ARMADILLO)
			.add(EntityType.BEE)
			.add(EntityType.CAMEL)
			.add(EntityType.CAT)
			.add(EntityType.CHICKEN)
			.add(EntityType.COW)
			.add(EntityType.DONKEY)
			.add(EntityType.FOX)
			.add(EntityType.GOAT)
			.add(EntityType.HORSE)
			.add(EntityType.SKELETON_HORSE)
			.add(EntityType.LLAMA)
			.add(EntityType.MULE)
			.add(EntityType.OCELOT)
			.add(EntityType.PANDA)
			.add(EntityType.PARROT)
			.add(EntityType.PIG)
			.add(EntityType.POLAR_BEAR)
			.add(EntityType.RABBIT)
			.add(EntityType.SHEEP)
			.add(EntityType.SNIFFER)
			.add(EntityType.STRIDER)
			.add(EntityType.VILLAGER)
			.add(EntityType.WOLF);
	}
}
