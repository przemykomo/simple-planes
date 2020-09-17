package xyz.przemyk.simpleplanes.upgrades.energy;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.Map;

public class CoalEngine extends Upgrade {
    public static final ResourceLocation COAL_TAG = new ResourceLocation("minecraft", "coals");

    public CoalEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.COAL_ENGINE.get(), planeEntity);
    }

    public CoalEngine(UpgradeType upgradeType, PlaneEntity planeEntity) {
        super(upgradeType, planeEntity);
    }

    @Override
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = event.getItemStack();
        if (!player.world.isRemote && planeEntity.getFuel() < Config.FLY_TICKS_PER_COAL.get() / 4) {
            //func_230235_a_ - contains
            if (ItemTags.getCollection().func_241834_b(COAL_TAG).contains(itemStack.getItem())) {
                planeEntity.addFuelMaxed();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }

        return false;
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
//        if (playerEntity.world.isRemote) {
//            return;
//        }
        for (Map.Entry<ResourceLocation, Upgrade> entry : planeEntity.upgrades.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            Upgrade upgrade = entry.getValue();
            if (upgrade.isEngine() && resourceLocation != this.getType().getRegistryName()) {
                planeEntity.upgrades.remove(resourceLocation);
                planeEntity.entityDropItem(upgrade.getDrops());
                planeEntity.upgradeChanged();
                return;
            }
        }
        planeEntity.setFuel(0);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.FURNACE);
    }

    @Override
    public boolean isEngine() {
        return true;
    }
}
