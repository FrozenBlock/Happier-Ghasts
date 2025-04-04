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
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.block.DriedGhastBlock;
import net.frozenblock.happierghasts.registry.HGBlocks;
import net.frozenblock.happierghasts.registry.HGItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class HGModelProvider extends FabricModelProvider {
	private static final TextureSlot TENTACLE = TextureSlot.create("tentacle");
	private static final ModelTemplate DRIED_GHAST_MODEL = new ModelTemplate(
		Optional.of(HGConstants.id("block/template_dried_ghast")),
		Optional.empty(),
		TextureSlot.FRONT,
		TextureSlot.WEST,
		TextureSlot.BACK,
		TextureSlot.EAST,
		TextureSlot.TOP,
		TextureSlot.BOTTOM,
		TENTACLE
	);

	public HGModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators generator) {
		generator.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(HGBlocks.DRIED_GHAST)
					.with(PropertyDispatch.initial(DriedGhastBlock.REHYDRATION_LEVEL)
						.generate(rehydrationLevel -> createDriedGhastModel(generator, rehydrationLevel))
					).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
			);
	}

	public final @NotNull MultiVariant createDriedGhastModel(@NotNull BlockModelGenerators generator, int rehydrationLevel) {
		int modelState = rehydrationLevel + 1;
		TextureMapping driedGhastTextureMapping = new TextureMapping();
		driedGhastTextureMapping.put(TextureSlot.FRONT, HGConstants.id("block/dried_ghast_state_" + modelState + "_front"));
		driedGhastTextureMapping.put(TextureSlot.WEST, HGConstants.id("block/dried_ghast_state_" + modelState + "_right"));
		driedGhastTextureMapping.put(TextureSlot.BACK, HGConstants.id("block/dried_ghast_state_" + modelState + "_back"));
		driedGhastTextureMapping.put(TextureSlot.EAST, HGConstants.id("block/dried_ghast_state_" + modelState + "_left"));
		driedGhastTextureMapping.put(TextureSlot.TOP, HGConstants.id("block/dried_ghast_state_" + modelState + "_top"));
		driedGhastTextureMapping.put(TextureSlot.BOTTOM, HGConstants.id("block/dried_ghast_state_" + modelState + "_bottom"));
		driedGhastTextureMapping.put(TENTACLE, HGConstants.id("block/dried_ghast_state_" + modelState + "_tentacles"));

		ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(HGBlocks.DRIED_GHAST, "_state_" + modelState);
		MultiVariant variant = BlockModelGenerators.plainVariant(DRIED_GHAST_MODEL.create(modelLocation, driedGhastTextureMapping, generator.modelOutput));

		if (modelState == 1) generator.registerSimpleItemModel(HGBlocks.DRIED_GHAST, modelLocation);

		return variant;
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
		generator.generateFlatItem(HGItems.HAPPY_GHAST_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

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
