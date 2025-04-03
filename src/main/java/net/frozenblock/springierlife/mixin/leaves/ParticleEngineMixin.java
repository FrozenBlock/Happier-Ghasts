package net.frozenblock.springierlife.mixin.leaves;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

	@Shadow
	@Final
	private RandomSource random;

	@Shadow
	public abstract @Nullable Particle createParticle(ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i);

	@Inject(method = "destroy", at = @At(value = "HEAD"))
	public void springierLife$spawnLeafParticlesOnDestroy(BlockPos pos, BlockState state, CallbackInfo info) {
		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isPresent()) {
			FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
			for (int i = 0; i < this.random.nextInt(2, 4); i++) {
				this.createParticle(
					FallingLeafUtil.createLeafParticleOptions(fallingLeafData),
					pos.getX() + 0.5D + this.random.nextDouble() * 0.25D,
					pos.getY() + 0.5D + this.random.nextDouble() * 0.25D,
					pos.getZ() + 0.5D + this.random.nextDouble() * 0.25D,
					this.random.nextGaussian() * 0.05D,
					this.random.nextGaussian() * 0.025D,
					this.random.nextGaussian() * 0.05D
				);
			}
		}
	}

}
