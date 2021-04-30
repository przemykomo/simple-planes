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

    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    private final Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType;

    public PlaneItem(Properties properties, Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType) {
        super(properties.stacksTo(1));
        this.planeEntityType = planeEntityType;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundNBT entityTag = stack.getTagElement("EntityTag");

        if (entityTag != null) {
            if (entityTag.contains("material")) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entityTag.getString("material")));
                if (block != null) {
                    tooltip.add(new TranslationTextComponent(SimplePlanesMod.MODID + ".material").append(block.getName()));
                }
            }
            if (entityTag.contains("upgrades")) {
                CompoundNBT upgradesNBT = entityTag.getCompound("upgrades");
                for (String key : upgradesNBT.getAllKeys()) {
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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else {
            Vector3d vec3d = playerIn.getViewVector(1.0F);
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vec3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                PlaneEntity planeEntity = planeEntityType.get().create(worldIn);

                planeEntity.setPos(raytraceresult.getLocation().x(), raytraceresult.getLocation().y(), raytraceresult.getLocation().z());
                planeEntity.yRot = playerIn.yRot;
                planeEntity.yRotO = playerIn.yRotO;
                planeEntity.setCustomName(itemstack.getHoverName());
                CompoundNBT entityTag = itemstack.getTagElement("EntityTag");
                if (entityTag != null) {
                    planeEntity.readAdditionalSaveData(entityTag);
                }
                if (!worldIn.noCollision(planeEntity, planeEntity.getBoundingBox().inflate(-0.1D))) {
                    return ActionResult.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(planeEntity);
                        if (!playerIn.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.success(itemstack);
                }
            } else {
                return ActionResult.pass(itemstack);
            }
        }
    }
}
