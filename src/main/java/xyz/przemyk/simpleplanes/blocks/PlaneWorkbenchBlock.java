package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

public class PlaneWorkbenchBlock extends Block {

    public static final ITextComponent CONTAINER_NAME = new TranslationTextComponent(SimplePlanesMod.MODID + ".container.plane_workbench");

    public PlaneWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider((id, inventory, player) -> new PlaneWorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos)), CONTAINER_NAME));
            return ActionResultType.CONSUME;
        }
    }
}
