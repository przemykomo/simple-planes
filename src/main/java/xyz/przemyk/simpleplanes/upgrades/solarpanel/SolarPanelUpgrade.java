package xyz.przemyk.simpleplanes.upgrades.solarpanel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.ElectricEngineUpgrade;

import javax.annotation.Nullable;

public class SolarPanelUpgrade extends Upgrade {

    private final short MAX_PER_TICK;

    public SolarPanelUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SOLAR_PANEL.get(), planeEntity);
        if (planeEntity instanceof LargePlaneEntity) {
            MAX_PER_TICK = 10;
        } else {
            MAX_PER_TICK = 5;
        }
    }

    @Override
    public void tick() {
        PlaneEntity entity = getPlaneEntity();
        Level world = entity.level();
        if (canSeeSun(world, entity.getOnPos().above())) {
            float brightness = MAX_PER_TICK * getSunBrightness(entity.level(), 1.0F);
            if (entity.engineUpgrade instanceof ElectricEngineUpgrade engine) {
                engine.energyStorage.receiveEnergy((int) brightness, false);
            }
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        UpgradesModels.ModelEntry modelEntry = UpgradesModels.MODEL_ENTRIES.get(getType());
        if (entityType == SimplePlanesEntities.PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.itemEntityTranslucentCull(modelEntry.normalTexture()), false, false);
            modelEntry.normal().renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        } else if(entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.itemEntityTranslucentCull(modelEntry.largeTexture()), false, false);
            modelEntry.large().renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.SOLAR_PANEL.get().getDefaultInstance();
    }

    private static boolean canSeeSun(@Nullable Level level, BlockPos pos) {
        return level != null && level.dimensionType().hasSkyLight() && level.getSkyDarken() < 4 && level.canSeeSky(pos);
    }

    public static float getSunBrightness(Level world, float partialTicks) {
        float f = world.getTimeOfDay(partialTicks);
        float f1 = 1.0F - (Mth.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.2F);
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        f1 = 1.0F - f1;
        f1 = (float) (f1 * (1.0D - world.getRainLevel(partialTicks) * 5.0F / 16.0D));
        f1 = (float) (f1 * (1.0D - world.getThunderLevel(partialTicks) * 5.0F / 16.0D));
        return f1 * 0.8F;
    }

}
