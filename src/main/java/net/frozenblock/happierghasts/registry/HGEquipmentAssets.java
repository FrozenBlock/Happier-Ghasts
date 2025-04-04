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
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class HGEquipmentAssets {
	public static final Map<DyeColor, ResourceKey<EquipmentAsset>> HARNESSES = Util.makeEnumMap(
		DyeColor.class,
		dyeColor -> createId("harness_" + dyeColor.getSerializedName())
	);

	static @NotNull ResourceKey<EquipmentAsset> createId(String string) {
		return ResourceKey.create(
			ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("equipment_asset")),
			HGConstants.id(string)
		);
	}
}
