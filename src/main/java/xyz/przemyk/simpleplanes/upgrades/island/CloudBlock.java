package xyz.przemyk.simpleplanes.upgrades.island;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeBlockState;

import java.util.Random;

public class CloudBlock extends Block {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;

    public CloudBlock() {
        super(Properties.create(Material.AIR).sound(SoundType.SNOW).hardnessAndResistance(1F, 1F));
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if ((rand.nextInt(3) == 0 || this.shouldMelt(worldIn, pos, 4)) && worldIn.getLight(pos) > 11 - state.get(AGE) - state.getOpacity(worldIn, pos) && this.slightlyMelt(state, worldIn, pos)) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for(Direction direction : Direction.values()) {
                blockpos$mutable.func_239622_a_(pos, direction);
                BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
                if (blockstate.isIn(this) && !this.slightlyMelt(blockstate, worldIn, blockpos$mutable)) {
                    worldIn.getPendingBlockTicks().scheduleTick(blockpos$mutable, this, MathHelper.nextInt(rand, 20, 40));
                }
            }

        } else {
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, MathHelper.nextInt(rand, 20, 40));
        }
    }

    private boolean slightlyMelt(BlockState state, World worldIn, BlockPos pos) {
        int i = state.get(AGE);
        if (i < 3) {
            worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
            return false;
        } else {
            this.turnIntoAir(state, worldIn, pos);
            return true;
        }
    }

    private void turnIntoAir(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (blockIn == this && this.shouldMelt(worldIn, pos, 2)) {
            this.turnIntoAir(state, worldIn, pos);
        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    private boolean shouldMelt(IBlockReader worldIn, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.func_239622_a_(pos, direction);
            if (worldIn.getBlockState(blockpos$mutable).isIn(this)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }

        return true;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
