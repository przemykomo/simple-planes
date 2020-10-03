package xyz.przemyk.simpleplanes.upgrades.shooter;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.Random;

public class ShooterUpgrade extends Upgrade {
    public static final Identifier TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/shooter.png");

    private boolean shootSide = false;

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER, planeEntity);
    }
    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, 0, (float) (1 + planeEntity.getVelocity().length())));
        Vec3d motion = new Vec3d(motion1);
        Random random = world.random;

        Vector3f pos = planeEntity.transformPos(new Vector3f(shootSide ? 0.8f : -0.8f, 0.8f, 0.8f));
        shootSide = !shootSide;

        double x = pos.getX() + planeEntity.getX();
        double y = pos.getY() + planeEntity.getY();
        double z = pos.getZ() + planeEntity.getZ();

        Item item = itemStack.getItem();

        if (item == Items.FIREWORK_ROCKET) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, itemStack, x, y, z, true);
            fireworkrocketentity.setVelocity(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            world.spawnEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
        } else if (item == Items.FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            AbstractFireballEntity fireBallEntity = Util
                .make(new SmallFireballEntity(world, player, d3, d4, d5), (p_229425_1_) -> p_229425_1_.setItem(itemStack));
            fireBallEntity.resetPosition(x, y, z);
            fireBallEntity.setVelocity(motion.multiply(2));
            world.spawnEntity(fireBallEntity);
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
        } else if (item == Items.ARROW) {
            ArrowEntity arrowentity = new ArrowEntity(world, x, y, z);
            arrowentity.setOwner(player);
            arrowentity.setVelocity(motion.multiply(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.decrement(1);
                arrowentity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(arrowentity);
        } else if (item == Items.TIPPED_ARROW) {
            ArrowEntity arrowEntity = new ArrowEntity(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.initFromStack(itemStack);
            arrowEntity.setVelocity(motion.multiply(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.decrement(1);
                arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(arrowEntity);
        } else if (item == Items.SPECTRAL_ARROW) {
            SpectralArrowEntity arrowEntity = new SpectralArrowEntity(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setVelocity(motion.multiply(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.decrement(1);
                arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(arrowEntity);
        }
        return false;
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        VertexConsumer ivertexbuilder = buffer.getBuffer(ShooterModel.INSTANCE.getLayer(TEXTURE));
        ShooterModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
