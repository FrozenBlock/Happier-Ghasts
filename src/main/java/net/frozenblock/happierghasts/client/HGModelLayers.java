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

package net.frozenblock.happierghasts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.client.model.HappyGhastHarnessModel;
import net.frozenblock.happierghasts.client.model.HappyGhastModel;
import net.frozenblock.happierghasts.client.renderer.entity.HappyGhastRenderer;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;

@Environment(EnvType.CLIENT)
public class HGModelLayers {
	public static final ModelLayerLocation HAPPY_GHAST = new ModelLayerLocation(HGConstants.id("happy_ghast"), "main");
	public static final ModelLayerLocation HAPPY_GHAST_HARNESS = new ModelLayerLocation(HGConstants.id("happy_ghast"), "harness");

	public static final ModelLayerLocation GHASTLING = new ModelLayerLocation(HGConstants.id("ghastling"), "main");
	public static final ModelLayerLocation GHASTLING_HARNESS = new ModelLayerLocation(HGConstants.id("ghastling"), "harness");

	public static void init() {
		EntityRendererRegistry.register(HGEntityTypes.HAPPY_GHAST, HappyGhastRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HAPPY_GHAST, HappyGhastModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(HAPPY_GHAST_HARNESS, HappyGhastHarnessModel::createBodyLayer);

		EntityModelLayerRegistry.registerModelLayer(GHASTLING, HappyGhastModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(GHASTLING_HARNESS, HappyGhastHarnessModel::createBabyBodyLayer);
	}
}
