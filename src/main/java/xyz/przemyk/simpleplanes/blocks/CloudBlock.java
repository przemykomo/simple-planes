package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import java.util.Random;

public class CloudBlock extends Block {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    protected static final VoxelShape ZERO_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    protected static final VoxelShape ONE_SHAPE = Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected static final VoxelShape TWO_SHAPE = Block.makeCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    protected static final VoxelShape THREE_SHAPE = Block.makeCuboidShape(4.0D, 3.0D, 4.0D, 12.0D, 11.0D, 12.0D);

    public CloudBlock() {
        super(Properties.create(Material.SNOW).sound(SoundType.SNOW).hardnessAndResistance(1F, 1F).tickRandomly().slipperiness(0.5f));
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
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
        this.tick(state, worldIn, pos, random);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn.getMotion().y<0) {
            entityIn.setMotionMultiplier(state, new Vector3d(0.8, 0.3, 0.8));
        }

    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRemote) {
            return;
        }
        if (this.slightlyMelt(worldIn, pos)) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (Direction direction : Direction.values()) {
                blockpos$mutable.setAndMove(pos, direction);
                BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
                if (blockstate.isIn(this)) {
                    this.slightlyMelt(worldIn, blockpos$mutable);
                }
            }
        }
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getScheduledTime(rand,state.get(AGE)));
    }

    private boolean slightlyMelt(World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        int i = state.get(AGE);
        if (i < 3) {
            worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
            return false;
        } else {
            this.turnIntoAir(state, worldIn, pos);
            return true;
        }
    }

    private boolean shouldMelt(IBlockReader worldIn, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            blockpos$mutable.setAndMove(pos, direction);
            if (worldIn.getBlockState(blockpos$mutable).isIn(this)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return !entity.isSneaking();
    }

    public static void placeCloud(BlockPos blockPos, World world) {
        if (world.isRemote) {
            return;
        }

        BlockPos.Mutable pos = blockPos.toMutable();
        pos.move(Direction.DOWN);

        int f = 2;
        for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(-f, -1, -f), pos.add(f, 0D, f))) {
            if (world.rand.nextInt(2) == 0 &&
                world.getBlockState(pos1).isAir(world, pos1)) {
                world.setBlockState(pos1, SimplePlanesBlocks.CLOUD.get().getDefaultState());
                world.getPendingBlockTicks().scheduleTick(pos1, SimplePlanesBlocks.CLOUD.get(), getScheduledTime(world.rand,0));
            }
        }
    }

    private static int getScheduledTime(Random rand,int i) {
        return MathHelper.nextInt(rand, 50, 100)*(5-i);
    }

    private void turnIntoAir(BlockState state, World world, BlockPos pos) {
        world.removeBlock(pos, false);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
//        if (blockIn == this) {
//            worldIn.getPendingBlockTicks().scheduleTick(pos, this, MathHelper.nextInt(worldIn.rand, 10, 20));
//        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && !entityIn.isSneaking() && worldIn.rand.nextInt(20) == 0) {
            slightlyMelt(worldIn, pos);
        }
    }
    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 0.1F);
        if(worldIn.rand.nextInt(4) == 0){
            slightlyMelt(worldIn, pos);
        }
    }

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        final VoxelShape shape = getShape(state, worldIn, pos, context);
        if (context.func_216378_a(shape, pos, true) && !context.getPosY()) {
            return shape;
        } else {
            return VoxelShapes.empty();
        }
    }

}
