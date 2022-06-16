package xyz.przemyk.simpleplanes.upgrades.shooter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.SpectralArrow;

public class ShooterUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/shooter.png");

    private boolean shootSide = false;

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER.get(), planeEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand(event.getHand());
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, 0, (float) (1 + planeEntity.getDeltaMovement().length())));
        Vec3 motion = new Vec3(motion1);
        Level world = event.getWorld();
        Random random = world.random;

        Vector3f pos = planeEntity.transformPos(new Vector3f(shootSide ? 0.8f : -0.8f, 0.8f, 0.8f));
        shootSide = !shootSide;
        updateClient();

        double x = pos.x() + planeEntity.getX();
        double y = pos.y() + planeEntity.getY();
        double z = pos.z() + planeEntity.getZ();

        Item item = itemStack.getItem();

        if (item == Items.FIREWORK_ROCKET) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, itemStack, x, y, z, true);
            fireworkrocketentity.shoot(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            world.addFreshEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == Items.FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            Fireball fireBallEntity = Util
                .make(new SmallFireball(world, player, d3, d4, d5), (p_229425_1_) -> p_229425_1_.setItem(itemStack));
            fireBallEntity.setPos(x, y, z);
            fireBallEntity.setDeltaMovement(motion.scale(2));
            world.addFreshEntity(fireBallEntity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == Items.ARROW) {
            Arrow arrowEntity = new Arrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        } else if (item == Items.TIPPED_ARROW) {
            Arrow arrowEntity = new Arrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setEffectsFromItem(itemStack);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        } else if (item == Items.SPECTRAL_ARROW) {
            SpectralArrow arrowEntity = new SpectralArrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        VertexConsumer vertexConsumer = buffer.getBuffer(UpgradesModels.SHOOTER.renderType(TEXTURE));
        UpgradesModels.SHOOTER.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeBoolean(shootSide);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        shootSide = buffer.readBoolean();
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(Items.DISPENSER);
    }
}
