package xyz.przemyk.simpleplanes.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.datapack.PayloadEntry;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;
import xyz.przemyk.simpleplanes.network.DropPayloadPacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.payload.PayloadUpgrade;

import java.util.List;
import java.util.Optional;

public class LargePlaneEntity extends PlaneEntity {

    public boolean hasLargeUpgrade = false;

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityType, Level level) {
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
    public boolean tryToAddUpgrade(Player playerEntity, ItemStack itemStack) {
        if (super.tryToAddUpgrade(playerEntity, itemStack)) {
            return true;
        }
        if (!hasLargeUpgrade && getPassengers().size() < 2) {
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
    protected float getGroundPitch() {
        return 0;
    }

    @Override
    public int getFuelCost() {
        return SimplePlanesConfig.LARGE_PLANE_FUEL_COST.get();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (passenger.getVehicle() == this || passenger instanceof PlaneEntity) {
            return false;
        }
        if (!upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.SEATS.get()))) {
            return passengers.size() <= 1 && (passengers.isEmpty() || !hasLargeUpgrade);
        } else {
            return hasLargeUpgrade ? passengers.size() < 3 : passengers.size() < 4;
        }
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        if (index == 0) {
            Vector3f pos = transformPos(new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), 1));
            moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        } else {
            if (hasLargeUpgrade) {
                index++;
            }
            switch (index) {
                case 1 -> {
                    Vector3f pos = transformPos(new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), 0));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 2 -> {
                    Vector3f pos = transformPos(new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), -1));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 3 -> {
                    Vector3f pos = transformPos(new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), -1.8f));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
            }
        }
    }

    public float getEntityYOffset(Entity passenger) {
        if (passenger instanceof Villager) {
            return ((Villager) passenger).isBaby() ? -0.1f : -0.3f;
        }
        return -0.4f;
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return SimplePlanesConfig.LARGE_PLANE_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.LARGE_PLANE_ITEM.get();
    }

    public boolean hasStorageUpgrade() {
        if (hasLargeUpgrade) {
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade instanceof LargeUpgrade largeUpgrade) {
                    return largeUpgrade.hasStorage();
                }
            }
        }

        return false;
    }

    @Override
    public void dropPayload() {
        for (Upgrade upgrade : upgrades.values()) {
            if (upgrade.canBeDroppedAsPayload()) {
                upgrade.dropAsPayload();
                if (upgrade.removed) {
                    upgrades.remove(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgrade.getType()));
                }
                if (level().isClientSide) {
                    PacketDistributor.sendToServer(new DropPayloadPacket());
                }
                break;
            }
        }
    }

    @Override
    protected float getRotationSpeedMultiplier() {
        return 0.5f;
    }
}
