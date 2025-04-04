package net.frozenblock.happierghasts.entity.ai.happy_ghast;

import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.function.Predicate;

public class HappyGhastTemptGoal extends TemptGoal {
	private final HappyGhast happyGhast;
	private final Predicate<HappyGhast> shouldFollow;

	public HappyGhastTemptGoal(HappyGhast happyGhast, double d, Predicate<ItemStack> items, Predicate<HappyGhast> shouldFollow) {
		super(happyGhast, d, items, false);
		this.happyGhast = happyGhast;
		this.shouldFollow = shouldFollow;
	}

	@Override
	public boolean canUse() {
		if (!this.shouldFollow.test(this.happyGhast)) return false;
		return super.canUse();
	}

	@Override
	public void tick() {
		double xDistance = this.player.getX() - this.happyGhast.getX();
		double zDistance = this.player.getZ() - this.happyGhast.getZ();
		this.happyGhast.setYRot(-((float) Mth.atan2(xDistance, zDistance)) * Mth.RAD_TO_DEG);
		this.happyGhast.yBodyRot = this.happyGhast.getYRot();

		if (this.mob.distanceToSqr(this.player) <= 49D) {
			this.happyGhast.stopHappyGhastNavigation();
		} else {
			this.happyGhast.getMoveControl().setWantedPosition(this.player.getX(), this.player.getY() + 2D, this.player.getZ(), this.speedModifier);
		}
	}
}
