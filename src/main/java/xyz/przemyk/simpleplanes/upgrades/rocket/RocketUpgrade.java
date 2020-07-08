package xyz.przemyk.simpleplanes.upgrades.rocket;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static net.minecraft.item.Items.GUNPOWDER;

public class RocketUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/rocket.png");
    public static int FUEL_PER_GUNPOWDER = 15;

    private int fuel = 0;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("fuel", fuel);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
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
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        if (fuel <= 0) {
            if (itemStack.getItem().equals(GUNPOWDER)) {
                if (!event.getPlayer().isCreative()) {
                    itemStack.shrink(1);
                }
                fuel = FUEL_PER_GUNPOWDER;
                planeEntity.addFuel(FUEL_PER_GUNPOWDER * 4);
            }
        }
        push();
        return false;
    }

    private void push() {
        if (fuel < 0)
            return;
        fuel -= 1;
        planeEntity.gravity = false;

        Vec3d m = planeEntity.getMotion();
        Vec3d motion = planeEntity.getMotion();
        float pitch = PlaneEntity.getPitch(motion);
        PlayerEntity player = planeEntity.getPlayer();
        if (player != null) {
            if (player.moveForward > 0.0F) {
                pitch += 10;
                if (player.isSprinting()) {
                    pitch += 5;
                }
            } else if (player.moveForward < 0.0F) {
                pitch -= 10;
            }
        }
        motion = planeEntity.getVec(planeEntity.rotationYaw, pitch)
                .scale(0.1);
        planeEntity.setMotion(m.add(motion));

        if (!planeEntity.world.isRemote()) {

            ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.FLAME,
                    planeEntity.getPosX() + 1.2 * MathHelper.sin((planeEntity.rotationYaw - 20) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() - 1.2 * MathHelper.cos((planeEntity.rotationYaw - 20) * ((float) Math.PI / 180F)),
                    5, 0, 0, 0, 0.0);

            ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.FLAME,
                    planeEntity.getPosX() + 1.2 * MathHelper.sin((planeEntity.rotationYaw + 20) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() - 1.2 * MathHelper.cos((planeEntity.rotationYaw + 20) * ((float) Math.PI / 180F)),
                    5, 0, 0, 0, 0.0);
        }
    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RocketModel.INSTANCE.getRenderType(TEXTURE));
        RocketModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
