package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.container.StorageContainer;

public class SimplePlanesContainers {

    public static void init() {
    }

    public static final MenuType<PlaneWorkbenchContainer> PLANE_WORKBENCH = register("plane_workbench", new MenuType<>(PlaneWorkbenchContainer::new));
//    public static final MenuType<RemoveUpgradesContainer> UPGRADES_REMOVAL = register("upgrades_removal", IForgeMenuType.create(RemoveUpgradesContainer::new));
    public static final MenuType<StorageContainer> STORAGE = register("storage", new ExtendedScreenHandlerType<>(StorageContainer::new));
    public static final MenuType<PlaneInventoryContainer> PLANE_INVENTORY = register("plane_inventory", new ExtendedScreenHandlerType<>(PlaneInventoryContainer::new));

    private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType<T> container) {
        return Registry.register(Registry.MENU, new ResourceLocation(SimplePlanesMod.MODID, id), container);
    }
}
