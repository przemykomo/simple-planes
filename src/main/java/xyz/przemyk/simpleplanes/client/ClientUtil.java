package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.data.EmptyModelData;

public class ClientUtil {

    public static void renderItemModelAsBlock(MatrixStack matrixStack, Minecraft minecraft, IRenderTypeBuffer buffer, int packedLight, Item item) {
        IBakedModel ibakedmodel = minecraft.getItemRenderer().getItemModelShaper().getItemModel(item);
        float f = (float)(-1 >> 16 & 255) / 255.0F;
        float f1 = (float)(-1 >> 8 & 255) / 255.0F;
        minecraft.getBlockRenderer().getModelRenderer().renderModel(matrixStack.last(), buffer.getBuffer(Atlases.cutoutBlockSheet()), null, ibakedmodel, f, f1, 1.0F, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

    }
}
