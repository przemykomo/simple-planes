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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static net.minecraft.item.Items.*;
import static xyz.przemyk.simpleplanes.MathUtil.getVec;

public class RocketUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/rocket.png");
    public static int FUEL_PER_GUNPOWDER = 30;

    public int fuel = 0;

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
        super(SimplePlanesUpgrades.BOOSTER.get(), planeEntity);
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
                planeEntity.addFuel(FUEL_PER_GUNPOWDER * 2);
            }
        }
        push();
        return false;
    }

    private void push() {


        if (fuel < 0)
            return;
        fuel -= 1;

        Vector3d m = planeEntity.getMotion();
        float pitch = 0;
        PlayerEntity player = planeEntity.getPlayer();
        if (player != null) {
            if (player.moveForward > 0.0F) {
                if (player.isSprinting()) {
                    pitch += 2;
                }
            } else if (player.moveForward < 0.0F) {
                pitch -= 2;
            }
        }
        planeEntity.rotationPitch+=pitch;
        Vector3d motion = getVec(planeEntity.rotationYaw, planeEntity.rotationPitch, 0.2);

        planeEntity.setMotion(m.add(motion));
        if (!planeEntity.world.isRemote()) {
            planeEntity.spawnParticle(ParticleTypes.FLAME,new Vector3f(-0.6f, 0f, -1.3f), 5);
            planeEntity.spawnParticle(ParticleTypes.FLAME,new Vector3f(0.6f, 0f, -1.3f), 5);

        }


    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RocketModel.INSTANCE.getRenderType(TEXTURE));
        RocketModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
