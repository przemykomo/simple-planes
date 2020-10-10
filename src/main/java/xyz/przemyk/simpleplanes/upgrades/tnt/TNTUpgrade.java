package xyz.przemyk.simpleplanes.upgrades.tnt;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.BackSeatBlockModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class TNTUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/tnt.png");

    public TNTUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.TNT, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (world.isRemote) {
            return false;
        }
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            EntityTNTPrimed tntEntity = new EntityTNTPrimed(planeEntity.world, planeEntity.getPosX() - 1.0, planeEntity.getPosY(), planeEntity.getPosZ(),
                player);
            Vec3d motion = planeEntity.getMotion();
            tntEntity.motionX = motion.x;
            tntEntity.motionY = motion.y;
            tntEntity.motionZ = motion.z;
            planeEntity.world.spawnEntity(tntEntity);
            itemStack.damageItem(1, player);
            return true;
        }

        return false;
    }

    @Override
    public void render(float partialticks, float scale) {
        BackSeatBlockModel.renderBlock(planeEntity, partialticks, scale);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
