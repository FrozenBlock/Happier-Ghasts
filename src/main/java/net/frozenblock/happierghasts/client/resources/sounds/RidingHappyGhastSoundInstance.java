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

package net.frozenblock.happierghasts.client.resources.sounds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.frozenblock.happierghasts.registry.HGSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class RidingHappyGhastSoundInstance extends AbstractTickableSoundInstance {
	private final Player player;
	private final HappyGhast happyGhast;

	public RidingHappyGhastSoundInstance(Player player, @NotNull HappyGhast happyGhast) {
		super(HGSounds.HAPPY_GHAST_RIDE, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
		this.player = player;
		this.happyGhast = happyGhast;
		this.attenuation = SoundInstance.Attenuation.NONE;
		this.looping = true;
		this.delay = 0;
		this.volume = 0F;
	}

	@Override
	public boolean canPlaySound() {
		return !this.happyGhast.isSilent();
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}

	@Override
	public void tick() {
		if (this.happyGhast.isRemoved() || !this.player.isPassenger() || this.player.getVehicle() != this.happyGhast) {
			this.stop();
		} else if (this.player.isUnderWater()) {
			this.volume = 0F;
		} else {
			float movementDistance = (float) this.happyGhast.getDeltaMovement().length();
			if (movementDistance >= 0.01F) {
				this.volume = Mth.clampedLerp(0F, 1F, movementDistance);
			} else {
				this.volume = 0F;
			}
		}
	}
}
