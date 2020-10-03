package xyz.przemyk.simpleplanes.upgrades.floating;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade {
    public static final Identifier TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/floating.png");
    public static final Identifier LARGE_TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/floating_large.png");

    public FloatingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FLOATING, planeEntity);
    }

    @Override
    public boolean tick() {
        if (planeEntity.isAboveWater()) {
            Vec3d motion = planeEntity.getVelocity();
            double f = 1;
            double y = MathHelper.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setVelocity(motion.x * f, y, motion.z * f);
            if (planeEntity.world.getBlockState(new BlockPos(planeEntity.getPos().add(0, 0.5, 0))).getBlock() == Blocks.WATER) {
                planeEntity.setVelocity(planeEntity.getVelocity().add(0, 0.04, 0));
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialTicks) {
        if (planeEntity.isLarge()) {
            if (planeEntity instanceof HelicopterEntity) {
                HelicopterFloatingModel.INSTANCE
                    .render(matrixStack, buffer.getBuffer(LargeFloatingModel.INSTANCE.getLayer(TEXTURE)), packedLight, OverlayTexture.DEFAULT_UV, 1.0F,
                        1.0F, 1.0F, 1.0F);
            } else {
                LargeFloatingModel.INSTANCE
                    .render(matrixStack, buffer.getBuffer(LargeFloatingModel.INSTANCE.getLayer(LARGE_TEXTURE)), packedLight, OverlayTexture.DEFAULT_UV,
                        1.0F, 1.0F, 1.0F, 1.0F);
            }
        } else {
            FloatingModel.INSTANCE
                .render(matrixStack, buffer.getBuffer(FloatingModel.INSTANCE.getLayer(TEXTURE)), packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F,
                    1.0F, 1.0F);
        }
    }
}
