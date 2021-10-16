package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

import javax.annotation.Nullable;

public class PlaneWorkbenchBlock extends Block implements EntityBlock {

    public static final Component CONTAINER_NAME = new TranslatableComponent(SimplePlanesMod.MODID + ".container.plane_workbench");

    public PlaneWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            PlaneWorkbenchTile planeWorkbenchTile = (PlaneWorkbenchTile) level.getBlockEntity(blockPos);
            if (planeWorkbenchTile != null) {
                NetworkHooks.openGui((ServerPlayer) playerIn, new SimpleMenuProvider((id, inventory, player) ->
                        new PlaneWorkbenchContainer(id, inventory, blockPos, planeWorkbenchTile.itemStackHandler, planeWorkbenchTile.selectedRecipe), CONTAINER_NAME));
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PlaneWorkbenchTile(blockPos, blockState);
    }
}
