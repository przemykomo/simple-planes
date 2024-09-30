package xyz.przemyk.simpleplanes.datapack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlanePayloadReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<Item, PayloadEntry> payloadEntries = new HashMap<>();

    public PlanePayloadReloadListener() {
        super(GSON, "plane_payload");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        payloadEntries.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            try {
                JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
                Item item = Objects.requireNonNull(BuiltInRegistries.ITEM.get(ResourceLocation.parse(jsonObject.get("item").getAsString())), "missing item");
                Block renderBlock = Objects.requireNonNull(BuiltInRegistries.BLOCK.get(ResourceLocation.parse((jsonObject.get("block").getAsString()))), "missing block");
                EntityType<?> dropSpawnEntity = Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(jsonObject.get("entity").getAsString())), "missing entity");
                CompoundTag compoundTag;
                if (jsonObject.has("entity_nbt")) {
                    String tag = GsonHelper.convertToString(jsonObject.get("entity_nbt"), "entity_nbt");
                    compoundTag = TagParser.parseTag(tag);
                } else {
                    compoundTag = new CompoundTag();
                }

                payloadEntries.put(item, new PayloadEntry(item, renderBlock, dropSpawnEntity, compoundTag));
            } catch (Exception e) {
                LOGGER.error("Parsing error loading plane payload {}", entry.getKey(), e);
            }
        }
    }
}
