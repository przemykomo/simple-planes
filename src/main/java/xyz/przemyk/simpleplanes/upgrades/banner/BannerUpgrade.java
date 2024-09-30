package xyz.przemyk.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class BannerUpgrade extends Upgrade {

    public ItemStack banner;
    public float rotation, prevRotation;

    public BannerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BANNER.get(), planeEntity);
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
        compoundNBT.put("banner", banner.save(planeEntity.registryAccess()));
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final Tag tag = nbt.get("banner");
        if (tag instanceof CompoundTag compoundTag) {
            this.banner = ItemStack.parseOptional(planeEntity.registryAccess(), compoundTag);
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        BannerModel.renderBanner(this, partialTicks, matrixStack, buffer, banner, packedLight);
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
