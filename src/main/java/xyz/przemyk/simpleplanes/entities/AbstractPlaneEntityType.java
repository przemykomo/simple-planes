package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public AbstractPlaneEntityType(EntityType.IFactory<T> factory) {
        super(factory, EntityClassification.MISC, true, true, false, true, EntitySize.flexible(2.0f, 0.5f));
    }
}
