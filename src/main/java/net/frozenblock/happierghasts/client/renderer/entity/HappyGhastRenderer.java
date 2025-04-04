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

package net.frozenblock.happierghasts.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.client.HGModelLayers;
import net.frozenblock.happierghasts.client.model.HappyGhastHarnessModel;
import net.frozenblock.happierghasts.client.model.HappyGhastModel;
import net.frozenblock.happierghasts.client.renderer.entity.state.HappyGhastRenderState;
import net.frozenblock.happierghasts.client.resources.model.HGEquipmentClientInfoLayerTypes;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HappyGhastRenderer extends AgeableMobRenderer<HappyGhast, HappyGhastRenderState, HappyGhastModel> {
	private static final ResourceLocation HAPPY_GHAST_LOCATION = HGConstants.id("textures/entity/happy_ghast/adult.png");
	private static final ResourceLocation HAPPY_GHAST_BABY_LOCATION = HGConstants.id("textures/entity/happy_ghast/baby.png");

	public HappyGhastRenderer(EntityRendererProvider.Context context) {
		super(context, new HappyGhastModel(context.bakeLayer(HGModelLayers.HAPPY_GHAST)), new HappyGhastModel(context.bakeLayer(HGModelLayers.GHASTLING)), 1.5F);
		this.addLayer(
			new SimpleEquipmentLayer<>(
				this,
				context.getEquipmentRenderer(),
				HGEquipmentClientInfoLayerTypes.HAPPY_GHAST_HARNESS,
				happyGhastRenderState -> happyGhastRenderState.harness,
				new HappyGhastHarnessModel(context.bakeLayer(HGModelLayers.HAPPY_GHAST_HARNESS)),
				new HappyGhastHarnessModel(context.bakeLayer(HGModelLayers.GHASTLING_HARNESS))
			)
		);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull HappyGhastRenderState happyGhastRenderState) {
		return happyGhastRenderState.isBaby ? HAPPY_GHAST_BABY_LOCATION : HAPPY_GHAST_LOCATION;
	}

	@Override
	public @NotNull HappyGhastRenderState createRenderState() {
		return new HappyGhastRenderState();
	}

	@Override
	public void extractRenderState(HappyGhast happyGhast, HappyGhastRenderState happyGhastRenderState, float f) {
		super.extractRenderState(happyGhast, happyGhastRenderState, f);
		happyGhastRenderState.harness = happyGhast.getItemBySlot(EquipmentSlot.SADDLE);
		happyGhastRenderState.isBeingRidden = happyGhast.hasControllingPassenger();
	}
}
