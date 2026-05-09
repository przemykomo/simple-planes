package sappy.simpleplanes.upgrades.solarpanel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.SPUpgrades;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.LargePlaneEntity;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.upgrades.engines.electric.ElectricEngineUpgrade;

import javax.annotation.Nullable;

public class SolarPanelUpgrade extends Upgrade {

    private final float MAX_PER_TICK;

    public SolarPanelUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.SOLAR_PANEL.get(), planeEntity);
        if (planeEntity instanceof LargePlaneEntity) {
            MAX_PER_TICK = 10.0f;
        } else {
            MAX_PER_TICK = 5.0f;
        }
    }

    @Override
    public void tick() {
        PlaneEntity entity = getPlaneEntity();
        Level world = entity.level();
        // if (canSeeSun(world, entity.getOnPos().above())) {
        float brightness = MAX_PER_TICK * entity.level().getEffectiveSkyBrightness(entity.blockPosition()) / 15.0f;
        if (entity.engineUpgrade instanceof ElectricEngineUpgrade engine) {
            engine.energyStorage.receiveEnergy((int) brightness, false);
        }
        // }
    }

    // @Override // TODO
    // public void render(PoseStack matrixStack, MultiBufferSource buffer, int
    // packedLight, float partialTicks) {
    // EntityType<?> entityType = planeEntity.getType();
    // UpgradesModels.ModelEntry modelEntry =
    // UpgradesModels.MODEL_ENTRIES.get(getType());
    // if (entityType == SimplePlanesEntities.PLANE.get()) {
    // VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer,
    // RenderType.itemEntityTranslucentCull(modelEntry.normalTexture()), false);
    // modelEntry.normal().renderToBuffer(matrixStack, vertexconsumer, packedLight,
    // OverlayTexture.NO_OVERLAY);
    // } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
    // VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer,
    // RenderType.itemEntityTranslucentCull(modelEntry.largeTexture()), false);
    // modelEntry.large().renderToBuffer(matrixStack, vertexconsumer, packedLight,
    // OverlayTexture.NO_OVERLAY);
    // } else if (entityType == SimplePlanesEntities.CARGO_PLANE.get()) {
    // VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer,
    // RenderType.armorCutoutNoCull(modelEntry.cargoTexture()), false);
    // modelEntry.cargo().renderToBuffer(matrixStack, vertexconsumer, packedLight,
    // OverlayTexture.NO_OVERLAY);
    // }
    // }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.SOLAR_PANEL.get().getDefaultInstance();
    }

    // private static boolean canSeeSun(@Nullable Level level, BlockPos pos) {
    // return level != null && level.dimensionType().hasSkyLight() &&
    // level.getSkyDarken() < 4 && level.canSeeSky(pos);
    // }

    // public static float getSunBrightness(Level world, float partialTicks) {
    // float f = world.getTimeOfDay(partialTicks);
    // float f1 = 1.0F - (Mth.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.2F);
    // f1 = Mth.clamp(f1, 0.0F, 1.0F);
    // f1 = 1.0F - f1;
    // f1 = (float) (f1 * (1.0D - world.getRainLevel(partialTicks) * 5.0F / 16.0D));
    // f1 = (float) (f1 * (1.0D - world.getThunderLevel(partialTicks) * 5.0F /
    // 16.0D));
    // return f1 * 0.8F;
    // }

}
