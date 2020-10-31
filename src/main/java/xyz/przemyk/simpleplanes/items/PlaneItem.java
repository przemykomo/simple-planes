package xyz.przemyk.simpleplanes.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PlaneItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
    private final Function<World, PlaneEntity> planeSupplier;

    public PlaneItem(Settings properties, Function<World, PlaneEntity> planeSupplier) {
        super(properties.maxCount(1));
        this.planeSupplier = planeSupplier;
    }

    @Override
    public Text getName() {
        return super.getName();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return super.hasGlint(stack) || stack.getSubTag("EntityTag") != null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getStackInHand(handIn);
        HitResult raytraceresult = raycast(worldIn, playerIn, RaycastContext.FluidHandling.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS || worldIn.isClient) {
            return TypedActionResult.pass(itemstack);
        } else {
            Vec3d vec3d = playerIn.getRotationVec(1.0F);
            List<Entity> list = worldIn.getOtherEntities(playerIn, playerIn.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3d vec3d1 = playerIn.getCameraPosVec(1.0F);

                for (Entity entity : list) {
                    Box axisalignedbb = entity.getBoundingBox().expand(entity.getTargetingMargin());
                    if (axisalignedbb.contains(vec3d1)) {
                        return TypedActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                PlaneEntity planeEntity = planeSupplier.apply(worldIn);
                UpgradeType coalEngine = SimplePlanesUpgrades.COAL_ENGINE;
                Upgrade upgrade = coalEngine.instanceSupplier.apply(planeEntity);
//                if (itemstack.getSubTag("Used") == null) {
//                }
                planeEntity.upgrades.put(coalEngine.getRegistryName(), upgrade);
                planeEntity.upgradeChanged();

                planeEntity.updatePosition(raytraceresult.getPos().getX(), raytraceresult.getPos().getY(), raytraceresult.getPos().getZ());
                planeEntity.yaw = playerIn.yaw;
                planeEntity.prevYaw = playerIn.prevYaw;
                planeEntity.setCustomName(itemstack.getName());
                CompoundTag entityTag = itemstack.getSubTag("EntityTag");
                if (entityTag != null) {
                    planeEntity.readCustomDataFromTag(entityTag);
                }
                if (!worldIn.isSpaceEmpty(planeEntity, planeEntity.getBoundingBox().expand(-0.1D))) {
                    return TypedActionResult.fail(itemstack);
                } else {
                    if (!worldIn.isClient) {
                        worldIn.spawnEntity(planeEntity);
                        if (!playerIn.abilities.creativeMode) {
                            itemstack.decrement(1);
                        }
                    }
                    playerIn.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemstack);
                }
            } else {
                return TypedActionResult.pass(itemstack);
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack);
    }

}
