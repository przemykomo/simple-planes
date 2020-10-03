package xyz.przemyk.simpleplanes.upgrades.energy;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import static xyz.przemyk.simpleplanes.PlanesEvents.NOT_COAL_TAG;

public class FurnceJunkEngine extends AbstractEngine {

    public FurnceJunkEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SMOKER_ENGINE, planeEntity);
    }

    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (!player.world.isClient && !itemStack.isEmpty() && planeEntity.getFuel() < SimplePlanesMod.CONFIG.getConfig().FLY_TICKS_PER_COAL / 4) {
            //func_230235_a_ - contains
            Integer burnTime = FuelRegistry.INSTANCE.get(itemStack.getItem());
            if (burnTime != null && burnTime > 0) {
                int fuel = (int) ((burnTime / 1600f) * SimplePlanesMod.CONFIG.getConfig().FLY_TICKS_PER_COAL);
                if (!TagRegistry.item(NOT_COAL_TAG).contains(itemStack.getItem())) {
                    planeEntity.addFuelMaxed(fuel);
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.SMOKER);
    }
}
