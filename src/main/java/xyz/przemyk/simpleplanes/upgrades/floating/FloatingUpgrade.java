package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating.png");
    public static final ResourceLocation LARGE_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating_large.png");
    public static final ResourceLocation HELICOPTER_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating_helicopter.png");

    public FloatingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FLOATY_BEDDING.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (planeEntity.isOnWater()) {
            Vector3d motion = planeEntity.getDeltaMovement();
            double f = 1;
            double y = MathHelper.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setDeltaMovement(motion.x * f, y, motion.z * f);
            if (planeEntity.level.getBlockState(new BlockPos(planeEntity.position().add(0, 0.5, 0))).getBlock() == Blocks.WATER) {
                planeEntity.setDeltaMovement(planeEntity.getDeltaMovement().add(0, 0.04, 0));
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            HelicopterFloatingModel.INSTANCE.renderToBuffer(matrixStack, buffer.getBuffer(LargeFloatingModel.INSTANCE.renderType(HELICOPTER_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            LargeFloatingModel.INSTANCE.renderToBuffer(matrixStack, buffer.getBuffer(LargeFloatingModel.INSTANCE.renderType(LARGE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            FloatingModel.INSTANCE.renderToBuffer(matrixStack, buffer.getBuffer(FloatingModel.INSTANCE.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void writePacket(PacketBuffer buffer) {}

    @Override
    public void readPacket(PacketBuffer buffer) {}

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(SimplePlanesItems.FLOATY_BEDDING.get());
    }
}
