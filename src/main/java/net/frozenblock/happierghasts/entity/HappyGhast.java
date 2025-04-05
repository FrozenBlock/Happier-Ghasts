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

package net.frozenblock.happierghasts.entity;

import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastFollowMobGoal;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastFreezeGoal;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastLookGoal;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastMoveControl;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastRandomFloatAroundGoal;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastReturnToHomeGoal;
import net.frozenblock.happierghasts.entity.ai.happy_ghast.HappyGhastTemptGoal;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.frozenblock.happierghasts.registry.HGSounds;
import net.frozenblock.happierghasts.tag.HGEntityTypeTags;
import net.frozenblock.happierghasts.tag.HGItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;

public class HappyGhast extends Animal implements FlyingAnimal, OwnableEntity {
	private Optional<GlobalPos> homePosition = Optional.empty();
	@Nullable
	private EntityReference<LivingEntity> owner;

	public HappyGhast(EntityType<? extends HappyGhast> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new HappyGhastMoveControl(this);
	}

	public void updateHomePosition() {
		this.homePosition = Optional.of(new GlobalPos(this.level().dimension(), this.blockPosition()));
	}

	@Override
	protected void registerGoals() {
		this.clearAndSetGoalsForAge();
	}

	public void clearAndSetGoalsForAge() {
		this.goalSelector.removeAllGoals(goal -> {
			goal.stop();
			return true;
		});

		this.goalSelector.addGoal(0, new FloatGoal(this));

		if (this.isBaby()) {
			this.goalSelector.addGoal(1, new HappyGhastTemptGoal(this, 1.25D, itemStack -> itemStack.is(Items.SNOWBALL), happyGhast -> true));
			this.goalSelector.addGoal(2, new HappyGhastReturnToHomeGoal(this));
			this.goalSelector.addGoal(3, new HappyGhastFollowMobGoal(this, 1D, entity -> entity.getType().is(HGEntityTypeTags.GHASTLING_FOLLOWS)));
			this.goalSelector.addGoal(4, new HappyGhastRandomFloatAroundGoal(this));
			this.goalSelector.addGoal(5, new HappyGhastLookGoal(this));
		} else {
			this.goalSelector.addGoal(1, new HappyGhastFreezeGoal(this));
			this.goalSelector.addGoal(2, new HappyGhastTemptGoal(this, 1.25D, itemStack -> itemStack.is(Items.SNOWBALL), happyGhast -> true));
			this.goalSelector.addGoal(2, new HappyGhastTemptGoal(
				this,
				1.25D,
				itemStack -> {
					Equippable equippable = itemStack.getComponents().get(DataComponents.EQUIPPABLE);
					if (equippable != null) return equippable.canBeEquippedBy(this.getType());
					return false;
				},
				happyGhast -> !happyGhast.isSaddled()
				)
			);
			this.goalSelector.addGoal(3, new HappyGhastReturnToHomeGoal(this));
			this.goalSelector.addGoal(5, new HappyGhastRandomFloatAroundGoal(this));
			this.goalSelector.addGoal(6, new HappyGhastLookGoal(this));
		}
	}

	@Override
	public void stopInPlace() {
		super.stopInPlace();
		this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0D);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
		return 0F;
	}

	@Override
	public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return HGEntityTypes.HAPPY_GHAST.create(serverLevel, EntitySpawnReason.BREEDING);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return createAnimalAttributes()
			.add(Attributes.MAX_HEALTH, 20D)
			.add(Attributes.FOLLOW_RANGE, 64D)
			.add(Attributes.TEMPT_RANGE, 64D);
	}

	@Nullable
	@Override
	public EntityReference<LivingEntity> getOwnerReference() {
		return this.owner;
	}

	public void setOwner(@Nullable LivingEntity livingEntity) {
		this.owner = livingEntity != null ? new EntityReference<>(livingEntity) : null;
	}

	@Override
	public boolean canBeCollidedWith() {
		return this.isSaddled();
	}

	@Override
	public void push(double d, double e, double f) {
		if (this.isBaby()) super.push(d, e, f);
	}

	@Override
	public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		this.updateHomePosition();
		if (this.isSaddled()) {
			if (!player.isSecondaryUseActive()) {
				if (this.getPassengers().size() < 4 && !this.isBaby()) {
					this.doPlayerRide(player);
					return InteractionResult.SUCCESS;
				}
			} else if (player.getItemInHand(interactionHand).getItem() == Items.SHEARS) {
				this.level().playSeededSound(
					null,
					this.getX(),
					this.getY(),
					this.getZ(),
					HGSounds.HAPPY_GHAST_HARNESS_UNEQUIP,
					this.getSoundSource(),
					1F,
					1F,
					this.random.nextLong()
				);
				this.drop(this.getItemBySlot(EquipmentSlot.SADDLE).copyAndClear(), true, false);
				return InteractionResult.SUCCESS;
			}
			return super.mobInteract(player, interactionHand);
		} else {
			InteractionResult interactionResult = super.mobInteract(player, interactionHand);
			if (!interactionResult.consumesAction()) {
				ItemStack itemStack = player.getItemInHand(interactionHand);
				return this.isEquippableInSlot(itemStack, EquipmentSlot.SADDLE)
					? itemStack.interactLivingEntity(player, this, interactionHand)
					: InteractionResult.PASS;
			} else {
				return interactionResult;
			}
		}
	}

	@Override
	public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Vec3 dismountLocation = super.getDismountLocationForPassenger(livingEntity);
		return dismountLocation.add(0D, 0.2D, 0D);
	}

	protected void doPlayerRide(Player player) {
		if (!this.level().isClientSide) {
			player.setYRot(this.getYRot());
			player.setXRot(this.getXRot());
			player.startRiding(this);
		}
	}

	@Override
	public boolean canUseSlot(EquipmentSlot equipmentSlot) {
		return equipmentSlot != EquipmentSlot.SADDLE ? super.canUseSlot(equipmentSlot) : this.isAlive() && !this.isBaby();
	}

	@Override
	protected boolean canDispenserEquipIntoSlot(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.SADDLE || super.canDispenserEquipIntoSlot(equipmentSlot);
	}

	@Override
	protected @NotNull Holder<SoundEvent> getEquipSound(EquipmentSlot equipmentSlot, ItemStack itemStack, Equippable equippable) {
		return equipmentSlot == EquipmentSlot.SADDLE ? HGSounds.HAPPY_GHAST_HARNESS_EQUIP : super.getEquipSound(equipmentSlot, itemStack, equippable);
	}

	@Override
	protected void tickRidden(Player player, Vec3 vec3) {
		super.tickRidden(player, vec3);
		this.setRot(player.getYRot(), player.getXRot() * 0.5F);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
	}

	@Override
	protected @NotNull Vec3 getRiddenInput(@NotNull Player player, Vec3 vec3) {
		float movement = player.xxa;
		float speed = player.zza;
		if (speed <= 0F) speed *= 0.25F;

		float horizontalMovement = 0F;
		if (player.jumping) {
			horizontalMovement = 1F;
		} else if (speed != 0F) {
			horizontalMovement = (float) player.getLookAngle().y();
			if (horizontalMovement > 0F) horizontalMovement *= 0.5F;
		}

		return new Vec3(movement, Math.clamp(horizontalMovement * 2F, -1F, 1F), speed);
	}

	@Override
	protected void addPassenger(Entity entity) {
		super.addPassenger(entity);
		if (this.getControllingPassenger() == entity) {
			this.level().playSeededSound(
				null,
				this.getX(),
				this.getY(),
				this.getZ(),
				HGSounds.HAPPY_GHAST_GOGGLES_DOWN,
				this.getSoundSource(),
				1F,
				1F,
				this.random.nextLong()
			);
		}
	}

	@Override
	public LivingEntity getControllingPassenger() {
		return this.isSaddled() && this.getFirstPassenger() instanceof Player player ? player : super.getControllingPassenger();
	}

	@Override
	protected void removePassenger(Entity entity) {
		super.removePassenger(entity);
		if (!this.hasControllingPassenger()) {
			this.stopInPlace();
			this.level().playSeededSound(
				null,
				this.getX(),
				this.getY(),
				this.getZ(),
				HGSounds.HAPPY_GHAST_GOGGLES_UP,
				this.getSoundSource(),
				1F,
				1F,
				this.random.nextLong()
			);
		}
	}

	public boolean hasHomePosition() {
		return this.homePosition.isPresent();
	}

	public boolean isInHomePositionDimension() {
		return this.hasHomePosition() && this.level().dimension() == this.homePosition.get().dimension();
	}

	public Optional<BlockPos> getHomeBlockPosition() {
		return this.homePosition.map(GlobalPos::pos);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.hasControllingPassenger()) this.updateHomePosition();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.level() instanceof ServerLevel && this.isAlive()) {
			if (this.deathTime == 0) {
				if (this.level().dimension() == Level.OVERWORLD) {
					if (this.getBoundingBox().contains(this.getX(), 195D, this.getZ()) || this.isRainingOrSnowingAt()) {
						if (this.getActiveEffectsMap().get(MobEffects.REGENERATION) == null) {
							this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
						}
					}
				}
				if (this.random.nextInt(900) == 0) {
					this.heal(1F);
				}
			}
		}
	}

	public boolean isRainingOrSnowingAt() {
		Level level = this.level();
		BlockPos blockPos = this.blockPosition();
		if (!level.isRaining()) {
			return false;
		} else if (!level.canSeeSky(blockPos)) {
			return false;
		} else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > blockPos.getY()) {
			return false;
		} else {
			Biome biome = level.getBiome(blockPos).value();
			return biome.getPrecipitationAt(blockPos, level.getSeaLevel()) != Biome.Precipitation.NONE;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isBaby() ? HGSounds.GHASTLING_AMBIENT : HGSounds.HAPPY_GHAST_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return this.isBaby() ? HGSounds.GHASTLING_HURT : HGSounds.HAPPY_GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isBaby() ? HGSounds.GHASTLING_DEATH : HGSounds.HAPPY_GHAST_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return this.isBaby() ? 3F : 5F;
	}

	@Override
	public int getAmbientSoundInterval() {
		return super.getAmbientSoundInterval() * 2;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		this.homePosition.ifPresent(globalPos -> compoundTag.store("HomePosition", GlobalPos.CODEC, globalPos));
		if (this.owner != null) {
			this.owner.store(compoundTag, "Owner");
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.homePosition = compoundTag.read("HomePosition", GlobalPos.CODEC);
		this.owner = EntityReference.readWithOldOwnerConversion(compoundTag, "Owner", this.level());
	}

	@Override
	public boolean isFood(@NotNull ItemStack itemStack) {
		return itemStack.is(HGItemTags.HAPPY_GHAST_FOOD);
	}

	@Override
	protected void playEatingSound() {
		// TODO
	}

	@Override
	public boolean canFallInLove() {
		return false;
	}

	@Override
	protected void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
	}

	@Override
	public float getAgeScale() {
		return this.isBaby() ? 0.3F : 1F;
	}

	@Override
	public void setAge(int i) {
		boolean wasBaby = this.isBaby();
		super.setAge(i);
		if (wasBaby != this.isBaby()) {
			this.clearAndSetGoalsForAge();
		}
	}

	@Override
	public float getVoicePitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1F;
	}

	@Override
	public void travel(Vec3 vec3) {
		if (this.isInWater()) {
			this.moveRelative(0.02F, vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
		} else if (this.isInLava()) {
			this.moveRelative(0.02F, vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
		} else {
			float f = 0.91F;
			if (this.onGround()) {
				f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
			}

			float g = 0.16277137F / (f * f * f);
			f = 0.91F;
			if (this.onGround()) {
				f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
			}

			this.moveRelative(this.onGround() ? 0.1F * g : 0.02F, vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(f));
		}
	}

	@Override
	public boolean onClimbable() {
		return false;
	}

	@Override
	public boolean isFlying() {
		return true;
	}
}
