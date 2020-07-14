package xyz.przemyk.simpleplanes.upgrades.folding;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.AbstractPlaneRenderer;
import xyz.przemyk.simpleplanes.render.FurnacePlaneModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FoldingUpgrade extends Upgrade {
    public static final FoldingModel FOLDING_MODEL = new FoldingModel();
    public static final FoldingSprayerModel LARGE_SPRAYER_MODEL = new FoldingSprayerModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/folding.png");
    public static final AxisAlignedBB AFFECT_ENTITIES = new AxisAlignedBB(-3, -3, -3, 3, 0, 3);

    public FoldingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FOLDING.get(), planeEntity);
    }



    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(FOLDING_MODEL.getRenderType(TEXTURE));
        FOLDING_MODEL.render(matrixStack,ivertexbuilder,packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
