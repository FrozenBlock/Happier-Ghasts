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

package net.frozenblock.happierghasts.entity.ai.happy_ghast;

import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HappyGhastRandomFloatAroundGoal extends Goal {
	private final HappyGhast happyGhast;

	public HappyGhastRandomFloatAroundGoal(HappyGhast happyGhast) {
		this.happyGhast = happyGhast;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		MoveControl moveControl = this.happyGhast.getMoveControl();
		if (!moveControl.hasWanted()) {
			return true;
		} else {
			double d = moveControl.getWantedX() - this.happyGhast.getX();
			double e = moveControl.getWantedY() - this.happyGhast.getY();
			double f = moveControl.getWantedZ() - this.happyGhast.getZ();
			double g = d * d + e * e + f * f;
			return g < 1D || g > 3600D;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		int distance = this.happyGhast.isSaddled() ? 8 : 16;
		RandomSource randomSource = this.happyGhast.getRandom();
		double d = this.happyGhast.getX() + (randomSource.nextFloat() * 2F - 1F) * distance;
		double e = this.happyGhast.getY() + (randomSource.nextFloat() * 2F - 1F) * distance;
		double f = this.happyGhast.getZ() + (randomSource.nextFloat() * 2F - 1F) * distance;
		this.happyGhast.getMoveControl().setWantedPosition(d, e, f, 1D);
	}
}
