package xyz.przemyk.simpleplanes.setup;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;

public class SimplePlanesRecipes {
    public static final RecipeType<PlaneWorkbenchRecipe> PLANE_WORKBENCH_RECIPE_TYPE = new RecipeType<>() {
        @Override
        public String toString() {
            return SimplePlanesMod.MODID + ":plane_workbench";
        }
    };

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, SimplePlanesRecipes::registerRecipeSerializers);
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(PLANE_WORKBENCH_RECIPE_TYPE.toString()), PLANE_WORKBENCH_RECIPE_TYPE);
        event.getRegistry().register(PlaneWorkbenchRecipe.SERIALIZER);
    }
}
