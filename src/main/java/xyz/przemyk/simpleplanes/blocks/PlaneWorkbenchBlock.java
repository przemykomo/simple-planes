package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

import javax.annotation.Nullable;

public class PlaneWorkbenchBlock extends Block implements EntityBlock {

    public static final Component CONTAINER_NAME = Component.translatable(SimplePlanesMod.MODID + ".container.plane_workbench");

    public PlaneWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            PlaneWorkbenchBlockEntity planeWorkbenchBlockEntity = (PlaneWorkbenchBlockEntity) pLevel.getBlockEntity(pPos);
            if (planeWorkbenchBlockEntity != null) {
                SimpleMenuProvider menu = new SimpleMenuProvider((id, inventory, player) ->
                    new PlaneWorkbenchContainer(id, inventory, pPos, planeWorkbenchBlockEntity.itemStackHandler, planeWorkbenchBlockEntity.selectedRecipe), CONTAINER_NAME);
                pPlayer.openMenu(menu);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PlaneWorkbenchBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            PlaneWorkbenchBlockEntity blockEntity = (PlaneWorkbenchBlockEntity) level.getBlockEntity(blockPos);
            if (blockEntity != null) {
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockEntity.itemStackHandler.getStackInSlot(0));
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockEntity.itemStackHandler.getStackInSlot(1));
            }
        }
        super.onRemove(state, level, blockPos, newState, isMoving);
    }
}
