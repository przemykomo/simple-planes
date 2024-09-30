package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade {

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
            if (planeEntity.level().getBlockState(new BlockPos((int) planeEntity.getX(), (int) (planeEntity.getY() + 0.5), (int) planeEntity.getZ())).getFluidState().is(FluidTags.WATER)) {
                planeEntity.setDeltaMovement(planeEntity.getDeltaMovement().add(0, 0.04, 0));
            }
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        super.render(matrixStack, buffer, packedLight, partialTicks);

        if (planeEntity.getType() == SimplePlanesEntities.CARGO_PLANE.get()) {
            UpgradesModels.WOODEN_CARGO_FLOATING.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))), packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.FLOATY_BEDDING.get().getDefaultInstance();
    }
}
