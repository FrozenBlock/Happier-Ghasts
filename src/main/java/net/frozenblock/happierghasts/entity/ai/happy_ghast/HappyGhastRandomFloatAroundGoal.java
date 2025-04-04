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
