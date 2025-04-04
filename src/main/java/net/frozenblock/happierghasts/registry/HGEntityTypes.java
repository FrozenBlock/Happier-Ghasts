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

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class HGEntityTypes {
	public static final EntityType<HappyGhast> HAPPY_GHAST = register(
		"happy_ghast",
		EntityType.Builder.of(HappyGhast::new, MobCategory.MISC)
			.sized(4.75F, 4.75F)
			.eyeHeight(2.6F)
			.passengerAttachments(
				new Vec3(0D, 4.75D, 2.2D),
				new Vec3(2.2D, 4.75D, 0D),
				new Vec3(-2.2D, 4.75D, 0D),
				new Vec3(0D, 4.75D, -2.2D)
			)
			.ridingOffset(0.5F)
			.clientTrackingRange(10)
	);

	private HGEntityTypes() {
		throw new UnsupportedOperationException("HGEntityTypes contains only static declarations.");
	}

	public static void init() {
		FabricDefaultAttributeRegistry.register(HAPPY_GHAST, HappyGhast.createAttributes());
	}

	static {
		HGConstants.logWithModId("Registering Entities for", HGConstants.UNSTABLE_LOGGING);
	}

	private static <T extends Entity> @NotNull EntityType<T> register(String string, EntityType.@NotNull Builder<T> builder) {
		ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, HGConstants.id(string));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
	}

}
