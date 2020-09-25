package xyz.przemyk.simpleplanes.integration.liquid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class LiquidFuelingBlock extends Block {
    public LiquidFuelingBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5F, 30F));
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
