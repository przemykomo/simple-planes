package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.container.ModifyUpgradesContainer;
import xyz.przemyk.simpleplanes.container.StorageContainer;

import java.util.function.Supplier;

public class SimplePlanesContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        CONTAINERS.register(bus);
    }

    public static final Supplier<MenuType<PlaneWorkbenchContainer>> PLANE_WORKBENCH = CONTAINERS.register("plane_workbench", () -> new MenuType<>(PlaneWorkbenchContainer::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<ModifyUpgradesContainer>> UPGRADES_REMOVAL = CONTAINERS.register("upgrades_removal", () -> IMenuTypeExtension.create(ModifyUpgradesContainer::new));
    public static final Supplier<MenuType<StorageContainer>> STORAGE = CONTAINERS.register("storage", () -> IMenuTypeExtension.create(StorageContainer::new));
    public static final Supplier<MenuType<PlaneInventoryContainer>> PLANE_INVENTORY = CONTAINERS.register("plane_inventory", () -> IMenuTypeExtension.create(PlaneInventoryContainer::new));
}
