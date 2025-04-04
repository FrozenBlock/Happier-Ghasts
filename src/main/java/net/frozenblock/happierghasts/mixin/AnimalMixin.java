package net.frozenblock.happierghasts.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public class AnimalMixin {

	@ModifyExpressionValue(
		method = "mobInteract",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/Animal;getSpeedUpSecondsWhenFeeding(I)I"
		)
	)
	public int happierghasts$fasterSnowballGrowth(int original) {
		if (Animal.class.cast(this) instanceof HappyGhast) original *= 2;
		return original;
	}
}
