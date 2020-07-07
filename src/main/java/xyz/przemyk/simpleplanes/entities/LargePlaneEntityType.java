package xyz.przemyk.simpleplanes.entities;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class LargePlaneEntityType extends AbstractPlaneEntityType<LargePlaneEntity> {

    public LargePlaneEntityType(Item dropItem, ResourceLocation texture, ResourceLocation texturePowered, boolean immuneToFire) {
        super(LargePlaneEntity::new, dropItem, texture, texturePowered, immuneToFire);
    }
}
