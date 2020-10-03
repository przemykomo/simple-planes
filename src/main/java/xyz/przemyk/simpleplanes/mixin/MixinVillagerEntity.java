package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerEntity extends PassiveEntity {

    protected MixinVillagerEntity(EntityType<? extends PassiveEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public double getHeightOffset() {
        return isBaby() ? -0.1 : -0.35D;
    }


}
