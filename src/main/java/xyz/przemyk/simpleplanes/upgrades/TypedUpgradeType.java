package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Function;
import java.util.function.Predicate;

public class TypedUpgradeType<T extends Item> extends UpgradeType {
    private final Class<T> tClass;

    public TypedUpgradeType(Class<T> tClass, Function<PlaneEntity, Upgrade> instanceSupplier, Predicate<PlaneEntity> isPlaneApplicable, boolean occupyBackSeat) {
        super(null, instanceSupplier, isPlaneApplicable, occupyBackSeat);
        this.tClass = tClass;
    }

    public TypedUpgradeType(Class<T> tClass,Function<PlaneEntity, Upgrade> instanceSupplier, Predicate<PlaneEntity> isPlaneApplicable) {
        super(null, instanceSupplier, isPlaneApplicable);
        this.tClass = tClass;
    }

    public TypedUpgradeType(Class<T> tClass,Function<PlaneEntity, Upgrade> instanceSupplier) {
        super(null, instanceSupplier);
        this.tClass = tClass;
    }

    @Override
    public boolean IsThisItem(Item item) {
        return item.getClass() == tClass;
    }
}
