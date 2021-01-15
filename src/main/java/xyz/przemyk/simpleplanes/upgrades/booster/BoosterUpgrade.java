package xyz.przemyk.simpleplanes.upgrades.booster;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import javax.annotation.Nullable;

import static net.minecraft.item.Items.GUNPOWDER;

public class BoosterUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/rocket.png");
    public static int FUEL_PER_GUNPOWDER = 20;

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

    @Override
    public void writePacket(PacketBuffer buffer) {
        buffer.writeVarInt(fuel);
    }

    @Override
    public void readPacket(PacketBuffer buffer) {
        fuel = buffer.readVarInt();
    }

    public BoosterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BOOSTER.get(), planeEntity);
    }

    @Override
    public void tick() {
        push();
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        if (fuel <= 0) {
            if (itemStack.getItem().equals(GUNPOWDER)) {
                if (!event.getPlayer().isCreative()) {
                    itemStack.shrink(1);
                }
                fuel = FUEL_PER_GUNPOWDER;
            }
        }
        push();
    }

    private void push() {
        if (fuel < 0) {
            return;
        }

        --fuel;
        updateClient();

        Vector3d m = planeEntity.getMotion();
        float pitch = 0;
        PlayerEntity player = planeEntity.getPlayer();
        if (player != null) {
            if (player.moveForward > 0.0F) {
                if (planeEntity.isSprinting()) {
                    pitch += 2;
                }
            } else if (player.moveForward < 0.0F) {
                pitch -= 2;
            }
        }
        if (planeEntity.world.rand.nextInt(50) == 0) {
            planeEntity.attackEntityFrom(DamageSource.ON_FIRE, 1);
        }
        if (planeEntity instanceof HelicopterEntity) {
            pitch = 0;
        }
        planeEntity.rotationPitch += pitch;
        Vector3d motion = MathUtil.rotationToVector(planeEntity.rotationYaw, planeEntity.rotationPitch, 0.05);

        planeEntity.setMotion(m.add(motion));
        if (!planeEntity.world.isRemote) { //TODO: spawn particles on client, not on server
            planeEntity.spawnParticle(ParticleTypes.FLAME, new Vector3f(-0.6f, 0f, -1.3f));
            planeEntity.spawnParticle(ParticleTypes.FLAME, new Vector3f(0.6f, 0f, -1.3f));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(BoosterModel.INSTANCE.getRenderType(TEXTURE));
        BoosterModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Nullable
    @Override
    protected Item getItem() {
        return SimplePlanesItems.BOOSTER.get();
    }
}
