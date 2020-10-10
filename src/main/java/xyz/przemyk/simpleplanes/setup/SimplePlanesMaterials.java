package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
public class SimplePlanesMaterials {

    public static Set<Entry<ResourceLocation, PlaneMaterial>> getMaterials() {
        return SimplePlanesRegistries.PLANE_MATERIALS.getEntries();
    }

    public static PlaneMaterial getMaterial(ResourceLocation name) {
        return SimplePlanesRegistries.PLANE_MATERIALS.getValue(name);
    }

    public static final String[] MATERIALS = new String[]{
        ("oak"),
        ("acacia"),
        ("birch"),
        ("crimson"),
        ("dark_oak"),
        ("jungle"),
        ("spruce"),
        ("warped"),
        ("bop_cherry"),
        ("bop_dead"),
        ("bop_fir"),
        ("bop_hellbark"),
        ("bop_jacaranda"),
        ("bop_mahogany"),
        ("bop_magic"),
        ("bop_palm"),
        ("bop_redwood"),
        ("bop_umbran"),
        ("bop_willow"),

        ("byg_aspen"),
        ("byg_baobab"),
        ("byg_blue_enchanted"),
//        ("byg_bulbis"),
        ("byg_cherry"),
        ("byg_cika"),
        ("byg_cypress"),
        ("byg_ebony"),
//        ("byg_embur"),
        ("byg_fir"),
//        ("byg_glacial_oak"),
        ("byg_green_enchanted"),
        ("byg_holly"),
        ("byg_jacaranda"),
        ("byg_mahogany"),
        ("byg_mangrove"),
        ("byg_maple"),
        ("byg_pine"),
        ("byg_rainbow_eucalyptus"),
        ("byg_redwood"),
        ("byg_skyris"),
        ("byg_willow"),
        ("byg_witch_hazel"),
        ("byg_zelkova"),

        ("ft_cherry"),
        ("ft_citrus")
    };
    public static final String[] FIRE_RESISTANT = new String[]{
        ("crimson"),
        ("warped"),
        ("bop_hellbark")
    };

    @GameRegistry.ObjectHolder(SimplePlanesMod.MODID + ":oak")
    public static final PlaneMaterial OAK = null;
    //        public static final RegistryObject<PlaneMaterial> OAK =
    //                UPGRADE_TYPES.register("oak", () ->
    //                        new PlaneMaterial("oak", false));

    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<PlaneMaterial> event) {
        List<String> fire = Arrays.asList(FIRE_RESISTANT);
        for (String name :
            MATERIALS) {
            register(event, fire, name);
        }
//        PaintUpgrade.init();
//        for (String name :
//            PaintUpgrade.PAINTS.values()) {
//            register(event, fire, name);
//        }
        //        event.getRegistry().registerAll(materials);
    }

    public static void register(Register<PlaneMaterial> event, List<String> fire, String name) {
        event.getRegistry().register(new PlaneMaterial(name, fire.contains(name)).setRegistryName(new ResourceLocation(SimplePlanesMod.MODID, name)));
    }

    public static PlaneMaterial getMaterial(String name) {
        return getMaterial(new ResourceLocation(SimplePlanesMod.MODID, name));
    }
}