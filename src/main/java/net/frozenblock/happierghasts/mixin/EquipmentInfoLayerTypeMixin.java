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

package net.frozenblock.happierghasts.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.client.resources.model.HGEquipmentClientInfoLayerTypes;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
@Mixin(EquipmentClientInfo.LayerType.class)
public class EquipmentInfoLayerTypeMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@SuppressWarnings("ShadowTarget")
	@Final
	@Shadow
	@Mutable
	private static EquipmentClientInfo.LayerType[] $VALUES;

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static EquipmentClientInfo.LayerType happierGhasts$newBlock(String internalName, int internalId, String layerId) {
		throw new AssertionError("Mixin injection failed - Happier Ghasts EquipmentInfoLayerTypeMixin.");
	}

	@Inject(
		method = "<clinit>",
		at = @At(value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;$VALUES:[Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;",
			shift = At.Shift.AFTER
		)
	)
	private static void happierGhasts$addCustomLayerType(CallbackInfo info) {
		var layerTypes = new ArrayList<>(Arrays.asList($VALUES));
		var last = layerTypes.get(layerTypes.size() - 1);

		var happyGhastHarness = happierGhasts$newBlock(
			"HAPPIERGHASTSHAPPYGHASTHARNESS",
			last.ordinal() + 1,
			"happy_ghast_harness"
		);

		HGEquipmentClientInfoLayerTypes.HAPPY_GHAST_HARNESS = happyGhastHarness;
		layerTypes.add(happyGhastHarness);

		$VALUES = layerTypes.toArray(new EquipmentClientInfo.LayerType[0]);
	}
}
