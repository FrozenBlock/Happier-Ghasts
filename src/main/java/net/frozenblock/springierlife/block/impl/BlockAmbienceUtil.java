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

package net.frozenblock.springierlife.block.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.springierlife.registry.SLSounds;
import net.frozenblock.springierlife.tag.SLBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class BlockAmbienceUtil {
	private static final int COMPLETELY_SURROUNDED = 6;
	private static final int MOSTLY_SURROUNDED = 4;

	public static void doAmbientTick(@NotNull BlockState blockState, Level level, BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (blockState.getBlock() instanceof LeavesBlock) {
			playLeavesAmbience(blockState, level, blockPos, randomSource);
		}

		if (blockState.is(SLBlockTags.CREAKING_COPPER)) {
			playCopperAmbience(level, blockPos, randomSource);
		}

		if (blockState.isAir()) {
			playAirAmbience(level, blockPos, randomSource);
		}

		if (blockState.getBlock() instanceof BlockAmbientTickInterface ambientTickInterface) {
			ambientTickInterface.springierLife$ambientTick(level, blockState, blockPos, randomSource);
		}
	}

	public static void playLeavesAmbience(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
		if (state.getOptionalValue(LeavesBlock.DISTANCE).orElse(0) >= 7) return;

		SoundEvent soundToPlay = null;
		double soundYOffset = 0D;

		float windStrength = horizontalWindStrength(level, pos, true, false);

		float baseVolume = 0.05F;
		float maxRandomVolume = 0.15F;
		float basePitch = getFromLerp(windStrength, 0.75F, 1.1F);
		float maxRandomPitch = getFromLerp(windStrength, 0.075F, 0.2F);

		if (state.is(SLBlockTags.WIND_FALLING_LEAVES)) {
			if (random.nextFloat() <= getFromLerp(windStrength, 0.0005F, 0.005F)) {
				if (!isBrightEnoughForVegetationWind(level, pos) || !hasNeighboringBlocks(level, pos, COMPLETELY_SURROUNDED, blockState -> blockState.is(BlockTags.LEAVES))) return;
				soundToPlay = windStrength > 0.75F ? SLSounds.AMBIENT_OVERWORLD_WIND_FALLING_LEAVES_FAST : SLSounds.AMBIENT_OVERWORLD_WIND_FALLING_LEAVES;

				baseVolume = getFromLerp(windStrength, 0.015F, 0.09F);
				maxRandomVolume = getFromLerp(windStrength, 0.005F, 0.15F);

				soundYOffset = -2D;
			}
		} else {
			if (random.nextFloat() <= windStrength * 0.005F) {
				if (!isBrightEnoughForVegetationWind(level, pos) || !hasNeighboringBlocks(level, pos, MOSTLY_SURROUNDED, blockState -> blockState.is(BlockTags.LEAVES))) return;
				soundToPlay = windStrength > 0.75F ? SLSounds.AMBIENT_OVERWORLD_WIND_LEAVES_FAST : SLSounds.AMBIENT_OVERWORLD_WIND_LEAVES;

				baseVolume = getFromLerp(windStrength, 0.0075F, 0.09F);
				maxRandomVolume = getFromLerp(windStrength, 0.005F, 0.15F);
			}
		}

		if (soundToPlay != null) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + soundYOffset + 0.5D,
				pos.getZ() + 0.5D,
				soundToPlay,
				SoundSource.AMBIENT,
				baseVolume + (random.nextFloat() * maxRandomVolume),
				basePitch + (random.nextFloat() * maxRandomPitch),
				false
			);
		}
	}

	public static void playCopperAmbience(Level level, BlockPos pos, @NotNull RandomSource random) {
		if (!(random.nextFloat() < 0.0003F)) return;
		if (!hasNeighboringBlocks(level, pos, MOSTLY_SURROUNDED, blockState -> blockState.is(SLBlockTags.CREAKING_COPPER))) return;

		BlockAmbienceUtil.playSound(
			level,
			pos,
			SLSounds.AMBIENT_COPPER_CREAK,
			SoundSource.AMBIENT,
			0.1F + (random.nextFloat() * 0.1F),
			0.25F + (random.nextFloat() * 0.9F)
		);
	}

	public static void playAirAmbience(@NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (pos.getY() < level.getSeaLevel()) return;

		SoundEvent soundToPlay = null;
		double soundYOffset = 0D;

		float windStrength = horizontalWindStrength(level, pos, true, true);

		float baseVolume = getFromLerp(windStrength, 0.006F, 0.15F);
		float maxRandomVolume = getFromLerp(windStrength, 0.006F, 0.1F);
		float basePitch = getFromLerp(windStrength, 0.75F, 1.1F);
		float maxRandomPitch = getFromLerp(windStrength, 0.075F, 0.2F);

		if (random.nextFloat() <= windStrength * 0.0001F) {
			if (isBrightEnoughForAirWind(level, pos) && hasNeighboringBlocks(level, pos, COMPLETELY_SURROUNDED, BlockBehaviour.BlockStateBase::isAir)) {
				soundToPlay = windStrength >= 0.85F ? SLSounds.AMBIENT_OVERWORLD_WIND_WHISTLE : SLSounds.AMBIENT_OVERWORLD_WIND;
			}
		}

		if (soundToPlay != null) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + soundYOffset + 0.5D,
				pos.getZ() + 0.5D,
				soundToPlay,
				SoundSource.AMBIENT,
				baseVolume + (random.nextFloat() * maxRandomVolume),
				basePitch + (random.nextFloat() * maxRandomPitch),
				false
			);
		}
	}

	public static float getFromLerp(float lerp, float min, float max) {
		return Mth.lerp(lerp, min, max);
	}

	public static float horizontalWindStrength(@NotNull Level level, @NotNull BlockPos pos, boolean useWeather, boolean scaleWithHeight) {
		float windLength = 0F;
		if (level instanceof ServerLevel serverLevel) {
			windLength = (float) WindManager.getOrCreateWindManager(serverLevel).getWindMovement(pos).horizontalDistance();
		} else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && level.isClientSide) {
			windLength = (float) ClientWindManager.getWindMovement(level, pos).horizontalDistance();
		}

		windLength = Mth.clamp(windLength * 1.25F, 0F, 1F);
		float windScale = 1F;

		if (scaleWithHeight) {
			int height = pos.getY();
			int seaLevel = level.getSeaLevel();
			int maxHeightDifference = level.getMaxY() - seaLevel;
			float heightFromSeaLevel = ((float) (height - seaLevel) / maxHeightDifference);
			windScale = 0.5F + heightFromSeaLevel;
		}

		if (useWeather) {
			if (level.isRaining()) windScale *= 1.3F;
			if (level.isThundering()) windScale *= 1.2F;
		}

		return windLength * Math.min(1.5F, windScale);
	}

	public static boolean isBrightEnoughForAirWind(@NotNull Level level, BlockPos pos) {
		return level.getBrightness(LightLayer.SKY, pos) == LightEngine.MAX_LEVEL;
	}

	public static boolean isBrightEnoughForVegetationWind(@NotNull Level level, BlockPos pos) {
		return level.getBrightness(LightLayer.SKY, pos) > 10;
	}

	public static boolean hasNeighboringBlocks(Level level, BlockPos pos, int requiredBlockCount, Predicate<BlockState> predicate) {
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		int countedBlocks = 0;

		for (Direction direction : Direction.values()) {
			mutablePos.setWithOffset(pos, direction);
			if (predicate.test(level.getBlockState(mutablePos))) countedBlocks += 1;
		}

		return countedBlocks >= requiredBlockCount;
	}

	public static boolean isDay(@NotNull Level level) {
		return !level.dimensionType().hasFixedTime() && Mth.cos(level.getSunAngle(1F)) >= 0F;
	}

	public static boolean isNight(@NotNull Level level) {
		return !level.dimensionType().hasFixedTime() && Mth.cos(level.getSunAngle(1F)) < 0F;
	}

	public static void playSound(@NotNull Level level, @NotNull BlockPos pos, SoundEvent sound, SoundSource source, float volume, float pitch) {
		level.playLocalSound(
			pos.getX() + 0.5D,
			pos.getY() + 0.5D,
			pos.getZ() + 0.5D,
			sound,
			source,
			volume,
			pitch,
			false
		);
	}
}
