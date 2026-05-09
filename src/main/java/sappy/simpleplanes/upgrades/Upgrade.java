package sappy.simpleplanes.upgrades;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;

import java.util.function.Function;

public abstract class Upgrade {

    private final UpgradeType type;
    protected final PlaneEntity planeEntity;
    public boolean updateClient = false;
    public boolean removed = false;

    public PlaneEntity getPlaneEntity() {
        return planeEntity;
    }

    public Upgrade(UpgradeType type, PlaneEntity planeEntity) {
        this.type = type;
        this.planeEntity = planeEntity;
    }

    /**
     * Call it when data is changed, and it needs to be synced to the client.
     * If called on a server, results in calling writePacket method on a server and
     * readPacket on a client
     */
    protected void updateClient() {
        updateClient = true;
    }

    /**
     * Call it to remove this upgrade from the plane.
     */
    public void remove() {
        removed = true;
    }

    public final UpgradeType getType() {
        return type;
    }

    /**
     * Called when passenger right clicks with item.
     *
     * @param event The right click event
     */
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
    }

    /**
     * Called every tick by plane entity.
     */
    public void tick() {
    }

    /**
     * Called to render upgrade model. Loading model outside this method may crash
     * server.
     */
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        EntityType<?> entityType = planeEntity.getType();
        UpgradesModels.ModelEntry modelEntry = UpgradesModels.MODEL_ENTRIES.get(getType());
        EntityModel<PlaneRenderState> model;
        Identifier texture;

        if (entityType == SPEntities.PLANE.get()) {
            model = modelEntry.normal();
            texture = modelEntry.normalTexture();
        } else if (entityType == SPEntities.LARGE_PLANE.get()) {
            model = modelEntry.large();
            texture = modelEntry.largeTexture();
        } else if (entityType == SPEntities.CARGO_PLANE.get()) {
            model = modelEntry.cargo();
            texture = modelEntry.cargoTexture();
        } else {
            model = modelEntry.heli();
            texture = modelEntry.heliTexture();
        }

        RenderType render = model.renderType(texture);
        submitNodeCollector.submitModel(model, state, poseStack, render, state.lightCoords, OverlayTexture.NO_OVERLAY,
                state.outlineColor, null);
    }

    public Tag serializeNBT() {
        return new CompoundTag();
    }

    public void deserializeNBT(CompoundTag nbt) {
    }

    public void onApply(ItemStack itemStack) {
    }

    /**
     * Called on the server.
     */
    public abstract void writePacket(RegistryFriendlyByteBuf buffer);

    /**
     * Called on the client.
     */
    public abstract void readPacket(RegistryFriendlyByteBuf buffer);

    /**
     * Called when upgrade is removed using wrench.
     */
    public void onRemoved() {
    }

    public abstract ItemStack getItemStack();

    public boolean canBeDroppedAsPayload() {
        return false;
    }

    public void dropAsPayload() {
    }

    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
    }

    public void renderScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks,
            PlaneInventoryScreen planeInventoryScreen) {
    }

    public void renderScreenBg(GuiGraphics guiGraphics, int x, int y, float partialTicks, PlaneInventoryScreen screen) {
    }

    public <T> T getCap(BaseCapability<T, ?> cap) {
        return null;
    }
}
