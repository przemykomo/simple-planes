package xyz.przemyk.simpleplanes.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public AbstractPlaneEntityType(EntityType.EntityFactory<T> factory, EntityDimensions size) {
        super(factory, SpawnGroup.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3);
    }

}
