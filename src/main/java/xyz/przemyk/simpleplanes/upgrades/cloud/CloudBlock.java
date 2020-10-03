package xyz.przemyk.simpleplanes.upgrades.cloud;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import java.util.Random;

public class CloudBlock extends Block {

    public static final IntProperty AGE = Properties.AGE_3;
    protected static final VoxelShape ZERO_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    protected static final VoxelShape ONE_SHAPE = Block.createCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected static final VoxelShape TWO_SHAPE = Block.createCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    protected static final VoxelShape THREE_SHAPE = Block.createCuboidShape(4.0D, 3.0D, 4.0D, 12.0D, 11.0D, 12.0D);

    public CloudBlock() {
        super(Settings.of(Material.SNOW_LAYER).sounds(BlockSoundGroup.SNOW).strength(1F, 1F).ticksRandomly().slipperiness(0.5f));
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        switch (state.get(AGE)) {
            case 1:
                return ONE_SHAPE;
            case 2:
                return TWO_SHAPE;
            case 3:
                return THREE_SHAPE;
            case 0:
            default:
                return ZERO_SHAPE;
        }
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.scheduledTick(state, worldIn, pos, random);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn.getVelocity().y<0) {
            entityIn.slowMovement(state, new Vec3d(0.8, 0.3, 0.8));
        }

    }

    public boolean canReplace(BlockState state, ItemPlacementContext useContext) {
        return true;
    }

    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isClient) {
            return;
        }
        if (this.slightlyMelt(worldIn, pos)) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (Direction direction : Direction.values()) {
                blockpos$mutable.set(pos, direction);
                BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
                if (blockstate.isOf(this)) {
                    this.slightlyMelt(worldIn, blockpos$mutable);
                }
            }
        }
        worldIn.getBlockTickScheduler().schedule(pos, this, getScheduledTime(rand,state.get(AGE)));
    }

    private boolean slightlyMelt(World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        int i = state.get(AGE);
        if (i < 3) {
            worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
            return false;
        } else {
            this.turnIntoAir(state, worldIn, pos);
            return true;
        }
    }

    private boolean shouldMelt(BlockView worldIn, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            blockpos$mutable.set(pos, direction);
            if (worldIn.getBlockState(blockpos$mutable).isOf(this)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isLadder(BlockState state, WorldView world, BlockPos pos, LivingEntity entity) {
        return !entity.isSneaking();
    }

    public static void placeCloud(BlockPos blockPos, World world) {
        if (world.isClient) {
            return;
        }

        BlockPos.Mutable pos = blockPos.mutableCopy();
        pos.move(Direction.DOWN);

        int f = 2;
        for (BlockPos pos1 : BlockPos.iterate(pos.add(-f, -1, -f), pos.add(f, 0D, f))) {
            if (world.random.nextInt(2) == 0 &&
                world.getBlockState(pos1).isAir()) {
                world.setBlockState(pos1, SimplePlanesBlocks.CLOUD.getDefaultState());
                world.getBlockTickScheduler().schedule(pos1, SimplePlanesBlocks.CLOUD, getScheduledTime(world.random,0));
            }
        }
    }

    private static int getScheduledTime(Random rand,int i) {
        return MathHelper.nextInt(rand, 50, 100)*(5-i);
    }

    private void turnIntoAir(BlockState state, World world, BlockPos pos) {
        world.removeBlock(pos, false);
    }

    public void neighborUpdate(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
//        if (blockIn == this) {
//            worldIn.getPendingBlockTicks().scheduleTick(pos, this, MathHelper.nextInt(worldIn.rand, 10, 20));
//        }

        super.neighborUpdate(state, worldIn, pos, blockIn, fromPos, isMoving);
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void onSteppedOn(World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClient && !entityIn.isSneaking() && worldIn.random.nextInt(20) == 0) {
            slightlyMelt(worldIn, pos);
        }
    }
    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onLandedUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.handleFallDamage(fallDistance, 0.1F);
        if(worldIn.random.nextInt(4) == 0){
            slightlyMelt(worldIn, pos);
        }
    }

    public ItemStack getPickStack(BlockView worldIn, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        final VoxelShape shape = getOutlineShape(state, worldIn, pos, context);
        if (context.isAbove(shape, pos, true) && !context.isDescending()) {
            return shape;
        } else {
            return VoxelShapes.empty();
        }
    }

}
