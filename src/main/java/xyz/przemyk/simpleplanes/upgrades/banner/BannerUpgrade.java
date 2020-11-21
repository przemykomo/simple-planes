package xyz.przemyk.simpleplanes.upgrades.banner;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;


public class BannerUpgrade extends Upgrade {
    public ItemStack banner;
    public float rotation, prevRotation;

    public BannerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BANNER, planeEntity);
        banner = Items.WHITE_BANNER.getDefaultStack();
        prevRotation = planeEntity.prevYaw;
        rotation = planeEntity.prevYaw;
    }

    @Override
    public boolean tick() {
        prevRotation = rotation;
        rotation = MathUtil.lerpAngle(0.05f, rotation, planeEntity.prevYaw);
        return super.tick();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.put("banner", banner.getTag());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final Tag banner = nbt.get("banner");
        if (banner instanceof CompoundTag)
            this.banner = ItemStack.fromTag((CompoundTag) banner);
    }

    @Override
    public CompoundTag serializeNBTData() {
        return serializeNBT();
    }

    @Override
    public void deserializeNBTData(CompoundTag nbt) {
        deserializeNBT(nbt);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        BannerModel.renderBanner(this, partialticks, matrixStack, buffer, banner, packedLight, BannerBlockEntityRenderer.createBanner());
    }

    @Override
    public ItemStack getDrop() {
        return banner;
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        if (itemStack.getItem() instanceof BannerItem) {
            banner = itemStack.copy();
            banner.setCount(1);
            planeEntity.upgradeChanged();
        }
    }
}
