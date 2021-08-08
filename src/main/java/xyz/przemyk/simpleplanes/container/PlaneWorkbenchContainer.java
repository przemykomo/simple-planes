package xyz.przemyk.simpleplanes.container;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

import java.util.List;

//TODO: make plane workbench work like crafting station with proper item handler, don't try to replicate bugged crafting table
public class PlaneWorkbenchContainer extends AbstractContainerMenu {

    public static final ResourceLocation PLANE_MATERIALS = new ResourceLocation(SimplePlanesMod.MODID, "plane_materials");

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private final ContainerLevelAccess worldPosCallable;
    private final Player player;

    private int selectedRecipe = 0;
    private final CompoundTag resultItemTag = new CompoundTag();
    private List<PlaneWorkbenchRecipe> recipeList;

    public PlaneWorkbenchContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, ContainerLevelAccess.NULL);
    }

    public PlaneWorkbenchContainer(int id, Inventory playerInventory, ContainerLevelAccess worldPosCallable) {
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

    public void updateCraftingResult() {
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

        updateCraftingResult();
    }

    public void onCrafting() {
        if (!player.level.isClientSide) {
            PlaneWorkbenchRecipe recipe = recipeList.get(selectedRecipe);
            itemHandler.extractItem(0, recipe.ingredient.getItems()[0].getCount(), false);
            itemHandler.extractItem(1, recipe.materialAmount, false);
            updateCraftingResult();
        }
    }

    protected void updateCraftingResult(Level world, Player player) {
        if (!world.isClientSide) {
            ServerPlayer serverPlayerEntity = (ServerPlayer) player;
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
            serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(containerId, 2, result));
        }
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        worldPosCallable.execute((world, blockPos) -> {
            if (!playerIn.isAlive() || playerIn instanceof ServerPlayer && ((ServerPlayer) playerIn).hasDisconnected()) {
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
    public boolean stillValid(Player playerIn) {
        return stillValid(worldPosCallable, playerIn, SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get());
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        ItemStack itemStack = super.clicked(slotId, dragType, clickTypeIn, player);
        updateCraftingResult();
        return itemStack;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
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
