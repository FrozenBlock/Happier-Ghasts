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
