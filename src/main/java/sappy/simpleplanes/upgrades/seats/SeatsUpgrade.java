package sappy.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.PlaneRenderer;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.SPUpgrades;

public class SeatsUpgrade extends Upgrade {

    public static final Identifier TEXTURE = SPMod.texture("seats.png");
    public static final Identifier LARGE_TEXTURE = SPMod.texture("seats_large.png");
    public static final Identifier CARGO_TEXTURE = SPMod.texture("cargo_plane_metal.png");
    public static final Identifier HELI_TEXTURE = SPMod.texture("seats_heli.png");

    public SeatsUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.SEATS.get(), planeEntity);
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        EntityType<?> entityType = planeEntity.getType();
        EntityModel<PlaneRenderState> model;
        EntityModel<PlaneRenderState> woodModel = null;
        Identifier texture;

        if (entityType == SPEntities.PLANE.get()) {
            model = UpgradesModels.SEATS;
            woodModel = UpgradesModels.WOODEN_SEATS;
            texture = TEXTURE;
        } else if (entityType == SPEntities.LARGE_PLANE.get()) {
            model = UpgradesModels.LARGE_SEATS;
            texture = LARGE_TEXTURE;
        } else if (entityType == SPEntities.CARGO_PLANE.get()) {
            model = UpgradesModels.CARGO_SEATS;
            texture = CARGO_TEXTURE;
        } else {
            model = UpgradesModels.HELI_SEATS;
            woodModel = UpgradesModels.WOODEN_HELI_SEATS;
            texture = HELI_TEXTURE;
        }

        RenderType render = model.renderType(texture);
        submitNodeCollector.submitModel(model, state, poseStack, render, state.lightCoords, OverlayTexture.NO_OVERLAY,
                state.outlineColor, null);
        if (woodModel != null) {
            render = model.renderType(PlaneRenderer.getMaterialTexture(state));
            submitNodeCollector.submitModel(model, state, poseStack, render, state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor, null);
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void onRemoved() {
        planeEntity.ejectPassengers();
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.SEATS.get().getDefaultInstance();
    }
}
