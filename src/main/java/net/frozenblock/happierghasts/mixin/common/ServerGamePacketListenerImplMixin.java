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
