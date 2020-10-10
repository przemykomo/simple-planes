package xyz.przemyk.simpleplanes.upgrades.banner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;


public class BannerUpgrade extends Upgrade {
    public ItemStack banner;
    public float rotation, prevRotation;

    public BannerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BANNER, planeEntity);
        banner = ForgeRegistries.ITEMS.getValue(new ResourceLocation("banner")).getDefaultInstance();
        prevRotation = planeEntity.prevRotationYaw;
        rotation = planeEntity.prevRotationYaw;
    }

    @Override
    public boolean tick() {
        prevRotation = rotation;
        rotation = MathUtil.lerpAngle(0.05f, rotation, planeEntity.prevRotationYaw);
        return super.tick();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compoundNBT = new NBTTagCompound();
        compoundNBT.setTag("banner", banner.serializeNBT());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTBase banner = nbt.getTag("banner");
        if (banner instanceof NBTTagCompound)
            this.banner =new ItemStack((NBTTagCompound) banner);
    }

    @Override
    public NBTTagCompound serializeNBTData() {
        return serializeNBT();
    }

    @Override
    public void deserializeNBTData(NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    @Override
    public void render(float partialticks, float scale) {
        BannerModel.renderBanner(this,partialticks,banner);
    }

    @Override
    public ItemStack getDrop() {
        return banner;
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        if (itemStack.getItem() instanceof ItemBanner) {
            banner = itemStack.copy();
            banner.setCount(1);
            planeEntity.upgradeChanged();
        }
    }
}
