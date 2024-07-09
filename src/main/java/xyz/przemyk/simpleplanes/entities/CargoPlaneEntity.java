package xyz.przemyk.simpleplanes.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.datapack.PayloadEntry;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;
import xyz.przemyk.simpleplanes.network.DropPayloadPacket;
import xyz.przemyk.simpleplanes.network.NewCargoUpgradePacket;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;
import xyz.przemyk.simpleplanes.network.UpdateUpgradePacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.payload.PayloadUpgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CargoPlaneEntity extends PlaneEntity {

    public List<LargeUpgrade> largeUpgrades = new ArrayList<>(6);

    public CargoPlaneEntity(EntityType<? extends CargoPlaneEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = level().getEntities(this, getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
        for (Entity entity : list) {
            if (!level().isClientSide && !(getControllingPassenger() instanceof Player) &&
                    !entity.hasPassenger(this) &&
                    !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof Player)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    protected boolean tryToAddUpgrade(Player playerEntity, ItemStack itemStack) {
        if (super.tryToAddUpgrade(playerEntity, itemStack)) {
            return true;
        }
        if (largeUpgrades.size() < 6) {
            Optional<UpgradeType> upgradeTypeOptional = SimplePlanesUpgrades.getLargeUpgradeFromItem(itemStack.getItem());
            if (upgradeTypeOptional.map(upgradeType -> {
                if (canAddUpgrade(upgradeType)) {
                    Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                    addUpgrade(playerEntity, itemStack, upgrade);
                    return true;
                }
                return false;
            }).orElse(false)) {
                return true;
            }
            PayloadEntry payloadEntry = PlanePayloadReloadListener.payloadEntries.get(itemStack.getItem());
            if (payloadEntry != null) {
                addUpgrade(playerEntity, itemStack, new PayloadUpgrade(this, payloadEntry));
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        // Disabling jukebox for now since it sends a packet which is unsupported for multiple upgrades of the same type
        return upgradeType != SimplePlanesUpgrades.JUKEBOX.get() && super.canAddUpgrade(upgradeType);
    }

    @Override
    protected void addUpgrade(Player playerEntity, ItemStack itemStack, Upgrade upgrade) {
        if (!level().isClientSide) {
            upgrade.onApply(itemStack);
            if (!playerEntity.isCreative()) {
                itemStack.shrink(1);
            }
            UpgradeType upgradeType = upgrade.getType();

            if (upgrade instanceof LargeUpgrade largeUpgrade) {
                largeUpgrades.add(largeUpgrade);
                SimplePlanesNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new NewCargoUpgradePacket(SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgradeType), getId(), upgrade));
            } else {
                upgrades.put(SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgradeType), upgrade);
                if (upgradeType.isEngine) {
                    engineUpgrade = (EngineUpgrade) upgrade;
                }
                SimplePlanesNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgradeType), getId(), (ServerLevel) level(), true));
            }
        }
    }

    public void readNewCargoUpgradePacket(ResourceLocation upgradeID, FriendlyByteBuf packetBuffer) {
        UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.get().getValue(upgradeID);
        Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
        if (upgrade instanceof LargeUpgrade largeUpgrade) {
            largeUpgrades.add(largeUpgrade);
        }
        upgrade.readPacket(packetBuffer);
    }

    @Override
    public void dropPayload() {
        for (LargeUpgrade upgrade : largeUpgrades) {
            if (upgrade.canBeDroppedAsPayload()) {
                upgrade.dropAsPayload();
                if (upgrade.removed) {
                    largeUpgrades.remove(upgrade);
                }
                if (level().isClientSide) {
                    SimplePlanesNetworking.INSTANCE.sendToServer(new DropPayloadPacket());
                }
                break;
            }
        }
    }

    @Override
    public void removeUpgrade(ResourceLocation upgradeID) {
        super.removeUpgrade(upgradeID);
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.CARGO_PLANE_ITEM.get();
    }

    @Override
    protected float getGroundPitch() {
        return 0;
    }

    @Override
    public int getFuelCost() {
        return SimplePlanesConfig.CARGO_PLANE_FUEL_COST.get();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (!upgrades.containsKey(SimplePlanesUpgrades.SEATS.getId())) {
            return passengers.size() < 2;
        } else {
            return passengers.size() < 6;
        }
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        Vector3f pos = switch (index) {
            case 0 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f, 1.5f));
            case 1 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.125f, -12.0f));
            case 2 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, 2.875f));
            case 3 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, 3.75f));
            case 4 ->
                    transformPos(new Vector3f(0.6f, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, -6.25f));
            default ->
                    transformPos(new Vector3f(-0.6f, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, -6.25f));
        };

        moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return SimplePlanesConfig.CARGO_PLANE_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    @Override
    protected float getRotationSpeedMultiplier() {
        return 0.2f;
    }

    @Override
    public void openContainer(ServerPlayer player, int containerID) {
        if (containerID == 0) {
            NetworkHooks.openScreen(player, new SimpleMenuProvider((id, inventory, playerIn) ->
                    new PlaneInventoryContainer(id, inventory, this), getName()), buffer -> buffer.writeVarInt(getId()));
        } else {
            int id = 0;
            for (LargeUpgrade upgrade : largeUpgrades) {
                if (upgrade.hasStorage()) {
                    id++;
                    if (containerID == id) {
                        upgrade.openStorageGui(player, id);
                    }
                }
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("large_upgrades")) {
            ListTag listTag = compound.getList("large_upgrades", CompoundTag.TAG_COMPOUND);
            largeUpgrades.clear();
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag compoundTag = listTag.getCompound(i);
                UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.get().getValue(new ResourceLocation(compoundTag.getString("id")));
                if (upgradeType != null) {
                    Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                    if (upgrade instanceof LargeUpgrade largeUpgrade) {
                        largeUpgrade.deserializeNBT(compoundTag.getCompound("nbt"));
                        largeUpgrades.add(largeUpgrade);
                    }
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        ListTag listTag = new ListTag();
        for (LargeUpgrade upgrade : largeUpgrades) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("id", SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgrade.getType()).toString());
            compoundTag.put("nbt", upgrade.serializeNBT());
            listTag.add(compoundTag);
        }
        compound.put("large_upgrades", listTag);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        super.writeSpawnData(buffer);

        buffer.writeVarInt(largeUpgrades.size());
        for (LargeUpgrade upgrade : largeUpgrades) {
            ResourceLocation upgradeID = SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgrade.getType());
            buffer.writeResourceLocation(upgradeID);
            upgrade.writePacket(buffer);
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        super.readSpawnData(additionalData);
        int largeUpgradesSize = additionalData.readVarInt();
        for (int i = 0; i < largeUpgradesSize; i++) {
            ResourceLocation upgradeID = additionalData.readResourceLocation();
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.get().getValue(upgradeID);
            Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
            largeUpgrades.add((LargeUpgrade) upgrade);
            upgrade.readPacket(additionalData);
        }
    }
}
