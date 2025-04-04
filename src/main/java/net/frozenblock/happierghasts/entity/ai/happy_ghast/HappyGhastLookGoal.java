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
