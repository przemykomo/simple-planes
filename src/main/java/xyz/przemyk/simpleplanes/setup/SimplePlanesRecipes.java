package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;

public class SimplePlanesRecipes {
    public static final IRecipeType<PlaneWorkbenchRecipe> PLANE_WORKBENCH_RECIPE_TYPE = new IRecipeType<PlaneWorkbenchRecipe>() {
        @Override
        public String toString() {
            return SimplePlanesMod.MODID + ":plane_workbench";
        }
    };

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, SimplePlanesRecipes::registerRecipeSerializers);
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(PLANE_WORKBENCH_RECIPE_TYPE.toString()), PLANE_WORKBENCH_RECIPE_TYPE);
        event.getRegistry().register(PlaneWorkbenchRecipe.SERIALIZER);
    }
}
