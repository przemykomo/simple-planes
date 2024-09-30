package xyz.przemyk.simpleplanes.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

public record PlaneWorkbenchRecipe(Ingredient ingredient, int ingredientAmount,
                                   int materialAmount,
                                   ItemStack result) implements Recipe<RecipeInput> {

    public boolean canCraft(ItemStack ingredientStack, ItemStack materialStack) {
        return ingredientStack.getCount() >= ingredientAmount && materialStack.getCount() >= materialAmount && ingredient.test(ingredientStack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE.get();
    }

    @Override
    public boolean matches(RecipeInput p_77569_1_, Level p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }
}
