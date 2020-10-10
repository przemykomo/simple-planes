package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Function;

public class UpgradeType implements IForgeRegistryEntry<UpgradeType> {

    private final Item upgradeItem;
    public final Function<PlaneEntity, Upgrade> instanceSupplier;
    public final boolean occupyBackSeat;
    private ResourceLocation Id;

    /**
     * Upgrade Type Constructor
     *
     * @param upgradeItem      After right clicking with this item, stack shrinks and plane gets this upgrade.
     * @param instanceSupplier Supplier of Upgrade instances
     * @param occupyBackSeat   Upgrade occupying back seat can only be applied to large plane.
     *                         Large plane can have only 1 upgrade occupying back seat.
     */
    public UpgradeType(Item upgradeItem, Function<PlaneEntity, Upgrade> instanceSupplier, boolean occupyBackSeat) {
        this.upgradeItem = upgradeItem;
        this.instanceSupplier = instanceSupplier;
        this.occupyBackSeat = occupyBackSeat;
    }

    public UpgradeType(Item upgradeItem, Function<PlaneEntity, Upgrade> instanceSupplier) {
        this(upgradeItem, instanceSupplier, false);
    }

    public boolean IsThisItem(ItemStack itemStack) {
        return itemStack.getItem() == this.upgradeItem;
    }

    public ItemStack getDrops() {
        return upgradeItem != null ? upgradeItem.getDefaultInstance() : ItemStack.EMPTY;
    }

    public boolean isPlaneApplicable(PlaneEntity planeEntity) {
        return true;
    }

    public boolean isPlaneApplicable(LargePlaneEntity planeEntity) {
        return isPlaneApplicable((PlaneEntity) planeEntity);
    }

    public boolean isPlaneApplicable(HelicopterEntity planeEntity) {
        return isPlaneApplicable((LargePlaneEntity) planeEntity);
    }

    public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
        return isPlaneApplicable((LargePlaneEntity) planeEntity);
    }

    public ResourceLocation getId() {
        return this.Id;
    }

    @Override
    public UpgradeType setRegistryName(ResourceLocation name) {
        this.Id = name;
        return this;
    }

    public ResourceLocation getRegistryName() {
        return this.Id;
    }

    @Override
    public Class<UpgradeType> getRegistryType() {
        return UpgradeType.class;
    }

}
