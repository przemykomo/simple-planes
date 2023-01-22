package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.container.ImplementedInventory;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

public class PlaneWorkbenchBlockEntity extends BlockEntity implements ImplementedInventory {

    public final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public final DataSlot selectedRecipe = DataSlot.standalone();

    public PlaneWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SimplePlanesBlocks.PLANE_WORKBENCH_TILE, blockPos, blockState);
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("input", ContainerHelper.saveAllItems(new CompoundTag(), items));
        compoundTag.putInt("selected_recipe", selectedRecipe.get());
    }

    @Override
    public void load(CompoundTag compoundTag) {
        ContainerHelper.loadAllItems(compoundTag.getCompound("input"), items);
        selectedRecipe.set(compoundTag.getInt("selected_recipe"));
        super.load(compoundTag);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
