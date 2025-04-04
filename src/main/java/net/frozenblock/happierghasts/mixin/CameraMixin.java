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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public class CameraMixin {

	@WrapOperation(
		method = "setup",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"
		)
	)
	public float happierGhasts$zoomFurtherWhenOnHappyGhast(
		Camera instance, float f, Operation<Float> original,
		@Local(argsOnly = true) Entity entity
	) {
		if (entity.isPassenger() && entity.getVehicle().getType() == HGEntityTypes.HAPPY_GHAST) {
			f *= 1.25F;
		}
		return original.call(instance, f);
	}

}
