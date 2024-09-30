package xyz.przemyk.simpleplanes.container;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesComponents;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

import java.util.List;

public class PlaneWorkbenchContainer extends AbstractContainerMenu {

    public static final ResourceLocation PLANE_MATERIALS = ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane_materials");
    public static final TagKey<Block> PLANE_MATERIALS_TAG = BlockTags.create(PLANE_MATERIALS);

    private final ItemStackHandler itemHandler;
    private final ItemStackHandler resultItemHandler = new ItemStackHandler();
    private final ContainerLevelAccess usabilityTest;
    private final Player player;
    private final DataSlot selectedRecipe;

    private final CompoundTag resultItemTag = new CompoundTag();
    private final List<RecipeHolder<PlaneWorkbenchRecipe>> recipeList;

    public PlaneWorkbenchContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2), DataSlot.standalone());
    }

    public PlaneWorkbenchContainer(int id, Inventory playerInventory, BlockPos blockPos, ItemStackHandler itemHandler, DataSlot selectedRecipe) {
        super(SimplePlanesContainers.PLANE_WORKBENCH.get(), id);
        this.player = playerInventory.player;
        this.itemHandler = itemHandler;
        this.usabilityTest = ContainerLevelAccess.create(player.level(), blockPos);
        this.recipeList = player.level().getRecipeManager().getAllRecipesFor(SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE.get());
        this.selectedRecipe = selectedRecipe;

        addSlot(new SlotItemHandler(itemHandler, 0, 28, 47));
        addSlot(new SlotItemHandler(itemHandler, 1, 75, 47));
        addSlot(new PlaneCraftingResultSlot(player, this, resultItemHandler, 0, 134, 47));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        addDataSlot(selectedRecipe);
        updateCraftingResult();
    }

    public void cycleItems(CycleItemsPacket.Direction direction) {
        int prevSelectedRecipe = selectedRecipe.get();
        ItemStack ingredient = itemHandler.getStackInSlot(0);
        ItemStack material = itemHandler.getStackInSlot(1);

        switch (direction) {
            case CRAFTING_LEFT -> {
                do {
                    if (selectedRecipe.get() == 0) {
                        selectedRecipe.set(recipeList.size() - 1);
                    } else {
                        selectedRecipe.set(selectedRecipe.get() - 1);
                    }
                } while (selectedRecipe.get() != prevSelectedRecipe && !recipeList.get(selectedRecipe.get()).value().canCraft(ingredient, material));
            }
            case CRAFTING_RIGHT -> {
                do {
                    if (selectedRecipe.get() == recipeList.size() - 1) {
                        selectedRecipe.set(0);
                    } else {
                        selectedRecipe.set(selectedRecipe.get() + 1);
                    }
                } while (selectedRecipe.get() != prevSelectedRecipe && !recipeList.get(selectedRecipe.get()).value().canCraft(ingredient, material));
            }
        }

        updateCraftingResult();
    }

    public void onCrafting() {
        if (!player.level().isClientSide) {
            PlaneWorkbenchRecipe recipe = recipeList.get(selectedRecipe.get()).value();
            itemHandler.extractItem(0, recipe.ingredientAmount(), false);
            itemHandler.extractItem(1, recipe.materialAmount(), false);
            updateCraftingResult();
        }
    }

    @SuppressWarnings("deprecation")
    protected void updateCraftingResult() {
        if (!this.player.level().isClientSide) {
            ServerPlayer serverPlayerEntity = (ServerPlayer) this.player;
            ItemStack result = ItemStack.EMPTY;
            ItemStack ingredientStack = itemHandler.getStackInSlot(0);
            ItemStack materialStack = itemHandler.getStackInSlot(1);
            Item materialItem = materialStack.getItem();

            PlaneWorkbenchRecipe recipe = recipeList.get(selectedRecipe.get()).value();

            if (recipe.canCraft(ingredientStack, materialStack) && materialItem instanceof BlockItem blockItem &&
                blockItem.getBlock().builtInRegistryHolder().is(PLANE_MATERIALS_TAG)) {

                result = recipe.result().copy();
                Block block = blockItem.getBlock();
                resultItemTag.putString("material", BuiltInRegistries.BLOCK.getKey(block).toString());
                result.set(SimplePlanesComponents.ENTITY_TAG, resultItemTag);
            }

            resultItemHandler.setStackInSlot(0, result);
            serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(containerId, 0, 2, result));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(usabilityTest, playerIn, SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get());
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        super.clicked(slotId, dragType, clickTypeIn, player);
        updateCraftingResult();
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack originalItemStack;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack inputItemStack = slot.getItem();
            originalItemStack = inputItemStack.copy();

            if (index == 2) { // test for result slot
                inputItemStack.getItem().onCraftedBy(inputItemStack, playerIn.level(), playerIn);

                // move result to the player inventory
                if (!moveItemStackTo(inputItemStack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(inputItemStack, originalItemStack);

            } else if (index >= 3 && index < 39) { // test for slot in player inventory

                if (!moveItemStackTo(inputItemStack, 0, 2, false)) { // move it to recipe input

                    if (index < 30) { // test for main player inventory (excluding hotbar)

                        if (!moveItemStackTo(inputItemStack, 30, 39, false)) { // move it to the hotbar
                            return ItemStack.EMPTY;
                        }

                    } else if (!moveItemStackTo(inputItemStack, 3, 30, false)) { // if it's in hotbar, move it to the main inventory
                        return ItemStack.EMPTY;
                    }

                }

            } else if (!moveItemStackTo(inputItemStack, 3, 39, false)) { // if it's the recipe input, move it to the player inventory
                return ItemStack.EMPTY;
            }

            if (inputItemStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (inputItemStack.getCount() == originalItemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, inputItemStack);
            if (index == 2) {
                playerIn.drop(inputItemStack, false);
            }
        }

//        updateCraftingResult();
        return ItemStack.EMPTY;
    }
}
