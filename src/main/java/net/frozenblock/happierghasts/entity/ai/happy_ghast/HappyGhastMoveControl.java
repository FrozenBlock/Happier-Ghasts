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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HappyGhastMoveControl extends MoveControl {
	private final HappyGhast happyGhast;
	private int floatDuration;

	public HappyGhastMoveControl(HappyGhast happyGhast) {
		super(happyGhast);
		this.happyGhast = happyGhast;
	}

	@Override
	public void tick() {
		if (this.operation == Operation.MOVE_TO) {
			if (this.floatDuration-- <= 0) {
				this.floatDuration = this.floatDuration + this.happyGhast.getRandom().nextInt(5) + 2;
				Vec3 vec3 = new Vec3(this.wantedX - this.happyGhast.getX(), this.wantedY - this.happyGhast.getY(), this.wantedZ - this.happyGhast.getZ());
				double d = vec3.length();
				vec3 = vec3.normalize();
				if (this.canReach(vec3, Mth.ceil(d))) {
					this.happyGhast.setDeltaMovement(this.happyGhast.getDeltaMovement().add(vec3.scale(0.1)));
				} else {
					this.operation = Operation.WAIT;
				}
			}
		}
	}

	private boolean canReach(Vec3 vec3, int i) {
		AABB aABB = this.happyGhast.getBoundingBox();

		for (int j = 1; j < i; j++) {
			aABB = aABB.move(vec3);
			if (!this.happyGhast.level().noCollision(this.happyGhast, aABB)) {
				return false;
			}
		}

		return true;
	}
}
