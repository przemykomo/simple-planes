package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {

    public final Item dropItem;
    public final ResourceLocation texture;
    public final ResourceLocation texturePowered;
    public AbstractPlaneEntityType(EntityType.IFactory<T> factory, Item dropItem, ResourceLocation texture, ResourceLocation texturePowered, boolean immuneToFire) {
        super(factory, EntityClassification.MISC, true, true, immuneToFire, true, EntitySize.flexible(2.0f, 0.5f));
        this.dropItem = dropItem;
        this.texture = texture;
        this.texturePowered = texturePowered;
    }
}
