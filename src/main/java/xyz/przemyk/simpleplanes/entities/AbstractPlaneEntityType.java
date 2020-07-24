package xyz.przemyk.simpleplanes.entities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class AbstractPlaneEntityType<T extends PlaneEntity> extends EntityType<T> {


//    public final Item dropItem;
//    public final ResourceLocation texture;
//    public final ResourceLocation texturePowered;

    public AbstractPlaneEntityType(EntityType.IFactory<T> factory, boolean immuneToFire) {
        super(factory, EntityClassification.MISC, true, true, immuneToFire, true, ImmutableSet.of(), EntitySize.flexible(2.0f, 0.5f), 5, 3);
//        this.dropItem = dropItem;
//        this.texture = texture;
//        this.texturePowered = texturePowered;
    }


}
