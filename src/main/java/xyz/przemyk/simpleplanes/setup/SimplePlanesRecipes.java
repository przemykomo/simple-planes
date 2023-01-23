package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipeSerializer;

public class SimplePlanesRecipes {
    public static void init() {}

    public static final RecipeSerializer<?> PLANE_WORKBENCH_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(SimplePlanesMod.MODID, "plane_workbench"), new PlaneWorkbenchRecipeSerializer());
    public static final RecipeType<PlaneWorkbenchRecipe> PLANE_WORKBENCH_RECIPE_TYPE = RecipeType.register(SimplePlanesMod.MODID + ":plane_workbench");
}
