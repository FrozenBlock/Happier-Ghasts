package net.frozenblock.happierghasts.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.client.resources.sounds.RidingHappyGhastSoundInstance;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

	@Shadow
	@Final
	protected Minecraft minecraft;

	@Inject(
		method = "startRiding",
		at = @At(
			value = "RETURN",
			ordinal = 1
		)
	)
	public void happierGhasts$playGhastRidingSound(Entity entity, boolean bl, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof HappyGhast happyGhast) {
			this.minecraft.getSoundManager().play(new RidingHappyGhastSoundInstance(LocalPlayer.class.cast(this), happyGhast));
		}
	}

}
