package net.frozenblock.happierghasts.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.frozenblock.happierghasts.registry.HGEntityTypes;
import net.frozenblock.happierghasts.registry.HGSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DriedGhastBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	private static final int HYDRATION_DELAY = 5 * 20 * 60;
	public static final MapCodec<DriedGhastBlock> CODEC = simpleCodec(DriedGhastBlock::new);
	private static final VoxelShape SHAPE = Block.box(3D, 0D, 3D, 13D, 10D, 13D);
	public static final IntegerProperty REHYDRATION_LEVEL = IntegerProperty.create("rehydration_level", 0, 3);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DriedGhastBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(REHYDRATION_LEVEL, 0));
	}

	@Override
	protected @NotNull MapCodec<? extends DriedGhastBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess tickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos blockPos2,
		BlockState blockState2,
		RandomSource randomSource
	) {
		if (blockState.getValue(WATERLOGGED)) tickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		tickAccess.scheduleTick(blockPos, this, HYDRATION_DELAY);
		return super.updateShape(blockState, levelReader, tickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
	}

	@Override
	protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		super.onPlace(blockState, level, blockPos, blockState2, bl);
		if (blockState.getValue(WATERLOGGED)) level.levelEvent(LevelEvent.PARTICLES_EGG_CRACK, blockPos, 0);
		level.scheduleTick(blockPos, this, HYDRATION_DELAY);
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (!this.canSurvive(blockState, serverLevel, blockPos)) {
			serverLevel.destroyBlock(blockPos, true);
			return;
		}

		if (blockState.getValue(WATERLOGGED)) {
			if (!isAtMaxStage(blockState)) {
				serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(REHYDRATION_LEVEL, blockState.getValue(REHYDRATION_LEVEL) + 1));
				serverLevel.scheduleTick(blockPos, this, HYDRATION_DELAY);
				serverLevel.levelEvent(LevelEvent.PARTICLES_EGG_CRACK, blockPos, 0);
			} else {
				serverLevel.playSound(null, blockPos, HGSounds.GHASTLING_SPAWN, SoundSource.BLOCKS, 1F, 0.9F + randomSource.nextFloat() * 0.2F);
				serverLevel.destroyBlock(blockPos, false);
				HappyGhast ghastling = HGEntityTypes.HAPPY_GHAST.create(serverLevel, EntitySpawnReason.BREEDING);
				if (ghastling != null) {
					Vec3 vec3 = blockPos.getCenter();
					ghastling.setBaby(true);
					ghastling.snapTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(blockState.getValue(FACING).toYRot() * 360F), 0F);
					ghastling.updateHomePosition();
					serverLevel.addFreshEntity(ghastling);
				}
			}
		} else {
			if (!isAtMinStage(blockState)) {
				serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(REHYDRATION_LEVEL, blockState.getValue(REHYDRATION_LEVEL) - 1));
				serverLevel.scheduleTick(blockPos, this, HYDRATION_DELAY);
			}
		}
	}

	@Override
	protected void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (blockState.getValue(WATERLOGGED)) serverLevel.levelEvent(LevelEvent.PARTICLES_EGG_CRACK, blockPos, 0);
		serverLevel.playSound(null, blockPos, HGSounds.DRIED_GHAST_AMBIENT, SoundSource.BLOCKS, 0.5F, 0.9F + randomSource.nextFloat() * 0.2F);
	}

	public static boolean isAtMaxStage(@NotNull BlockState state) {
		return state.getValue(REHYDRATION_LEVEL) >= 3;
	}

	public static boolean isAtMinStage(@NotNull BlockState state) {
		return state.getValue(REHYDRATION_LEVEL) <= 0;
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		return this.defaultBlockState()
			.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
			.setValue(WATERLOGGED, blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	protected @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING, REHYDRATION_LEVEL, WATERLOGGED);
	}
}
