package xyz.przemyk.simpleplanes.upgrades.engines.electric;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.CustomEnergyStorage;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.ClientEventHandler;
import xyz.przemyk.simpleplanes.container.ElectricEngineContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectricEngineUpgrade extends EngineUpgrade implements INamedContainerProvider {

    public static final int CAPACITY = 480_000;

    public final CustomEnergyStorage energyStorage = new CustomEnergyStorage(CAPACITY);
    public final LazyOptional<EnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public ElectricEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ELECTRIC_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (!planeEntity.getParked()) {
            if (energyStorage.extractEnergy(40 * planeEntity.getFuelCost(), false) > 0) {
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return energyStorage.getEnergyStored() > 0;
    }

    @Override
    public void renderPowerHUD(MatrixStack matrixStack, HandSide side, int scaledWidth, int scaledHeight, float partialTicks) {
        int i = scaledWidth / 2;
        if (side == HandSide.LEFT) {
            ClientEventHandler.blit(matrixStack, -90, i - 91 - 29, scaledHeight - 22, 38, 44, 22, 21);
        } else {
            ClientEventHandler.blit(matrixStack, -90, i + 91, scaledHeight - 22, 38, 44, 22, 21);
        }

        int energy = energyStorage.getEnergyStored();

        if (energy > 0) {
            int energyScaled = energy * 15 / CAPACITY;
            if (side == HandSide.LEFT) {
                ClientEventHandler.blit(matrixStack, -90, i - 91 - 29 + 3, scaledHeight - 22 + 16 - energyScaled, 60, 57 - energyScaled, 16, energyScaled + 2);
            } else {
                ClientEventHandler.blit(matrixStack, -90, i + 91 + 3, scaledHeight - 22 + 16 - energyScaled, 60, 57 - energyScaled, 16, energyScaled + 2);
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.8, 0.65);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 1.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        Minecraft minecraft = Minecraft.getInstance();
        IBakedModel ibakedmodel = minecraft.getItemRenderer().getItemModelShaper().getItemModel(SimplePlanesItems.ELECTRIC_ENGINE.get());
        float f = (float)(-1 >> 16 & 255) / 255.0F;
        float f1 = (float)(-1 >> 8 & 255) / 255.0F;
        minecraft.getBlockRenderer().getModelRenderer().renderModel(matrixStack.last(), buffer.getBuffer(Atlases.cutoutBlockSheet()), null, ibakedmodel, f, f1, 1.0F, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

        matrixStack.popPose();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("energy", energyStorage.getEnergyStored());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        int energy = nbt.getInt("energy");
        energyStorage.setEnergy(Math.min(energy, CAPACITY));
    }

    @Override
    public void writePacket(PacketBuffer buffer) {
        buffer.writeVarInt(energyStorage.getEnergyStored());
    }

    @Override
    public void readPacket(PacketBuffer buffer) {
        energyStorage.setEnergy(buffer.readVarInt());
    }

    @Override
    public boolean canOpenGui() {
        return true;
    }

    @Override
    public void openGui(ServerPlayerEntity playerEntity) {
        NetworkHooks.openGui(playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ".electric_engine_container");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ElectricEngineContainer(id, playerInventory, new IntReferenceHolder() {
            @Override
            public int get() {
                return energyStorage.getEnergyStored();
            }

            @Override
            public void set(int value) {}
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(SimplePlanesItems.ELECTRIC_ENGINE.get());
    }
}
