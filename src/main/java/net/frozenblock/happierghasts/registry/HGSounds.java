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

import net.frozenblock.happierghasts.HGConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public final class HGSounds {

	public static final SoundEvent HAPPY_GHAST_AMBIENT = register("entity.happy_ghast.ambient", Optional.empty());
	public static final SoundEvent HAPPY_GHAST_HURT = register("entity.happy_ghast.hurt", Optional.empty());
	public static final SoundEvent HAPPY_GHAST_DEATH = register("entity.happy_ghast.death", Optional.empty());
	public static final SoundEvent HAPPY_GHAST_GOGGLES_DOWN = register("entity.happy_ghast.goggles_down", Optional.empty());
	public static final SoundEvent HAPPY_GHAST_GOGGLES_UP = register("entity.happy_ghast.goggles_up", Optional.empty());
	public static final Holder<SoundEvent> HAPPY_GHAST_HARNESS_EQUIP = registerForHolder("entity.happy_ghast.harness_equip");
	public static final SoundEvent HAPPY_GHAST_HARNESS_UNEQUIP = register("entity.happy_ghast.harness_unequip", Optional.empty());
	public static final SoundEvent HAPPY_GHAST_RIDE = register("entity.happy_ghast.ride", Optional.empty());

	public static final SoundEvent GHASTLING_AMBIENT = register("entity.ghastling.ambient", Optional.empty());
	public static final SoundEvent GHASTLING_HURT = register("entity.ghastling.hurt", Optional.empty());
	public static final SoundEvent GHASTLING_DEATH = register("entity.ghastling.death", Optional.empty());
	public static final SoundEvent GHASTLING_SPAWN = register("entity.ghastling.spawn", Optional.empty());

	public static final SoundEvent DRIED_GHAST_AMBIENT = register("entity.dried_ghast.ambient", Optional.empty());
	public static final SoundEvent DRIED_GHAST_AMBIENT_WATER = register("entity.dried_ghast.ambient_water", Optional.empty());

	public static final SoundEvent DRIED_GHAST_BREAK = register("block.dried_ghast.break", Optional.empty());
	public static final SoundEvent DRIED_GHAST_STEP = register("block.dried_ghast.step", Optional.empty());
	public static final SoundEvent DRIED_GHAST_PLACE = register("block.dried_ghast.place", Optional.empty());
	public static final SoundEvent DRIED_GHAST_PLACE_WATER = register("block.dried_ghast.place_water", Optional.empty());
	public static final SoundEvent DRIED_GHAST_HIT = register("block.dried_ghast.hit", Optional.empty());
	public static final SoundEvent DRIED_GHAST_FALL = register("block.dried_ghast.fall", Optional.empty());
	public static final SoundType DRIED_GHAST = new SoundType(
		0.8F,
		1F,
		DRIED_GHAST_BREAK,
		DRIED_GHAST_STEP,
		DRIED_GHAST_PLACE,
		DRIED_GHAST_HIT,
		DRIED_GHAST_FALL
	);
	public static final SoundType DRIED_GHAST_WATER = new SoundType(
		0.8F,
		1F,
		DRIED_GHAST_BREAK,
		DRIED_GHAST_STEP,
		DRIED_GHAST_PLACE_WATER,
		DRIED_GHAST_HIT,
		DRIED_GHAST_FALL
	);

	private HGSounds() {
		throw new UnsupportedOperationException("HGSounds contains only static declarations.");
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull String string) {
		return registerForHolder(HGConstants.id(string));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation) {
		return registerForHolder(resourceLocation, resourceLocation);
	}

	@NotNull
	public static SoundEvent register(@NotNull String path, Optional<Float> fixedRange) {
		var id = HGConstants.id(path);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, new SoundEvent(id, fixedRange));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation, @NotNull ResourceLocation resourceLocation2) {
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
	}

	public static void init() {
		HGConstants.logWithModId("Registering SoundEvents for", HGConstants.UNSTABLE_LOGGING);
	}

}
