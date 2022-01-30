package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class ArmorUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/armor.png");
    public static final ResourceLocation TEXTURE_LARGE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/large_armor.png");

    public ArmorUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ARMOR.get(), planeEntity);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            UpgradesModels.LARGE_ARMOR.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.LARGE_ARMOR.renderType(TEXTURE_LARGE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            UpgradesModels.ARMOR.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.ARMOR.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(SimplePlanesItems.ARMOR.get());
    }

    public float getReducedDamage(float amount) {
        return amount * (1.0f - (0.04f * getArmorValue()));
    }

    public int getArmorValue() {
        return 15; //TODO: enchantments
    }
}
