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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;

public class HappyGhastFreezeGoal extends Goal {
	private final HappyGhast happyGhast;

	public HappyGhastFreezeGoal(HappyGhast happyGhast) {
		this.happyGhast = happyGhast;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (this.happyGhast.isSaddled()) {
			AABB happyGhastBox = this.happyGhast.getBoundingBox();
			happyGhastBox = happyGhastBox.move(0D, happyGhastBox.getYsize(), 0D);
			return !this.happyGhast.level().getEntities(this.happyGhast, happyGhastBox).isEmpty();
		}
		return false;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		this.happyGhast.stopHappyGhastNavigation();
	}
}
