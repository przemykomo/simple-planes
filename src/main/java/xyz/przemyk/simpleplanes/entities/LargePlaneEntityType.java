package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntitySize;

public class LargePlaneEntityType extends AbstractPlaneEntityType<LargePlaneEntity> {

    public LargePlaneEntityType() {
        super(LargePlaneEntity::new, LargePlaneEntity.FLYING_SIZE);
    }
}
