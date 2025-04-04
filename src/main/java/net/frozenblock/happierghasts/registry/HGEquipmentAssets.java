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
