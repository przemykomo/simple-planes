package xyz.przemyk.simpleplanes.upgrades.shooter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.Map;
import java.util.Random;

import static net.minecraft.item.Items.FIREWORK_ROCKET;
import static net.minecraft.item.Items.FIRE_CHARGE;

public class ShooterUpgrade extends Upgrade {

    public static final ShooterModel SHOOTER_MODEL = new ShooterModel();
    //TODO: different texture
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/shooter.png");
    private static final Map<Item, IShooterBehavior> SHOOTER_BEHAVIOR_MAP = Util.make(new Object2ObjectOpenHashMap<>(), (p_212564_0_) -> {
        p_212564_0_.defaultReturnValue(new IShooterBehavior() {
            @Override
            public void shoot(FurnacePlaneEntity planeEntity, ItemStack itemStack) {

            }
        });
    });


    private boolean shootSide = false;

    public ShooterUpgrade(FurnacePlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER_UPGRADE_TYPE.get(), planeEntity);
    }

    @Override
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = player.getHeldItem(event.getHand());
        Vector3d motion = planeEntity.getMotion();
        World world = event.getWorld();
        Random random = world.rand;
        Vector2f front = this.planeEntity.getHorizontalFrontPos();
        float pitch = FurnacePlaneEntity.getPitch(motion);
        motion = planeEntity.getVec(planeEntity.rotationYaw, pitch)
                .scale(Math.max(0.25, motion.length()));
        double x = planeEntity.getPosX() + 1 * front.x;
        double z = planeEntity.getPosZ() + 1 * front.y;
        double y = planeEntity.getPosY() + 0.5;
        if (shootSide) {
            z += 1 * front.x;
            x += 1 * front.y;
        } else {
            z -= 1 * front.x;
            x -= 1 * front.y;
        }
        shootSide = !shootSide;
        Item item = itemStack.getItem();
        if (item.equals(FIREWORK_ROCKET)) {
            shootSide = !shootSide;
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, itemStack,
                    x, y, z, true);
            fireworkrocketentity.shoot(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            world.addEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        }
        if (item.equals(FIRE_CHARGE)) {

            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            AbstractFireballEntity fireBallEntity = Util.make(new SmallFireballEntity(world, player, d3, d4, d5), (p_229425_1_) -> p_229425_1_.setStack(itemStack));
            fireBallEntity.forceSetPosition(x, y, z);
            fireBallEntity.setMotion(motion.scale(2));
            world.addEntity(fireBallEntity);

        }
        if (item.equals(Items.ARROW)) {
            ArrowEntity arrowentity = new ArrowEntity(world, x, y, z);
            arrowentity.setShooter(player);
            arrowentity.setMotion(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            }
            world.addEntity(arrowentity);
        }
        if (item.equals(Items.TIPPED_ARROW)) {
            ArrowEntity arrowentity = new ArrowEntity(world, x, y, z);
            arrowentity.setShooter(player);
            arrowentity.setPotionEffect(itemStack);
            arrowentity.setMotion(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            }
            world.addEntity(arrowentity);
        }
        if (item.equals(Items.SPECTRAL_ARROW)) {
            SpectralArrowEntity arrowentity = new SpectralArrowEntity(world, x, y, z);
            arrowentity.setShooter(player);
            arrowentity.setMotion(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
            }
            world.addEntity(arrowentity);
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(SHOOTER_MODEL.getRenderType(TEXTURE));
        SHOOTER_MODEL.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
