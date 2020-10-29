package xyz.przemyk.simpleplanes.integration.energy;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import xyz.przemyk.simpleplanes.integration.ChargingBlockBase;

import javax.annotation.Nullable;
import java.util.Random;

public class ChargerBlock extends ChargingBlockBase {


    public ChargerBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5F, 30F));
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChargerTileEntity();
    }

//    @Override
//    public boolean ticksRandomly(BlockState state) {
//        if(state.get(CHARGES) > 0){
//            return true;
//        }
//        return super.ticksRandomly(state);
//    }
//
//    @Override
//    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
//        Integer val = state.get(CHARGES);
//        if(val > 0){
//            worldIn.setBlockState(pos,state.with(CHARGES,val-1));
//            if(val > 1){
//                worldIn.getPendingBlockTicks().scheduleTick(pos, this, 10);
//            }
//        }
//        super.tick(state, worldIn, pos, rand);
//    }
}
