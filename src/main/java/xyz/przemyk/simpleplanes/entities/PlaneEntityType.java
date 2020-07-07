package xyz.przemyk.simpleplanes.entities;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class PlaneEntityType extends AbstractPlaneEntityType<PlaneEntity> {

    public PlaneEntityType(Item dropItem, ResourceLocation texture, ResourceLocation texturePowered, boolean immuneToFire) {
        super(PlaneEntity::new, dropItem, texture, texturePowered, immuneToFire);
    }
}
