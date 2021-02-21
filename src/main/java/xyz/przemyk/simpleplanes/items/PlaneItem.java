package xyz.przemyk.simpleplanes.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PlaneItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType;

    public PlaneItem(Properties properties, Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType) {
        super(properties.maxStackSize(1));
        this.planeEntityType = planeEntityType;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT entityTag = stack.getChildTag("EntityTag");

        if (entityTag != null) {
            if (entityTag.contains("material")) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entityTag.getString("material")));
                if (block != null) {
                    tooltip.add(new TranslationTextComponent(SimplePlanesMod.MODID + ".material").append(block.getTranslatedName()));
                }
            }
            if (entityTag.contains("upgrades")) {
                CompoundNBT upgradesNBT = entityTag.getCompound("upgrades");
                for (String key : upgradesNBT.keySet()) {
                    CompoundNBT upgradeNbt = upgradesNBT.getCompound(key);
                    ResourceLocation resourceLocation = new ResourceLocation(key);
                    if (upgradeNbt.contains("desc")) {
                        tooltip.add(new StringTextComponent(upgradeNbt.getString("desc")));
                    } else {
                        tooltip.add(new TranslationTextComponent("name." + resourceLocation.toString().replace(":", ".")));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else {
            Vector3d vec3d = playerIn.getLook(1.0F);
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vec3d1)) {
                        return ActionResult.resultPass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                PlaneEntity planeEntity = planeEntityType.get().create(worldIn);

                planeEntity.setPosition(raytraceresult.getHitVec().getX(), raytraceresult.getHitVec().getY(), raytraceresult.getHitVec().getZ());
                planeEntity.rotationYaw = playerIn.rotationYaw;
                planeEntity.prevRotationYaw = playerIn.prevRotationYaw;
                planeEntity.setCustomName(itemstack.getDisplayName());
                CompoundNBT entityTag = itemstack.getChildTag("EntityTag");
                if (entityTag != null) {
                    planeEntity.readAdditional(entityTag);
                }
                if (!worldIn.hasNoCollisions(planeEntity, planeEntity.getBoundingBox().grow(-0.1D))) {
                    return ActionResult.resultFail(itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.addEntity(planeEntity);
                        if (!playerIn.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                    }
                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return ActionResult.resultSuccess(itemstack);
                }
            } else {
                return ActionResult.resultPass(itemstack);
            }
        }
    }
}
