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

package net.frozenblock.happierghasts.datagen.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.client.resources.model.HGEquipmentClientInfoLayerTypes;
import net.frozenblock.happierghasts.registry.HGEquipmentAssets;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public class HGEquipmentAssetProvider implements DataProvider {
	private final PackOutput.PathProvider pathProvider;

	public HGEquipmentAssetProvider(@NotNull PackOutput packOutput) {
		this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
	}

	private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> biConsumer) {
		for (Map.Entry<DyeColor, ResourceKey<EquipmentAsset>> entry : HGEquipmentAssets.HARNESSES.entrySet()) {
			DyeColor dyeColor = entry.getKey();
			ResourceKey<EquipmentAsset> resourceKey = entry.getValue();
			biConsumer.accept(
				resourceKey,
				EquipmentClientInfo.builder()
					.addLayers(HGEquipmentClientInfoLayerTypes.HAPPY_GHAST_HARNESS, new EquipmentClientInfo.Layer(HGConstants.id(dyeColor.getSerializedName())))
					.build()
			);
		}
	}

	@Override
	public @NotNull CompletableFuture<?> run(CachedOutput cachedOutput) {
		Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
		bootstrap((resourceKey, equipmentClientInfo) -> {
			if (map.putIfAbsent(resourceKey, equipmentClientInfo) != null) {
				throw new IllegalStateException("Tried to register equipment asset twice for id: " + resourceKey);
			}
		});
		return DataProvider.saveAll(cachedOutput, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
	}

	@Override
	public @NotNull String getName() {
		return "Happier Ghasts Equipment Asset Definitions";
	}
}
