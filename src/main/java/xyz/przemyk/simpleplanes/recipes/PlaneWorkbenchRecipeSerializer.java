//package xyz.przemyk.simpleplanes.recipes;
//
//import com.google.gson.JsonObject;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.GsonHelper;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.ShapedRecipe;
//
//import javax.annotation.Nullable;
//
//public class PlaneWorkbenchRecipeSerializer implements RecipeSerializer<PlaneWorkbenchRecipe> {
//
//    @Override
//    public PlaneWorkbenchRecipe fromJson(ResourceLocation id, JsonObject json) {
//        Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
//        int ingredientAmount = GsonHelper.getAsInt(json, "ingredient_amount");
//        int materialAmount = GsonHelper.getAsInt(json, "material_amount");
//        ItemStack result = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")).getDefaultInstance();
//        return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
//    }
//
//    @Nullable
//    @Override
//    public PlaneWorkbenchRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
//        Ingredient ingredient = Ingredient.fromNetwork(buffer);
//        int ingredientAmount = buffer.readVarInt();
//        int materialAmount = buffer.readVarInt();
//        ItemStack result = buffer.readItem();
//        return new PlaneWorkbenchRecipe(id, ingredient, ingredientAmount, materialAmount, result);
//    }
//
//    @Override
//    public void toNetwork(FriendlyByteBuf buffer, PlaneWorkbenchRecipe recipe) {
//        recipe.ingredient().toNetwork(buffer);
//        buffer.writeVarInt(recipe.ingredientAmount());
//        buffer.writeVarInt(recipe.materialAmount());
//        buffer.writeItem(recipe.result());
//    }
//}
