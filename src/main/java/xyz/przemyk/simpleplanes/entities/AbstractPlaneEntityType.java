package xyz.przemyk.simpleplanes.entities;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public AbstractPlaneEntityType(EntityType.EntityFactory<T> factory) {
        super(factory, SpawnGroup.MISC, true, true, false, true, ImmutableSet.of(), EntityDimensions.changing(2.0f, 0.5f), 5, 3);
    }


}
