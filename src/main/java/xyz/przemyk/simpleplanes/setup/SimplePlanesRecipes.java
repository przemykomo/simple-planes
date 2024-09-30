package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipe;
import xyz.przemyk.simpleplanes.recipes.PlaneWorkbenchRecipeSerializer;

import java.util.function.Supplier;

public class SimplePlanesRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, SimplePlanesMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }

    public static final Supplier<RecipeSerializer<?>> PLANE_WORKBENCH_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("plane_workbench", PlaneWorkbenchRecipeSerializer::new);
    public static final Supplier<RecipeType<PlaneWorkbenchRecipe>> PLANE_WORKBENCH_RECIPE_TYPE = RECIPE_TYPES.register("plane_workbench", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane_workbench")));
}
