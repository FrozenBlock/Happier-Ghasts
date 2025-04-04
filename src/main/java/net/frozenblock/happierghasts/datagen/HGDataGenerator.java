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

package net.frozenblock.happierghasts.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.happierghasts.datagen.loot.HGBlockLootProvider;
import net.frozenblock.happierghasts.datagen.model.HGEquipmentAssetProvider;
import net.frozenblock.happierghasts.datagen.model.HGModelProvider;
import net.frozenblock.happierghasts.datagen.tag.HGEntityTagProvider;
import net.frozenblock.happierghasts.datagen.tag.HGItemTagProvider;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.happierghasts.HGConstants;
import net.minecraft.core.RegistrySetBuilder;
import org.jetbrains.annotations.NotNull;

public final class HGDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		FeatureFlagApi.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS
		pack.addProvider((FabricDataGenerator.Pack.Factory<HGEquipmentAssetProvider>) HGEquipmentAssetProvider::new);
		pack.addProvider(HGModelProvider::new);

		// DATA
		pack.addProvider(HGBlockLootProvider::new);
		pack.addProvider(HGEntityTagProvider::new);
		pack.addProvider(HGItemTagProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
	}

	@Override
	public @NotNull String getEffectiveModId() {
		return HGConstants.MOD_ID;
	}
}
