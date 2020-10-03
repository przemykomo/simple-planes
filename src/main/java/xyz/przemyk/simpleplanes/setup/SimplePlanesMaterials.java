package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.paint.PaintUpgrade;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("unused")
public class SimplePlanesMaterials {
    public static final MutableRegistry<PlaneMaterial> PLANE_MATERIALS = FabricRegistryBuilder.createSimple(PlaneMaterial.class, new Identifier(SimplePlanesMod.MODID, "plane_materials")).buildAndRegister();

    public static Set<Entry<RegistryKey<PlaneMaterial>, PlaneMaterial>> getMaterials() {
        return PLANE_MATERIALS.getEntries();
    }

    public static PlaneMaterial getMaterial(Identifier name) {
        return PLANE_MATERIALS.get(name);
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


    public static void init() {
        List<String> fire = Arrays.asList(FIRE_RESISTANT);
        for (String name :
            MATERIALS) {
            register(fire, name);
        }
        PaintUpgrade.init();
        for (String name :
            PaintUpgrade.PAINTS.values()) {
            register(fire, name);
        }
        //        event.getRegistry().registerAll(materials);
    }

    public static void register(List<String> fire, String name) {
        Registry.register(PLANE_MATERIALS, new Identifier(SimplePlanesMod.MODID, name), new PlaneMaterial(name, fire.contains(name)));
    }

    public static PlaneMaterial getMaterial(String name) {
        return getMaterial(new Identifier(SimplePlanesMod.MODID, name));
    }
}