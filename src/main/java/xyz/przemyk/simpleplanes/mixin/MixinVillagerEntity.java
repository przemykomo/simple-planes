package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends AgeableEntity {

    protected MixinVillagerEntity(EntityType<? extends AgeableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public double getYOffset() {
        return isChild() ? -0.1 : -0.35D;
    }


}
