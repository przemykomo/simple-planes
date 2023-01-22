//package xyz.przemyk.simpleplanes.datapack;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import it.unimi.dsi.fastutil.objects.Object2IntMap;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
//import net.minecraft.util.GsonHelper;
//import net.minecraft.util.profiling.ProfilerFiller;
//import net.minecraftforge.fluids.FluidType;
//import net.minecraftforge.registries.ForgeRegistries;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class PlaneLiquidFuelReloadListener extends SimpleJsonResourceReloadListener {
//
//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//    private static final Logger LOGGER = LogManager.getLogger();
//
//    public static final Map<FluidType, Integer> fuelMap = new HashMap<>();
//
//    public PlaneLiquidFuelReloadListener() {
//        super(GSON, "plane_liquid_fuels");
//    }
//
//    @Override
//    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
//        fuelMap.clear();
//        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
//            try {
//                JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
//                FluidType fluidType = Objects.requireNonNull(ForgeRegistries.FLUID_TYPES.get().getValue(new ResourceLocation(jsonObject.get("fluid").getAsString())), "missing fluid type");
//                int fuelPerMb = jsonObject.get("burn_time_per_mb").getAsInt();
//
//                fuelMap.put(fluidType, fuelPerMb);
//            } catch (Exception e) {
//                LOGGER.error("Parsing error loading plane liquid fuel {}", entry.getKey(), e);
//            }
//        }
//    }
//}
