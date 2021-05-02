package xyz.przemyk.simpleplanes.upgrades.engines.electric;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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
import xyz.przemyk.simpleplanes.container.ElectricEngineContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectricEngineUpgrade extends EngineUpgrade implements INamedContainerProvider {

    public static final int CAPACITY = 2_100_000;

    public final CustomEnergyStorage energyStorage = new CustomEnergyStorage(CAPACITY);
    public final LazyOptional<EnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public ElectricEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ELECTRIC_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (!planeEntity.getParked()) {
            if (energyStorage.extractEnergy(20 * planeEntity.getFuelCost(), false) > 0) {
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return energyStorage.getEnergyStored() > 0;
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
        //TODO: render electric engine instead of blackstone
        BlockState state = Blocks.BLACKSTONE.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
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
        energyStorage.setEnergy(nbt.getInt("energy"));
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
