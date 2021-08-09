package xyz.przemyk.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.przemyk.simpleplanes.MathUtil;
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
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.put("banner", banner.serializeNBT());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        final Tag banner = nbt.get("banner");
        if (banner instanceof CompoundTag) {
            this.banner = ItemStack.of((CompoundTag) banner);
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        BannerModel.renderBanner(this, partialTicks, matrixStack, buffer, banner, packedLight);
    }

    @Override
    public void onApply(ItemStack itemStack, Player playerEntity) {
        if (itemStack.getItem() instanceof BannerItem) {
            banner = itemStack.copy();
            banner.setCount(1);
            updateClient();
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeItem(banner);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        banner = buffer.readItem();
    }

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(banner);
    }
}
