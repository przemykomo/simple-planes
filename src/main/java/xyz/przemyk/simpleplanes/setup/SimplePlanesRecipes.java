package xyz.przemyk.simpleplanes.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipeSerializer;

public class SimplePlanesRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SimplePlanesMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SimplePlanesMod.MODID);

    public static void init() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<RecipeSerializer<?>> PLANE_WORKBENCH_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("plane_workbench", PlaneWorkbenchRecipeSerializer::new);
    public static final RegistryObject<RecipeType<PlaneWorkbenchRecipe>> PLANE_WORKBENCH_RECIPE_TYPE = RECIPE_TYPES.register("plane_workbench", () -> RecipeType.simple(new ResourceLocation(SimplePlanesMod.MODID, "plane_workbench")));
}
