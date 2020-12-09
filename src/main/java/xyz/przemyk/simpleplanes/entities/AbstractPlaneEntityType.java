package xyz.przemyk.simpleplanes.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public AbstractPlaneEntityType(EntityType.IFactory<T> factory, EntitySize size) {
        super(factory, EntityClassification.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3);
    }

}
