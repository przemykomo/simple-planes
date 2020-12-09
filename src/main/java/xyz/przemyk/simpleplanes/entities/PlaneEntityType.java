package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntitySize;

public class PlaneEntityType extends AbstractPlaneEntityType<PlaneEntity> {

    public PlaneEntityType() {
        super(PlaneEntity::new, EntitySize.flexible(2.0f, 0.5f));
    }
}
