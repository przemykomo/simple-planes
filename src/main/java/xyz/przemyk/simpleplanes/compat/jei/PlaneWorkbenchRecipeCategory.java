package xyz.przemyk.simpleplanes.compat.jei;

import com.google.common.collect.Streams;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.Arrays;
import java.util.stream.Stream;

public class PlaneWorkbenchRecipeCategory implements IRecipeCategory<PlaneWorkbenchRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "plane_workbench");

    private final IDrawable background;
    private final IDrawable icon;

    public PlaneWorkbenchRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png"), 0, 168, 125, 18);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(SimplePlanesItems.PLANE_WORKBENCH.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends PlaneWorkbenchRecipe> getRecipeClass() {
        return PlaneWorkbenchRecipe.class;
    }

    @Override
    public Component getTitle() {
        return SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get().getName();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PlaneWorkbenchRecipe recipe, IFocusGroup focuses) {
        Stream<ItemStack> materialStackStream = Streams.stream(Registry.BLOCK.getTagOrEmpty(PlaneWorkbenchContainer.PLANE_MATERIALS_TAG)).map(blockHolder ->
                new ItemStack(blockHolder.value(), recipe.materialAmount())
        );
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.of(Arrays.stream(recipe.ingredient().getItems()).map(itemStack -> new ItemStack(itemStack.getItem(), recipe.ingredientAmount()))));
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(Ingredient.of(materialStackStream));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addItemStack(recipe.result());
    }
}
