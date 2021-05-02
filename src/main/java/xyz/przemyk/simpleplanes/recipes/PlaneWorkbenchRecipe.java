package xyz.przemyk.simpleplanes.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

import javax.annotation.Nullable;

public class PlaneWorkbenchRecipe implements IRecipe<IInventory> {

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
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PlaneWorkbenchRecipe> {

        public Serializer() {
            setRegistryName(SimplePlanesMod.MODID, "plane_workbench");
        }

        @Override
        public PlaneWorkbenchRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
            int ingredientAmount = JSONUtils.getAsInt(json, "ingredient_amount");
            int materialAmount = JSONUtils.getAsInt(json, "material_amount");
            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
        }

        @Nullable
        @Override
        public PlaneWorkbenchRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int ingredientAmount = buffer.readVarInt();
            int materialAmount = buffer.readVarInt();
            ItemStack result = buffer.readItem();
            return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PlaneWorkbenchRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.ingredientAmount);
            buffer.writeVarInt(recipe.materialAmount);
            buffer.writeItem(recipe.result);
        }
    }

    @Override public boolean matches(IInventory p_77569_1_, World p_77569_2_) { return false; }
    @Override public ItemStack assemble(IInventory p_77572_1_) { return null; }
    @Override public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) { return false; }
}
