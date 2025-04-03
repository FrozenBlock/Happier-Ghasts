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
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(ShelfFungiBlock.class)
public abstract class ShelfFungiBlockMixin extends FaceAttachedHorizontalDirectionalBlock implements BlockAmbientTickInterface {

	protected ShelfFungiBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void springierLife$ambientTick(Level level, BlockState state, BlockPos blockPos, RandomSource random) {
		if (random.nextFloat() <= 0.004F) {
			Direction oppositeDirection = getConnectedDirection(state).getOpposite();
			BlockPos connectedPos = blockPos.relative(oppositeDirection);
			if (level.getBlockState(connectedPos).is(BlockTags.OVERWORLD_NATURAL_LOGS)) {
				BlockAmbienceUtil.playSound(
					level,
					blockPos,
					SLSounds.AMBIENT_OVERWORLD_CREAK,
					SoundSource.AMBIENT,
					0.1F + (random.nextFloat() * 0.2F),
					0.85F + (random.nextFloat() * 0.25F)
				);
			}
		}
	}
}
