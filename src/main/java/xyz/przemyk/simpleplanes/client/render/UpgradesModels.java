package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorWindowModel;
import xyz.przemyk.simpleplanes.upgrades.seats.*;

import java.util.HashMap;

public class UpgradesModels {
    public static ShulkerModel<?> SHULKER_FOLDING;
    public static SeatsModel SEATS;
    public static LargeSeatsModel LARGE_SEATS;
    public static HeliSeatsModel HELI_SEATS;
    public static WoodenSeatsModel WOODEN_SEATS;
    public static WoodenHeliSeatsModel WOODEN_HELI_SEATS;
    public static ArmorWindowModel ARMOR_WINDOW;

    public static final HashMap<UpgradeType, ModelEntry> MODEL_ENTRIES = new HashMap<>();

    public record ModelEntry(EntityModel<PlaneEntity> normal, ResourceLocation normalTexture,
                             EntityModel<PlaneEntity> large, ResourceLocation largeTexture,
                             EntityModel<PlaneEntity> heli, ResourceLocation heliTexture) {}
}
