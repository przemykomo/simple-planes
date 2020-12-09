package xyz.przemyk.simpleplanes.upgrades.energy;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import static xyz.przemyk.simpleplanes.PlanesEvents.NOT_COAL_TAG;

public final class CoalEngine extends AbstractEngine {
    public static final Identifier COAL_TAG = new Identifier("minecraft", "coals");

    public CoalEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.COAL_ENGINE, planeEntity);
    }


    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (!player.world.isClient && planeEntity.getFuel() < SimplePlanesMod.CONFIG.getConfig().COAL_MAX_FUEL) {
            //func_230235_a_ - contains
            Integer burnTime = FuelRegistry.INSTANCE.get(itemStack.getItem());
            if (burnTime != null && burnTime > 0) {
                int fuel = (int) ((burnTime / 1600f) * SimplePlanesMod.CONFIG.getConfig().FLY_TICKS_PER_COAL);
                if (!TagRegistry.item(NOT_COAL_TAG).contains(itemStack.getItem())
                    || !(itemStack.getItem() instanceof BucketItem)) {
                    planeEntity.addFuel(fuel);
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                        if (itemStack.isEmpty() && itemStack.getItem().hasRecipeRemainder()) {
                            player.setStackInHand(hand, new ItemStack(itemStack.getItem().getRecipeRemainder()));
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.FURNACE);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxFuel(SimplePlanesMod.CONFIG.getConfig().COAL_MAX_FUEL);
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        super.onApply(itemStack, playerEntity);
        planeEntity.setMaxFuel(SimplePlanesMod.CONFIG.getConfig().COAL_MAX_FUEL);
    }

}
