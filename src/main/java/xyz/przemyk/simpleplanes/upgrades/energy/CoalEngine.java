package xyz.przemyk.simpleplanes.upgrades.energy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class CoalEngine extends AbstractEngine {
    public static final ResourceLocation COAL_TAG = new ResourceLocation("minecraft", "coals");
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine.png");
    public static final ResourceLocation TEXTURE_LIT = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/engine_lit.png");

    public CoalEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.COAL_ENGINE, planeEntity);
    }

    public CoalEngine(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (!player.world.isRemote && planeEntity.getFuel() < Config.INSTANCE.FLY_TICKS_PER_COAL / 4) {
            //func_230235_a_ - contains
            //todo ore dictionary?
            if (itemStack.getItem() == Items.COAL) {
                planeEntity.addFuelMaxed();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }

        return false;
    }


    @Override
    public void render(float partialticks, float scale) {
        EngineModel.renderEngine(planeEntity, partialticks, scale);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxFuel(Config.INSTANCE.COAL_MAX_FUEL);
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        super.onApply(itemStack, playerEntity);
        planeEntity.setMaxFuel(Config.INSTANCE.COAL_MAX_FUEL);
    }

    @Override
    public ResourceLocation getTexture() {
        return planeEntity.isPowered() ? TEXTURE_LIT : TEXTURE;
    }
}
