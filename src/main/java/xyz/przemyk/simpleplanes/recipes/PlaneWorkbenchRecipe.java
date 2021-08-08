package xyz.przemyk.simpleplanes.recipes;

import com.google.gson.JsonObject;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

import javax.annotation.Nullable;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class PlaneWorkbenchRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    public final ResourceLocation id;
    public final Ingredient ingredient;
    public final int ingredientAmount;
    public final int materialAmount;
    public final ItemStack result;

    public PlaneWorkbenchRecipe(ResourceLocation id, Ingredient ingredient, int ingredientAmount, int materialAmount, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.ingredientAmount = ingredientAmount;
        this.materialAmount = materialAmount;
        this.result = result;
    }

    public boolean canCraft(ItemStack ingredientStack, ItemStack materialStack) {
        return ingredientStack.getCount() >= ingredientAmount && materialStack.getCount() >= materialAmount && ingredient.test(ingredientStack);
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PlaneWorkbenchRecipe> {

        public Serializer() {
            setRegistryName(SimplePlanesMod.MODID, "plane_workbench");
        }

        @Override
        public PlaneWorkbenchRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            int ingredientAmount = GsonHelper.getAsInt(json, "ingredient_amount");
            int materialAmount = GsonHelper.getAsInt(json, "material_amount");
            ItemStack result = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")).getDefaultInstance();
            return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
        }

        @Nullable
        @Override
        public PlaneWorkbenchRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int ingredientAmount = buffer.readVarInt();
            int materialAmount = buffer.readVarInt();
            ItemStack result = buffer.readItem();
            return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PlaneWorkbenchRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.ingredientAmount);
            buffer.writeVarInt(recipe.materialAmount);
            buffer.writeItem(recipe.result);
        }
    }

    @Override public boolean matches(Container p_77569_1_, Level p_77569_2_) { return false; }
    @Override public ItemStack assemble(Container p_77572_1_) { return null; }
    @Override public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) { return false; }
}
