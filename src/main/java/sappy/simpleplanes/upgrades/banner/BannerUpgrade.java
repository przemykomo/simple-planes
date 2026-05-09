package sappy.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.misc.MathUtil;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.SPUpgrades;

public class BannerUpgrade extends Upgrade {

    public ItemStack banner;
    public float rotation, prevRotation;

    public BannerUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.BANNER.get(), planeEntity);
        banner = Items.WHITE_BANNER.getDefaultInstance();
        prevRotation = planeEntity.yRotO;
        rotation = planeEntity.yRotO;
    }

    @Override
    public void tick() {
        prevRotation = rotation;
        rotation = MathUtil.lerpAngle(0.05f, rotation, planeEntity.yRotO);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();

        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, banner).ifSuccess(tag -> {
            compoundNBT.put("banner", tag);
        });
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final Tag tag = nbt.get("banner");
        ItemStack.CODEC.parse(NbtOps.INSTANCE, tag).ifSuccess(itemStack -> {
            this.banner = itemStack;
        });
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        UpgradesModels.BANNER.renderBanner(state, poseStack, submitNodeCollector, camera, this);
    }

    @Override
    public void onApply(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BannerItem) {
            banner = itemStack.copy();
            banner.setCount(1);
            updateClient();
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, banner);
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        banner = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
    }

    @Override
    public ItemStack getItemStack() {
        return banner;
    }
}
