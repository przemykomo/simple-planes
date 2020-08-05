package xyz.przemyk.simpleplanes.setup;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;

import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.paint.PaintUpgrade;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesMaterials
{

    public static Set<Entry<ResourceLocation, PlaneMaterial>> getMaterials()
    {
        return SimplePlanesRegistries.PLANE_MATERIALS.getEntries();
    }
    public static PlaneMaterial getMaterial(ResourceLocation name)
    {
        return SimplePlanesRegistries.PLANE_MATERIALS.getValue(name);
    }

    public static final String[] MATERIALS = new String[] {
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
             ("ft_cherry"),
             ("ft_citrus")
    };
    public static final String[] FIRE_RESISTANT = new String[] {
            ("crimson"),
            ("warped"),
            ("bop_hellbark")
    };

    @ObjectHolder(SimplePlanesMod.MODID+":oak")
    public static final PlaneMaterial OAK=null;
//        public static final RegistryObject<PlaneMaterial> OAK =
//                UPGRADE_TYPES.register("oak", () ->
//                        new PlaneMaterial("oak", false));


    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<PlaneMaterial> event)
    {
        List<String> fire = Arrays.asList(FIRE_RESISTANT);
        for (String name :
                MATERIALS)
        {
            register(event, fire, name);
        }
        PaintUpgrade.init();
        for (String name :
                PaintUpgrade.PAINTS.values())
        {
            register(event, fire, name);
        }
//        event.getRegistry().registerAll(materials);
    }

    private static void register(Register<PlaneMaterial> event, List<String> fire, String name)
    {
        event.getRegistry().register(new PlaneMaterial(name,fire.contains(name)).setRegistryName(SimplePlanesMod.MODID,name));
    }

    public static PlaneMaterial getMaterial(String name)
    {
        return getMaterial(new ResourceLocation(SimplePlanesMod.MODID,name));
    }
}