package sappy.simpleplanes.upgrades.heal;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import net.minecraft.nbt.CompoundTag;
import sappy.simpleplanes.SPUpgrades;

public class HealingUpgrade extends Upgrade {

    public HealingUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.HEALING.get(), planeEntity);
    }

    private int cooldown = 10;

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("cooldown", cooldown);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundNBT) {
        compoundNBT.getInt("cooldown").ifPresent(c -> cooldown = c);
    }

    @Override
    public void tick() {
        if (cooldown > 0) {
            --cooldown;
        } else {
            remove();
        }
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
    }

    @Override
    public void onApply(ItemStack itemStack) {
        int health = planeEntity.getHealth();
        int m = planeEntity.getMaxHealth() * 2;
        if (health < m) {
            int heal = planeEntity.getOnGround() ? 2 : 1;
            planeEntity.setHealth(Math.min(health + heal, m));
        }
        planeEntity.goldenHeartsTimeout = 0;
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.HEALING.get().getDefaultInstance();
    }
}
