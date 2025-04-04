package net.frozenblock.happierghasts.client.resources.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.EquipmentClientInfo;

@Environment(EnvType.CLIENT)
public class HGEquipmentClientInfoLayerTypes {
	public static EquipmentClientInfo.LayerType HAPPY_GHAST_HARNESS;

	static {
		EquipmentClientInfo.LayerType.values();
	}
}
