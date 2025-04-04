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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class HappyGhastFollowMobGoal extends Goal {
	private static final TargetingConditions FOLLOW_TARGETING_PLAYER = TargetingConditions.forNonCombat().ignoreLineOfSight();
	private static final TargetingConditions FOLLOW_TARGETING = TargetingConditions.forNonCombat();
	private static final int MAX_FOLLOW_TICKS = 400;

	private final TargetingConditions playerTargetingConditions;
	private final TargetingConditions targetingConditions;
	private final HappyGhast happyGhast;
	private final double yOffset;

	public double ex;
	public double ey;
	public double ez;
	@Nullable
	protected Entity target;
	private int followTicks;
	private int followCooldown;
	private boolean isRunning;

	public HappyGhastFollowMobGoal(HappyGhast happyGhast, double yOffset, Predicate<Entity> canFollow) {
		this.happyGhast = happyGhast;
		this.yOffset = yOffset;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		this.playerTargetingConditions = FOLLOW_TARGETING_PLAYER.copy().selector((livingEntity, serverLevel) -> canFollow.test(livingEntity) && this.target != livingEntity);
		this.targetingConditions = FOLLOW_TARGETING.copy().selector((livingEntity, serverLevel) -> canFollow.test(livingEntity) && this.target != livingEntity);
	}

	@Override
	public boolean canUse() {
		if (this.followTicks >= MAX_FOLLOW_TICKS) return false;
		if (this.followCooldown > 0) {
			this.followCooldown--;
			return false;
		} else {
			if (this.isRunning && this.target != null && this.target.level() == this.happyGhast.level()) return true;
			LivingEntity owner = this.happyGhast.getOwner();
			if (owner != null && owner.level() == this.happyGhast.level() && this.target != owner) {
				if (owner.distanceTo(this.happyGhast) <= this.getSearchRange(true)) {
					this.target = owner;
					return true;
				}
			}

			Player player = getServerLevel(this.happyGhast).getNearestPlayer(this.playerTargetingConditions.range(this.getSearchRange(true)), this.happyGhast);
			if (player != null && (player.distanceTo(this.happyGhast) > 16D || this.happyGhast.getRandom().nextFloat() <= 0.25F) && this.target != player) {
				this.target = player;
				return true;
			}

			int searchRange = this.getSearchRange(false);
			LivingEntity livingEntity = getServerLevel(this.happyGhast).getNearestEntity(
				LivingEntity.class,
				this.targetingConditions.range(searchRange),
				this.happyGhast,
				this.happyGhast.getX(),
				this.happyGhast.getY(),
				this.happyGhast.getZ(),
				this.happyGhast.getBoundingBox().inflate(searchRange)
			);
			if (livingEntity != null) {
				this.target = livingEntity;
				return true;
			}
		}
		return false;
	}

	public int getSearchRange(boolean searchingForPlayer) {
		if (this.happyGhast.isBaby()) return searchingForPlayer ? 32 : 16;
		return searchingForPlayer ? 64 : 32;
	}

	@Override
	public void start() {
		this.ex = this.target.getX();
		this.ey = this.target.getY();
		this.ez = this.target.getZ();
		this.isRunning = true;
	}

	@Override
	public void stop() {
		this.happyGhast.getNavigation().stop();
		this.followCooldown = reducedTickDelay(100);
		this.followTicks = 0;
		this.isRunning = false;
	}

	@Override
	public void tick() {
		this.followTicks += 1;
		double xDistance = this.target.getX() - this.happyGhast.getX();
		double zDistance = this.target.getZ() - this.happyGhast.getZ();
		this.happyGhast.setYRot(-((float) Mth.atan2(xDistance, zDistance)) * Mth.RAD_TO_DEG);
		this.happyGhast.yBodyRot = this.happyGhast.getYRot();

		if (this.happyGhast.distanceToSqr(this.target) <= Mth.square(this.happyGhast.getBoundingBox().getSize() * 2D)) {
			this.happyGhast.stopInPlace();
		} else {
			this.happyGhast.getMoveControl().setWantedPosition(this.target.getX(), this.target.getY() + this.yOffset, this.target.getZ(), 1D);
		}
	}
}
