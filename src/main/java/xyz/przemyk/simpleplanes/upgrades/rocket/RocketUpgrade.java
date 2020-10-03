package xyz.przemyk.simpleplanes.upgrades.rocket;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

public class RocketUpgrade extends Upgrade {
    public static final Identifier TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/rocket.png");
    public static int FUEL_PER_GUNPOWDER = 20;

    public int fuel = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("fuel", fuel);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundNBT) {
        fuel = compoundNBT.getInt("fuel");
    }

    public RocketUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BOOSTER, planeEntity);
    }

    @Override
    public boolean tick() {
        push();
        return super.tick();
    }


    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (fuel <= 0) {
            if (itemStack.getItem().equals(Items.GUNPOWDER)) {
                if (!player.isCreative()) {
                    itemStack.decrement(1);
                }
                fuel = FUEL_PER_GUNPOWDER;
                planeEntity.addFuelMaxed(FUEL_PER_GUNPOWDER * 2);
            }
        }
        push();
        return false;
    }

    private void push() {
        if (fuel < 0) {
            return;
        }

        fuel -= 1;

        Vec3d m = planeEntity.getVelocity();
        float pitch = 0;
        PlayerEntity player = planeEntity.getPlayer();
        if (player != null) {
            if (player.forwardSpeed > 0.0F) {
                if (planeEntity.isSprinting()) {
                    pitch += 2;
                }
            } else if (player.forwardSpeed < 0.0F) {
                pitch -= 2;
            }
        }
        if (planeEntity.world.random.nextInt(50) == 0) {
            planeEntity.damage(DamageSource.ON_FIRE, 1);
        }
        if (planeEntity instanceof HelicopterEntity) {
            pitch = 0;
        }
        planeEntity.pitch += pitch;
        Vec3d motion = MathUtil.rotationToVector(planeEntity.yaw, planeEntity.pitch, 0.05);

        planeEntity.setVelocity(m.add(motion));
        if (!planeEntity.world.isClient()) {
            planeEntity.spawnParticle(ParticleTypes.FLAME, new Vector3f(-0.6f, 0f, -1.3f), 5);
            planeEntity.spawnParticle(ParticleTypes.FLAME, new Vector3f(0.6f, 0f, -1.3f), 5);

        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        VertexConsumer ivertexbuilder = buffer.getBuffer(RocketModel.INSTANCE.getLayer(TEXTURE));
        RocketModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
