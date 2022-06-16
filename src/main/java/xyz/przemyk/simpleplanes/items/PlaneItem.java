package xyz.przemyk.simpleplanes.items;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PlaneItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType;

    public PlaneItem(Properties properties, Supplier<? extends EntityType<? extends PlaneEntity>> planeEntityType) {
        super(properties.stacksTo(1));
        this.planeEntityType = planeEntityType;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag entityTag = stack.getTagElement("EntityTag");

        if (entityTag != null) {
            if (entityTag.contains("material")) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entityTag.getString("material")));
                if (block != null) {
                    tooltip.add(Component.translatable(SimplePlanesMod.MODID + ".material").append(block.getName()));
                }
            }
            if (entityTag.contains("upgrades")) {
                CompoundTag upgradesNBT = entityTag.getCompound("upgrades");
                for (String key : upgradesNBT.getAllKeys()) {
                    CompoundTag upgradeNbt = upgradesNBT.getCompound(key);
                    ResourceLocation resourceLocation = new ResourceLocation(key);
                    if (upgradeNbt.contains("desc")) {
                        tooltip.add(Component.literal(upgradeNbt.getString("desc")));
                    } else {
                        tooltip.add(Component.translatable("name." + resourceLocation.toString().replace(":", ".")));
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult hitResult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3d = playerIn.getViewVector(1.0F);
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                PlaneEntity planeEntity = planeEntityType.get().create(worldIn);

                planeEntity.setPos(hitResult.getLocation().x(), hitResult.getLocation().y(), hitResult.getLocation().z());
                planeEntity.setYRot(playerIn.getYRot());
                planeEntity.yRotO = playerIn.yRotO;
                planeEntity.setCustomName(itemstack.getHoverName());
                CompoundTag entityTag = itemstack.getTagElement("EntityTag");
                if (entityTag != null) {
                    planeEntity.readAdditionalSaveData(entityTag);
                }
                if (!worldIn.noCollision(planeEntity, planeEntity.getBoundingBox().inflate(-0.1D))) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(planeEntity);
                        if (!playerIn.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.success(itemstack);
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void fillItemCategory(CreativeModeTab itemGroup, NonNullList<ItemStack> itemStacks) {
        if (allowedIn(itemGroup)) {
            Registry.BLOCK.getTagOrEmpty(PlaneWorkbenchContainer.PLANE_MATERIALS_TAG).forEach(blockHolder -> {
                ItemStack itemStack = new ItemStack(this);
                CompoundTag itemTag = new CompoundTag();
                itemTag.putString("material", ForgeRegistries.BLOCKS.getKey(blockHolder.value()).toString());
                itemStack.addTagElement("EntityTag", itemTag);
                itemStacks.add(itemStack);
            });
        }
    }
}
