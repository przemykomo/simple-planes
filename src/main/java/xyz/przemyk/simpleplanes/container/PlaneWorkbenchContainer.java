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
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

import java.util.List;

public class PlaneWorkbenchContainer extends Container {

    public static final ResourceLocation PLANE_MATERIALS = new ResourceLocation(SimplePlanesMod.MODID, "plane_materials");

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    private int selectedRecipe = 0;
    private final CompoundNBT resultItemTag = new CompoundNBT();
    private List<PlaneWorkbenchRecipe> recipeList;

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public PlaneWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(SimplePlanesContainers.PLANE_WORKBENCH.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;

        worldPosCallable.execute((world, blockPos) -> recipeList = world.getRecipeManager().getAllRecipesFor(SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE));

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
        worldPosCallable.execute(((world, blockPos) -> updateCraftingResult(world, player)));
    }

    public void cycleItems(CycleItemsPacket.TYPE type) {
        int prevSelectedRecipe = selectedRecipe;
        ItemStack ingredient = itemHandler.getStackInSlot(0);
        ItemStack material = itemHandler.getStackInSlot(1);
        switch (type) {
            case CRAFTING_LEFT:
                do {
                    if (selectedRecipe == 0) {
                        selectedRecipe = recipeList.size() - 1;
                    } else {
                        --selectedRecipe;
                    }
                } while (selectedRecipe != prevSelectedRecipe && !recipeList.get(selectedRecipe).canCraft(ingredient, material));
                break;
            case CRAFTING_RIGHT:
                do {
                    if (selectedRecipe == recipeList.size() - 1) {
                        selectedRecipe = 0;
                    } else {
                        ++selectedRecipe;
                    }
                } while (selectedRecipe != prevSelectedRecipe && !recipeList.get(selectedRecipe).canCraft(ingredient, material));
        }

        updateResultSlot();
    }

    private void updateResultSlot() {
        if (!player.level.isClientSide && recipeList.get(selectedRecipe).canCraft(itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1))) {
            ItemStack result = recipeList.get(selectedRecipe).result.copy();
            result.addTagElement("EntityTag", resultItemTag);
            itemHandler.setStackInSlot(2, result);
            ((ServerPlayerEntity) player).connection.send(new SSetSlotPacket(containerId, 2, result));
        }
    }

    public void onCrafting() {
        if (!player.level.isClientSide) {
            PlaneWorkbenchRecipe recipe = recipeList.get(selectedRecipe);
            itemHandler.extractItem(0, recipe.ingredient.getItems()[0].getCount(), false);
            itemHandler.extractItem(1, recipe.materialAmount, false);
            onInputChanged();
        }
    }

    protected void updateCraftingResult(World world, PlayerEntity player) {
        if (!world.isClientSide) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack result = ItemStack.EMPTY;
            ItemStack ingredientStack = itemHandler.getStackInSlot(0);
            ItemStack materialStack = itemHandler.getStackInSlot(1);
            Item materialItem = materialStack.getItem();

            PlaneWorkbenchRecipe recipe = recipeList.get(selectedRecipe);

            if (recipe.canCraft(ingredientStack, materialStack) && materialItem instanceof BlockItem &&
                BlockTags.getAllTags().getTagOrEmpty(PLANE_MATERIALS).contains(((BlockItem) materialItem).getBlock())) {

                result = recipe.result.copy();
                Block block = ((BlockItem) materialItem).getBlock();
                resultItemTag.putString("material", block.getRegistryName().toString());
                result.addTagElement("EntityTag", resultItemTag);
            }

            itemHandler.setStackInSlot(2, result);
            serverPlayerEntity.connection.send(new SSetSlotPacket(containerId, 2, result));
        }
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        worldPosCallable.execute((world, blockPos) -> {
            if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
                for (int i = 0; i < itemHandler.getSlots() - 1; ++i) {
                    playerIn.drop(itemHandler.getStackInSlot(i), false);
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
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(worldPosCallable, playerIn, SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get());
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack itemStack = super.clicked(slotId, dragType, clickTypeIn, player);
        onInputChanged();
        return itemStack;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.worldPosCallable.execute((p_217067_2_, p_217067_3_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_217067_2_, playerIn));
                if (!this.moveItemStackTo(itemstack1, 10, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 10 && index < 39) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.drop(itemstack2, false);
            }
        }

        return itemstack;
    }
}
