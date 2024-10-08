package xyz.przemyk.simpleplanes.upgrades.heal;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class HealingUpgrade extends Upgrade {

    public HealingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.HEALING.get(), planeEntity);
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
        cooldown = compoundNBT.getInt("cooldown");
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
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {}

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
    public void writePacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.HEALING.get().getDefaultInstance();
    }
}
