package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class LargePlaneEntityType extends AbstractPlaneEntityType<LargePlaneEntity> {

    public LargePlaneEntityType(boolean immuneToFire) {
        super(LargePlaneEntity::new, immuneToFire);
    }
}
