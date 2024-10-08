package xyz.przemyk.simpleplanes.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import java.util.Arrays;
import java.util.stream.Stream;

public class PlaneWorkbenchRecipeCategory extends AbstractRecipeCategory<PlaneWorkbenchRecipe> {

    public static final RecipeType<PlaneWorkbenchRecipe> RECIPE_TYPE = RecipeType.create(SimplePlanesMod.MODID, "plane_workbench", PlaneWorkbenchRecipe.class);

    public PlaneWorkbenchRecipeCategory(IGuiHelper guiHelper) {
        super(
            RECIPE_TYPE,
            SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get().getName(),
            guiHelper.createDrawableItemLike(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get()),
            125,
            18
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PlaneWorkbenchRecipe recipe, IFocusGroup focuses) {
        BuiltInRegistries.BLOCK.getTag(PlaneWorkbenchContainer.PLANE_MATERIALS_TAG).ifPresent(tag -> {
            Stream<ItemStack> materialStackStream = tag.stream().map(block -> new ItemStack(block.value(), recipe.materialAmount()));
            builder.addInputSlot(1, 1).addIngredients(
                Ingredient.of(Arrays.stream(recipe.ingredient().getItems()).map(itemStack ->
                    new ItemStack(itemStack.getItem(), recipe.ingredientAmount())))).setStandardSlotBackground();
            builder.addInputSlot(50, 1).addIngredients(Ingredient.of(materialStackStream)).setStandardSlotBackground();
            builder.addOutputSlot(108, 1).addItemStack(recipe.result()).setStandardSlotBackground();
        });
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, PlaneWorkbenchRecipe recipe, IFocusGroup focuses) {
        builder.addRecipePlusSign().setPosition(27, 3);
        builder.addRecipeArrow().setPosition(76, 1);
    }
}
