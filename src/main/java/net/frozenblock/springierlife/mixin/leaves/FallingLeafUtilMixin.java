package net.frozenblock.springierlife.mixin.leaves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.springierlife.block.impl.BlockAmbienceUtil;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(FallingLeafUtil.class)
public class FallingLeafUtilMixin {

	@ModifyExpressionValue(
		method = "addFallingLeafParticles",
		at = @At(
			value = "INVOKE",
			target = "Lnet/frozenblock/wilderwild/block/impl/FallingLeafUtil$LeafParticleData;particleChance()F"
		)
	)
	private static float springierLife$modifyLeafChanceForWind(
		float original,
		@Local(argsOnly = true) Level level,
		@Local(argsOnly = true) BlockPos pos
	) {
		float windLength = BlockAmbienceUtil.horizontalWindStrength(level, pos, true, false);
		return original * (Math.max(1F, windLength * 3F));
	}

}
