package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntitySize;

public class HelicopterEntityType extends AbstractPlaneEntityType<HelicopterEntity> {

    public HelicopterEntityType() {
        super(HelicopterEntity::new, HelicopterEntity.FLYING_SIZE);
    }
}
