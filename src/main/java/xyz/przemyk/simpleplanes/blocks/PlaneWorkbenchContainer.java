package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class PlaneWorkbenchContainer extends Container {
    private final ItemStackHandler craftMatrix = new ItemStackHandler(2);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    public static final int IRON_NEEDED = 4;
    public static final int PLANKS_NEEDED = 4;

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(SimplePlanesBlocks.PLANE_WORKBENCH_CONTAINER.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;

        addSlot(new PlaneCraftingResultSlot(player, this, craftResult, 0, 118, 35));

        addSlot(new PlaneWorkbenchInputSlot(craftMatrix, this, 0, 38, 35));
        addSlot(new PlaneWorkbenchInputSlot(craftMatrix, this, 1, 56, 35));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    public void onInputChanged() {
        worldPosCallable.consume(((world, blockPos) -> updateCraftingResult(windowId, world, player, craftMatrix, craftResult)));
    }

    protected static void updateCraftingResult(int id, World world, PlayerEntity player, ItemStackHandler craftMatrix, CraftResultInventory inventoryResult) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack result = ItemStack.EMPTY;
            ItemStack input = craftMatrix.getStackInSlot(0);
            ItemStack secondInput = craftMatrix.getStackInSlot(1);
            if (input.getItem() == Items.IRON_INGOT && input.getCount() >= IRON_NEEDED && secondInput.getItem() instanceof BlockItem && secondInput.getCount() >= PLANKS_NEEDED) {
                result = new ItemStack(SimplePlanesItems.PLANE_ITEM.get());
                CompoundNBT entityTag = new CompoundNBT();
                entityTag.putString("material", ((BlockItem) secondInput.getItem()).getBlock().getRegistryName().toString());
                result.setTagInfo("EntityTag", entityTag);
            }

            inventoryResult.setInventorySlotContents(0, result);
            serverPlayerEntity.connection.sendPacket(new SSetSlotPacket(id, 0, result));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        worldPosCallable.consume((world, blockPos) -> {
            if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerIn).hasDisconnected()) {
                for (int i = 0; i < craftMatrix.getSlots(); ++i) {
                    playerIn.dropItem(craftMatrix.getStackInSlot(i), false);
                    craftMatrix.setStackInSlot(i, ItemStack.EMPTY);
                }
            } else {
                for (int i = 0; i < craftMatrix.getSlots(); ++i) {
                    playerIn.inventory.placeItemBackInInventory(world, craftMatrix.getStackInSlot(i));
                    craftMatrix.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        });
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(worldPosCallable, playerIn, SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.worldPosCallable.consume((p_217067_2_, p_217067_3_) -> itemstack1.getItem().onCreated(itemstack1, p_217067_2_, playerIn));
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 39) {
                if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.mergeItemStack(itemstack1, 37, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public void onCrafting() {
        craftMatrix.extractItem(0, IRON_NEEDED, false);
        craftMatrix.extractItem(1, PLANKS_NEEDED, false);
    }
}
