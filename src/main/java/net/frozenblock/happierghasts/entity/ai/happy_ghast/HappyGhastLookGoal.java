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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class HappyGhastLookGoal extends Goal {
	private final HappyGhast happyGhast;

	public HappyGhastLookGoal(HappyGhast happyGhast) {
		this.happyGhast = happyGhast;
		this.setFlags(EnumSet.of(Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (this.happyGhast.getTarget() == null) {
			Vec3 vec3 = this.happyGhast.getDeltaMovement();
			this.happyGhast.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float) Math.PI));
			this.happyGhast.yBodyRot = this.happyGhast.getYRot();
		} else {
			LivingEntity livingEntity = this.happyGhast.getTarget();
			if (livingEntity.distanceToSqr(this.happyGhast) < 256D) {
				double e = livingEntity.getX() - this.happyGhast.getX();
				double f = livingEntity.getZ() - this.happyGhast.getZ();
				this.happyGhast.setYRot(-((float) Mth.atan2(e, f)) * (180F / (float) Math.PI));
				this.happyGhast.yBodyRot = this.happyGhast.getYRot();
			}
		}
	}
}
