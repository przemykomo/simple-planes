package xyz.przemyk.simpleplanes.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class PlaneWorkbenchRecipeSerializer implements RecipeSerializer<PlaneWorkbenchRecipe> {

    public static final MapCodec<PlaneWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(
        kind -> kind.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(PlaneWorkbenchRecipe::ingredient),
                Codec.INT.fieldOf("ingredient_amount").forGetter(PlaneWorkbenchRecipe::ingredientAmount),
                Codec.INT.fieldOf("material_amount").forGetter(PlaneWorkbenchRecipe::materialAmount),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(null))
            ).apply(kind, PlaneWorkbenchRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PlaneWorkbenchRecipe> STREAM_CODEC = StreamCodec.of(
        PlaneWorkbenchRecipeSerializer::toNetwork, PlaneWorkbenchRecipeSerializer::fromNetwork
    );

    public static PlaneWorkbenchRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        int ingredientAmount = buffer.readVarInt();
        int materialAmount = buffer.readVarInt();
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        return new PlaneWorkbenchRecipe(ingredient, ingredientAmount, materialAmount, result);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buffer, PlaneWorkbenchRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient());
        buffer.writeVarInt(recipe.ingredientAmount());
        buffer.writeVarInt(recipe.materialAmount());
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
    }

    @Override
    public MapCodec<PlaneWorkbenchRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PlaneWorkbenchRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
