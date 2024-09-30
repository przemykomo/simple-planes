package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

public class PlaneWorkbenchBlockEntity extends BlockEntity {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(2);
    public final DataSlot selectedRecipe = DataSlot.standalone();

    public PlaneWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SimplePlanesBlocks.PLANE_WORKBENCH_TILE.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("input", itemStackHandler.serializeNBT(pRegistries));
        pTag.putInt("selected_recipe", selectedRecipe.get());
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemStackHandler.deserializeNBT(pRegistries, pTag.getCompound("input"));
        selectedRecipe.set(pTag.getInt("selected_recipe"));
    }
}
