package sappy.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.PlaneRenderer;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.SPUpgrades;

public class FloatingUpgrade extends Upgrade {

    public FloatingUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.FLOATY_BEDDING.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (planeEntity.getHealth() > 0 && planeEntity.isOnWater()) {
            Vec3 motion = planeEntity.getDeltaMovement();
            double f = 1;
            double y = Mth.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setDeltaMovement(motion.x * f, y, motion.z * f);
            if (planeEntity.level().getBlockState(
                    new BlockPos((int) planeEntity.getX(), (int) (planeEntity.getY() + 0.5), (int) planeEntity.getZ()))
                    .getFluidState().is(FluidTags.WATER)) {
                planeEntity.setDeltaMovement(planeEntity.getDeltaMovement().add(0, 0.04, 0));
            }
        }
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        super.render(state, poseStack, submitNodeCollector, camera);
        if (planeEntity.getType() == SPEntities.CARGO_PLANE.get()) {
            submitNodeCollector.submitModel(UpgradesModels.WOODEN_CARGO_FLOATING, state, poseStack,
                    UpgradesModels.WOODEN_CARGO_FLOATING.renderType(PlaneRenderer.getMaterialTexture(state)),
                    state.lightCoords, OverlayTexture.NO_OVERLAY,
                    0, null);
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.FLOATY_BEDDING.get().getDefaultInstance();
    }
}
