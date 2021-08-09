package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.data.EmptyModelData;

public class ClientUtil {

    public static void renderItemModelAsBlock(PoseStack matrixStack, Minecraft minecraft, MultiBufferSource buffer, int packedLight, Item item) {
        BakedModel bakedModel = minecraft.getItemRenderer().getItemModelShaper().getItemModel(item);
        float f = (float)(-1 >> 16 & 255) / 255.0F;
        float f1 = (float)(-1 >> 8 & 255) / 255.0F;
        minecraft.getBlockRenderer().getModelRenderer().renderModel(matrixStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), null, bakedModel, f, f1, 1.0F, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
    }
}
