package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class PlaneWorkbenchContainer extends Container {
    private final ItemStackHandler craftMatrix = new ItemStackHandler(2);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    public static final int IRON_NEEDED = 4;
    public static final int PLANKS_NEEDED = 4;
    public static final Item[] OUTPUT_ITEMS = new Item[] {SimplePlanesItems.PLANE_ITEM.get(), SimplePlanesItems.LARGE_PLANE_ITEM.get(), SimplePlanesItems.MEGA_PLANE_ITEM.get(), SimplePlanesItems.HELICOPTER_ITEM.get()};

    private int selectedOutputItem = 0;
    private final CompoundNBT outputItemTag = new CompoundNBT();

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(SimplePlanesContainers.PLANE_WORKBENCH.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;

        addSlot(new PlaneCraftingResultSlot(player, this, craftResult, 0, 122, 104));

        addSlot(new PlaneWorkbenchInputSlot(craftMatrix, this, 0, 33, 104));
        addSlot(new PlaneWorkbenchInputSlot(craftMatrix, this, 1, 51, 104));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 128 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            addSlot(new Slot(playerInventory, l, 8 + l * 18, 186));
        }
    }

    public void onInputChanged() {
        worldPosCallable.consume(((world, blockPos) -> updateCraftingResult(world, player)));
    }

    public void cycleItems(CycleItemsPacket.TYPE type) {
        switch (type) {
            case CRAFTING_LEFT:
                if (selectedOutputItem == 0) {
                    selectedOutputItem = OUTPUT_ITEMS.length - 1;
                } else {
                    --selectedOutputItem;
                }
                break;
            case CRAFTING_RIGHT:
                if (selectedOutputItem == OUTPUT_ITEMS.length - 1) {
                    selectedOutputItem = 0;
                } else {
                    ++selectedOutputItem;
                }
        }

        updateResultSlot();
    }

    private void updateResultSlot() {
        if (!player.world.isRemote && !craftResult.getStackInSlot(0).isEmpty()) {
            ItemStack result = OUTPUT_ITEMS[selectedOutputItem].getDefaultInstance();
            result.setTagInfo("EntityTag", outputItemTag);
            craftResult.setInventorySlotContents(0, result);
            ((ServerPlayerEntity) player).connection.sendPacket(new SSetSlotPacket(windowId, 0, result));
        }
    }

    public void onCrafting() {
        craftMatrix.extractItem(0, IRON_NEEDED, false);
        craftMatrix.extractItem(1, PLANKS_NEEDED, false);
        onInputChanged();
    }

    protected void updateCraftingResult(World world, PlayerEntity player) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack result = ItemStack.EMPTY;
            ItemStack input = craftMatrix.getStackInSlot(0);
            ItemStack secondInput = craftMatrix.getStackInSlot(1);
            if (input.getItem() == Items.IRON_INGOT && input.getCount() >= IRON_NEEDED && secondInput.getItem() instanceof BlockItem && secondInput.getCount() >= PLANKS_NEEDED) {
                result = OUTPUT_ITEMS[selectedOutputItem].getDefaultInstance();
                outputItemTag.putString("material", ((BlockItem) secondInput.getItem()).getBlock().getRegistryName().toString());
                result.setTagInfo("EntityTag", outputItemTag);
            }

            craftResult.setInventorySlotContents(0, result);
            serverPlayerEntity.connection.sendPacket(new SSetSlotPacket(windowId, 0, result));
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
}
