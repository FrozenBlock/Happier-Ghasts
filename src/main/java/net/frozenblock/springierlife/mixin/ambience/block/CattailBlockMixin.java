/*
 * Copyright 2025 FrozenBlock
 * This file is part of Springier Life.
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

package net.frozenblock.springierlife.mixin.ambience.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.springierlife.block.impl.BlockAmbienceUtil;
import net.frozenblock.springierlife.block.impl.BlockAmbientTickInterface;
import net.frozenblock.springierlife.registry.SLSounds;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(CattailBlock.class)
public class CattailBlockMixin implements BlockAmbientTickInterface {

	@Override
	public void springierLife$ambientTick(Level level, BlockState state, BlockPos blockPos, RandomSource random) {
		if (random.nextFloat() < BlockAmbienceUtil.horizontalWindStrength(level, blockPos, true, false) * 0.045F) {
			if (state.getValue(CattailBlock.WATERLOGGED)) {
				BlockState aboveState = level.getBlockState(blockPos.above());
				if (aboveState.is(state.getBlock()) && !aboveState.getValue(CattailBlock.WATERLOGGED)) {
					if (BlockAmbienceUtil.isBrightEnoughForVegetationWind(level, blockPos)) {
						BlockAmbienceUtil.playSound(
							level,
							blockPos,
							SLSounds.AMBIENT_OVERWORLD_WIND_CATTAIL,
							SoundSource.AMBIENT,
							0.05F,
							0.85F + (random.nextFloat() * 0.25F)
						);
					}
				}
			}
		}
	}
}
