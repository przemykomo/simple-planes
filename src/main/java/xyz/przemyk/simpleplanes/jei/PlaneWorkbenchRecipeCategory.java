package xyz.przemyk.simpleplanes.jei;

import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
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
        icon = guiHelper.createDrawableIngredient(new ItemStack(SimplePlanesItems.PLANE_WORKBENCH.get()));
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
    public String getTitle() {
        return getTitleAsTextComponent().getString();
    }

    @Override
    public ITextComponent getTitleAsTextComponent() {
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

    @Override
    public void setIngredients(PlaneWorkbenchRecipe recipe, IIngredients ingredients) {
        Stream<ItemStack> itemStackStream = BlockTags.getAllTags().getTagOrEmpty(PlaneWorkbenchContainer.PLANE_MATERIALS)
                .getValues().stream().map(block -> new ItemStack(block.asItem(), recipe.materialAmount));
        ingredients.setInputIngredients(Arrays.asList(recipe.ingredient, Ingredient.of(itemStackStream)));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PlaneWorkbenchRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(1, true, 49, 0);
        guiItemStacks.init(2, false, 107, 0);

        guiItemStacks.set(ingredients);
    }
}
