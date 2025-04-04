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
