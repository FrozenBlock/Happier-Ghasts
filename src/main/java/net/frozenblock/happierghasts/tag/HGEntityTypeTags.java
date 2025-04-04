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

package net.frozenblock.happierghasts.tag;

import net.frozenblock.happierghasts.HGConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class HGEntityTypeTags {
	public static final TagKey<EntityType<?>> GHASTLING_FOLLOWS = bind("ghastling_follows");

	private HGEntityTypeTags() {
		throw new UnsupportedOperationException("HGItemTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<EntityType<?>> bind(@NotNull String path) {
		return TagKey.create(Registries.ENTITY_TYPE, HGConstants.id(path));
	}
}
