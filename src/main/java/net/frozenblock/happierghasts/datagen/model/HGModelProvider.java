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
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.happierghasts.registry.HGItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HGModelProvider extends FabricModelProvider {

	public HGModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(HGItems.BLACK_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.BLUE_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.BROWN_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.CYAN_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.GRAY_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.GREEN_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.LIGHT_BLUE_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.LIGHT_GRAY_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.LIME_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.MAGENTA_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.ORANGE_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.PINK_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.PURPLE_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.RED_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.WHITE_HARNESS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(HGItems.YELLOW_HARNESS, ModelTemplates.FLAT_ITEM);
	}

}
