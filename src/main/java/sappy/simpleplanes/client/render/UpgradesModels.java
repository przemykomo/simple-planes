
package sappy.simpleplanes.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.monster.shulker.ShulkerModel;
import net.minecraft.resources.Identifier;
import sappy.simpleplanes.client.PlaneSound;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.UpgradeType;
import sappy.simpleplanes.upgrades.armor.ArmorWindowModel;
import sappy.simpleplanes.upgrades.banner.BannerModel;
import sappy.simpleplanes.upgrades.floating.WoodenCargoFloatingModel;
import sappy.simpleplanes.upgrades.seats.CargoSeatsModel;
import sappy.simpleplanes.upgrades.seats.HeliSeatsModel;
import sappy.simpleplanes.upgrades.seats.LargeSeatsModel;
import sappy.simpleplanes.upgrades.seats.SeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenCargoSeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenHeliSeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenSeatsModel;

import java.util.HashMap;

public class UpgradesModels {
    public static ShulkerModel SHULKER_FOLDING;
    public static SeatsModel SEATS;
    public static LargeSeatsModel LARGE_SEATS;
    public static CargoSeatsModel CARGO_SEATS;
    public static HeliSeatsModel HELI_SEATS;
    public static WoodenSeatsModel WOODEN_SEATS;
    public static WoodenHeliSeatsModel WOODEN_HELI_SEATS;
    public static WoodenCargoSeatsModel WOODEN_CARGO_SEATS; // rendered when seats are NOT installed
    public static WoodenCargoFloatingModel WOODEN_CARGO_FLOATING;
    public static ArmorWindowModel ARMOR_WINDOW;
    public static BannerModel BANNER;

    public static final HashMap<UpgradeType, ModelEntry> MODEL_ENTRIES = new HashMap<>();

    public record ModelEntry(EntityModel<PlaneRenderState> normal, Identifier normalTexture,
            EntityModel<PlaneRenderState> large, Identifier largeTexture,
            EntityModel<PlaneRenderState> heli, Identifier heliTexture,
            EntityModel<PlaneRenderState> cargo, Identifier cargoTexture) {
    }
}
