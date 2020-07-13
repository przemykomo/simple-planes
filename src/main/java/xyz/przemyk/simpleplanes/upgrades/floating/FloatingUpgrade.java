package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade {
    public static final FloatingModel MODEL = new FloatingModel();
    public static final LargeFloatingModel LARGE_MODEL = new LargeFloatingModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/floating.png");
    public static final ResourceLocation LARGE_TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/floating_large.png");

    public FloatingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FLOATING.get(), planeEntity);
    }

    @Override
    public boolean tick() {
        if (planeEntity.isAboveWater()) {

            Vector3d motion = planeEntity.getMotion();
            double f = 0.98;
            double y = MathUtil.lerp(0.9,motion.y,Math.max(motion.y, 0));
            planeEntity.setMotion(motion.x * f, y, motion.z * f);
            if (planeEntity.world.getBlockState(new BlockPos(planeEntity.getPositionVec().add(0, 0.6, 0))).getBlock() == Blocks.WATER) {
                planeEntity.setMotion(planeEntity.getMotion().add(0,0.005,0));
            }
            else
            {
                planeEntity.setMotion(planeEntity.getMotion().add(0,0.001,0));
            }



        }
        return false;
    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        if (planeEntity.isLarge()) {
            LARGE_MODEL.render(matrixStack, buffer.getBuffer(LARGE_MODEL.getRenderType(LARGE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            MODEL.render(matrixStack, buffer.getBuffer(MODEL.getRenderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
