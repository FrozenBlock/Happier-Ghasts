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
