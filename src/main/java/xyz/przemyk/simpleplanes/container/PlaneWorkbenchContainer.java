package xyz.przemyk.simpleplanes.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class PlaneWorkbenchContainer extends Container {

    public static final ResourceLocation PLANE_MATERIALS = new ResourceLocation(SimplePlanesMod.MODID, "plane_materials");
    public static final int IRON_NEEDED = 4;
    public static final int PLANKS_NEEDED = 4;
    public static final Item[] OUTPUT_ITEMS = new Item[]{SimplePlanesItems.PLANE_ITEM.get(), SimplePlanesItems.LARGE_PLANE_ITEM.get(), SimplePlanesItems.HELICOPTER_ITEM.get()};

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    private int selectedOutputItem = 0;
    private final CompoundNBT outputItemTag = new CompoundNBT();

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(SimplePlanesContainers.PLANE_WORKBENCH.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;

        addSlot(new SlotItemHandler(itemHandler, 0, 28, 47));
        addSlot(new SlotItemHandler(itemHandler, 1, 75, 47));
        addSlot(new PlaneCraftingResultSlot(player, this, itemHandler, 2, 134, 47));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
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
        if (!player.world.isRemote && !itemHandler.getStackInSlot(2).isEmpty()) {
            ItemStack result = OUTPUT_ITEMS[selectedOutputItem].getDefaultInstance();
            result.setTagInfo("EntityTag", outputItemTag);
            itemHandler.setStackInSlot(2, result);
            ((ServerPlayerEntity) player).connection.sendPacket(new SSetSlotPacket(windowId, 2, result));
        }
    }

    public void onCrafting() {
        itemHandler.extractItem(0, IRON_NEEDED, false);
        itemHandler.extractItem(1, PLANKS_NEEDED, false);
        onInputChanged();
    }

    protected void updateCraftingResult(World world, PlayerEntity player) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack result = ItemStack.EMPTY;
            ItemStack input = itemHandler.getStackInSlot(0);
            ItemStack secondInput = itemHandler.getStackInSlot(1);
            Item secondItem = secondInput.getItem();

            if (input.getItem() == Items.IRON_INGOT && input.getCount() >= IRON_NEEDED &&
                secondInput.getCount() >= PLANKS_NEEDED && secondItem instanceof BlockItem &&
                BlockTags.getCollection().getTagByID(PLANE_MATERIALS).contains(((BlockItem) secondItem).getBlock())) {

                result = OUTPUT_ITEMS[selectedOutputItem].getDefaultInstance();
                Block block = ((BlockItem) secondItem).getBlock();
                outputItemTag.putString("material", block.getRegistryName().toString());
                result.setTagInfo("EntityTag", outputItemTag);
            }

            itemHandler.setStackInSlot(2, result);
            serverPlayerEntity.connection.sendPacket(new SSetSlotPacket(windowId, 2, result));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        worldPosCallable.consume((world, blockPos) -> {
            if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
                for (int i = 0; i < itemHandler.getSlots() - 1; ++i) {
                    playerIn.dropItem(itemHandler.getStackInSlot(i), false);
                    itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                }
            } else {
                for (int i = 0; i < itemHandler.getSlots() - 1; ++i) {
                    playerIn.inventory.placeItemBackInInventory(world, itemHandler.getStackInSlot(i));
                    itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        });
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(worldPosCallable, playerIn, SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get());
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack itemStack = super.slotClick(slotId, dragType, clickTypeIn, player);
        onInputChanged();
        return itemStack;
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
                if (!this.mergeItemStack(itemstack1, 10, 39, true)) {
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
