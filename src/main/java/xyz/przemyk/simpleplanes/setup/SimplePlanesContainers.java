package xyz.przemyk.simpleplanes.setup;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.*;

public class SimplePlanesContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SimplePlanesMod.MODID);

    public static void init() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<MenuType<PlaneWorkbenchContainer>> PLANE_WORKBENCH = CONTAINERS.register("plane_workbench", () -> new MenuType<>(PlaneWorkbenchContainer::new));
    public static final RegistryObject<MenuType<RemoveUpgradesContainer>> UPGRADES_REMOVAL = CONTAINERS.register("upgrades_removal", () -> IForgeMenuType.create(RemoveUpgradesContainer::new));
    public static final RegistryObject<MenuType<StorageContainer>> STORAGE = CONTAINERS.register("storage", () -> IForgeMenuType.create(StorageContainer::new));

    public static final RegistryObject<MenuType<FurnaceEngineContainer>> FURNACE_ENGINE = CONTAINERS.register("furnace_engine", () -> new MenuType<>(FurnaceEngineContainer::new));
    public static final RegistryObject<MenuType<ElectricEngineContainer>> ELECTRIC_ENGINE = CONTAINERS.register("electric_engine", () -> IForgeMenuType.create(ElectricEngineContainer::new));
}
