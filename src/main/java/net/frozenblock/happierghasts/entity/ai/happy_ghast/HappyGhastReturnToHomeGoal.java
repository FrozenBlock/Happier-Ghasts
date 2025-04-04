package net.frozenblock.happierghasts.entity.ai.happy_ghast;

import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class HappyGhastReturnToHomeGoal extends Goal {
	private final HappyGhast happyGhast;

	public HappyGhastReturnToHomeGoal(HappyGhast happyGhast) {
		this.happyGhast = happyGhast;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (!this.happyGhast.hasControllingPassenger() && this.happyGhast.isInHomePositionDimension()) {
			return !this.happyGhast.blockPosition().closerThan(this.happyGhast.getHomeBlockPosition().get(), this.getHomeDistance());
		}
		return false;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	public double getHomeDistance() {
		return this.happyGhast.isSaddled() ? 32D : 64D;
	}

	@Override
	public void start() {
		if (!this.happyGhast.hasControllingPassenger() && this.happyGhast.isInHomePositionDimension()) {
			BlockPos homePosition = this.happyGhast.getHomeBlockPosition().get();
			RandomSource randomSource = this.happyGhast.getRandom();
			double d = homePosition.getX() + (randomSource.nextFloat() * 2F - 1F) * this.getHomeDistance();
			double e = homePosition.getY() + (randomSource.nextFloat() * 2F - 1F) * this.getHomeDistance();
			double f = homePosition.getZ() + (randomSource.nextFloat() * 2F - 1F) * this.getHomeDistance();
			this.happyGhast.getMoveControl().setWantedPosition(d, e, f, 1D);
		}
	}
}
