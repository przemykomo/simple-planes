package xyz.przemyk.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static net.minecraft.item.Items.WHITE_BANNER;

public class BannerUpgrade extends Upgrade {
    public static final BannerModel BANNER_MODEL = new BannerModel();
    public ItemStack banner;

    public BannerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BANNER.get(), planeEntity);
        banner = WHITE_BANNER.getDefaultInstance();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("banner",banner.serializeNBT());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final INBT banner = nbt.get("banner");
        if (banner instanceof CompoundNBT)
            this.banner =ItemStack.read((CompoundNBT) banner);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        BannerModel.renderBanner(planeEntity,partialticks,matrixStack,buffer,banner,packedLight, BannerTileEntityRenderer.func_228836_a_());
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        if (itemStack.getItem() instanceof BannerItem) {
            banner = itemStack;
            planeEntity.upgradeChanged();
        }
    }
}
