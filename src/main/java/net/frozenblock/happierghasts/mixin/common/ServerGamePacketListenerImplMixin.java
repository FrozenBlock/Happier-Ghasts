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

package net.frozenblock.happierghasts.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

	@WrapOperation(
		method = "handleMoveVehicle",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;isNoGravity()Z"
		)
	)
	public boolean happierGhasts$correctVehicleFlyingCheck(Entity instance, Operation<Boolean> original) {
		if (instance.getType() == HGEntityTypes.HAPPY_GHAST) return true;
		return original.call(instance);
	}

	@WrapOperation(
		method = "handleMovePlayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z"
		)
	)
	public boolean happierGhasts$correctPlayerFlyingCheck(ServerPlayer instance, Holder holder, Operation<Boolean> original) {
		if (instance.getVehicle() != null && instance.getVehicle().getType() == HGEntityTypes.HAPPY_GHAST) return true;
		return original.call(instance, holder);
	}
}
