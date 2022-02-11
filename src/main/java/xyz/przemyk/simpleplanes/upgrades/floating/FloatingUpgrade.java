package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
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
        if (planeEntity.getHealth() > 0 && planeEntity.isOnWater()) {
            Vec3 motion = planeEntity.getDeltaMovement();
            double f = 1;
            double y = Mth.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setDeltaMovement(motion.x * f, y, motion.z * f);
            if (planeEntity.level.getBlockState(new BlockPos(planeEntity.position().add(0, 0.5, 0))).getBlock() == Blocks.WATER) {
                planeEntity.setDeltaMovement(planeEntity.getDeltaMovement().add(0, 0.04, 0));
            }
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            UpgradesModels.HELICOPTER_FLOATING.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.HELICOPTER_FLOATING.renderType(HELICOPTER_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            UpgradesModels.LARGE_FLOATING.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.LARGE_FLOATING.renderType(LARGE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            UpgradesModels.FLOATING.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.FLOATING.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.FLOATY_BEDDING.get());
    }
}
