package xyz.przemyk.simpleplanes.integration.liquid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LiquidFuelingBlock extends Block {
    public LiquidFuelingBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5F, 30F));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }

        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof LiquidFuelingTileEntity) {
            if (((LiquidFuelingTileEntity) tileEntity).onActivated(player)) {
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;

    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LiquidFuelingTileEntity();
    }

}
