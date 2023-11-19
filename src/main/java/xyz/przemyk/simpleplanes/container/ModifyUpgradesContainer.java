package xyz.przemyk.simpleplanes.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import xyz.przemyk.simpleplanes.container.slots.PlaneUpgradeSlot;
import xyz.przemyk.simpleplanes.datapack.PayloadEntry;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.payload.PayloadUpgrade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModifyUpgradesContainer extends AbstractContainerMenu {

    public final ItemStackHandler itemHandler;
    public PlaneEntity planeEntity;
    public int errorSlot = -1;
    private boolean internalItemsChange;

    @SuppressWarnings("unused")
    public ModifyUpgradesContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readVarInt());
    }

    @SuppressWarnings("PatternVariableHidesField")
    public ModifyUpgradesContainer(int id, Inventory playerInventory, int planeID) {
        super(SimplePlanesContainers.UPGRADES_REMOVAL.get(), id);
        internalItemsChange = true;
        this.itemHandler = new ItemStackHandler(6) {

            @Override
            public void setStackInSlot(int slot, @NotNull ItemStack stack) {
                validateSlotIndex(slot);
                itemsChanged(stacks.get(slot), stack, slot);
                stacks.set(slot, stack);
                onContentsChanged(slot);
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (simulate) {
                    return super.extractItem(slot, amount, simulate);
                } else {
                    ItemStack previousStack = stacks.get(slot).copy();
                    ItemStack result = super.extractItem(slot, amount, simulate);
                    ItemStack newStack = stacks.get(slot);
                    itemsChanged(previousStack, newStack, slot);
                    return result;
                }
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (simulate) {
                    return super.insertItem(slot, stack, simulate);
                } else {
                    ItemStack previousStack = stacks.get(slot).copy();
                    ItemStack result = super.insertItem(slot, stack, simulate);
                    ItemStack newStack = stacks.get(slot);
                    itemsChanged(previousStack, newStack, slot);
                    return result;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };

        addSlot(new PlaneUpgradeSlot(itemHandler, 0, 8, 17, this));
        addSlot(new PlaneUpgradeSlot(itemHandler, 1, 26, 17, this));
        addSlot(new PlaneUpgradeSlot(itemHandler, 2, 8, 35, this));
        addSlot(new PlaneUpgradeSlot(itemHandler, 3, 26, 35, this));
        addSlot(new PlaneUpgradeSlot(itemHandler, 4, 8, 53, this));
        addSlot(new PlaneUpgradeSlot(itemHandler, 5, 26, 53, this));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        Entity entity = playerInventory.player.level().getEntity(planeID);
        if (entity instanceof PlaneEntity planeEntity) {
            this.planeEntity = planeEntity;

            int i = 0;
            for (Upgrade upgrade : planeEntity.upgrades.values()) {
                itemHandler.setStackInSlot(i, upgrade.getItemStack());
                if (i >= 5) {
                    break;
                }
                i++;
            }
        }

        internalItemsChange = false;
    }

    public void itemsChanged(ItemStack previousStack, ItemStack newStack, int slot) {
        if (internalItemsChange || ItemStack.isSameItemSameTags(previousStack, newStack)) {
            return;
        }

        AtomicBoolean success = new AtomicBoolean(false);

        if (!previousStack.isEmpty()) {
            SimplePlanesUpgrades.getUpgradeFromItem(previousStack.getItem()).ifPresent(upgradeType -> {
                ResourceLocation resourceLocation = SimplePlanesRegistries.UPGRADE_TYPES.get().getKey(upgradeType);
                Upgrade upgrade = planeEntity.upgrades.get(resourceLocation);
                if (ItemStack.isSameItemSameTags(previousStack, upgrade.getItemStack())) {
                    ItemStack itemStack = ItemStack.EMPTY;
                    for (int i = 0; i < 6; i++) {
                        if (i == slot) {
                            continue;
                        }
                        itemStack = itemHandler.getStackInSlot(i);
                        if (ItemStack.isSameItem(previousStack, itemStack)) {
                            break;
                        } else {
                            itemStack = ItemStack.EMPTY;
                        }
                    }
                    if (!itemStack.isEmpty()) {
                        if (!ItemStack.isSameItemSameTags(previousStack, itemStack)) {
                            planeEntity.removeUpgrade(resourceLocation);
                            tryUpgradeFromItem(itemStack, success);
                        }
                    } else {
                        planeEntity.removeUpgrade(resourceLocation);
                    }
                }
                success.set(true);
            });
        }

        if (!newStack.isEmpty()) {
            tryUpgradeFromItem(newStack, success);
        }

        if (success.get()) {
            if (errorSlot == slot) {
                errorSlot = -1;
            }
        } else {
            errorSlot = slot;
        }
    }

    private void tryUpgradeFromItem(ItemStack newStack, AtomicBoolean success) {
        Item item = newStack.getItem();
        UpgradeType upgradeType = SimplePlanesUpgrades.ITEM_UPGRADE_MAP.get(item);
        PayloadEntry payloadEntry = null;
        if (upgradeType == null && planeEntity instanceof LargePlaneEntity largePlaneEntity && !largePlaneEntity.hasLargeUpgrade) {
            upgradeType = SimplePlanesUpgrades.LARGE_ITEM_UPGRADE_MAP.get(item);
            if (upgradeType == null) {
                payloadEntry = PlanePayloadReloadListener.payloadEntries.get(item);
                if (payloadEntry != null) {
                    upgradeType = SimplePlanesUpgrades.PAYLOAD.get();
                }
            }
        }
        if (upgradeType != null && planeEntity.canAddUpgrade(upgradeType)) {
            if (payloadEntry != null) {
                planeEntity.addUpgradeInWorkbench(newStack, new PayloadUpgrade(planeEntity, payloadEntry));
            } else {
                planeEntity.addUpgradeInWorkbench(newStack, upgradeType.instanceSupplier.apply(planeEntity));
            }

            success.set(true);
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (player instanceof ServerPlayer serverPlayer) {
            HashSet<Item> attachedUpgradeItems = new HashSet<>(6);
            ArrayList<ItemStack> notAttachedItemStacks = new ArrayList<>(6);

            for (Upgrade upgrade : planeEntity.upgrades.values()) {
                attachedUpgradeItems.add(upgrade.getItemStack().getItem());
            }

            for (int i = 0; i < 6; i++) {
                ItemStack itemStack = itemHandler.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    if (attachedUpgradeItems.contains(itemStack.getItem())) {
                        attachedUpgradeItems.remove(itemStack.getItem());
                    } else {
                        notAttachedItemStacks.add(itemStack);
                    }
                }
            }

            if (serverPlayer.isAlive() && !serverPlayer.hasDisconnected()) {
                for (ItemStack itemStack : notAttachedItemStacks) {
                    serverPlayer.getInventory().placeItemBackInInventory(itemStack);
                }
            } else {
                for (ItemStack itemStack : notAttachedItemStacks) {
                    serverPlayer.drop(itemStack, false);
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return planeEntity != null && planeEntity.isAlive();
    }
}
