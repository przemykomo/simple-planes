package xyz.przemyk.simpleplanes.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public AbstractPlaneEntityType(EntityType.IFactory<T> factory, boolean immuneToFire) {
        super(factory, EntityClassification.MISC, true, true, immuneToFire, true, ImmutableSet.of(), EntitySize.flexible(2.0f, 0.5f), 5, 3);
    }


}
