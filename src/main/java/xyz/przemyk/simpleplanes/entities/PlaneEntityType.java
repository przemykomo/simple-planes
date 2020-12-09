package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntityDimensions;

public class PlaneEntityType extends AbstractPlaneEntityType<PlaneEntity> {

    public PlaneEntityType() {
        super(PlaneEntity::new, EntityDimensions.changing(2.0f, 0.5f));
    }
}
