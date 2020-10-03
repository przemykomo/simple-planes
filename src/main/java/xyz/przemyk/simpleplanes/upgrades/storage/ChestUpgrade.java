package xyz.przemyk.simpleplanes.upgrades.storage;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.BackSeatBlockModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;


public class ChestUpgrade extends Upgrade implements InventoryChangedListener, NamedScreenHandlerFactory {
    ChestBlockEntity tileEntity;

    public Inventory inventory;
    public float lidAngle;
    private float partialticks = 0;
    private boolean open = false;
    private int size = 1;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST, planeEntity);
        initChest();
        lidAngle = 0f;
    }

    protected void initChest() {
        Inventory inventory = this.inventory;
        this.inventory = new SimpleInventory(size * 9) {
            @Override
            public void onOpen(PlayerEntity player) {
                ChestUpgrade.this.openInventory(player);
//                PlaneNetworking.send_OpenInventory(true);
            }

            @Override
            public void onClose(PlayerEntity player) {
                ChestUpgrade.this.closeInventory(player);
//                PlaneNetworking.send_OpenInventory(false);
            }
        };
        tileEntity = new ChestBlockEntity() {
            @Override
            public float getAnimationProgress(float partialTicks) {
                return ChestUpgrade.this.lidAngle;
//                return super.getLidAngle(ChestUpgrade.this.partialticks);
            }

            @Override
            public BlockState getCachedState() {
                return Blocks.CHEST.getDefaultState();
            }
        };
//        this.inventory = tileEntity;
        if (inventory != null) {
//            inventory.removeListener(this);
            int min_size = Math.min(inventory.size(), this.inventory.size());
            int j = 0;
            for (; j < min_size; ++j) {
                ItemStack itemstack = inventory.getStack(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setStack(j, itemstack.copy());
                }
            }
            int max_size = Math.max(inventory.size(), this.inventory.size());
            for (; j < max_size; ++j) {
                ItemStack itemstack = inventory.getStack(j);
                if (!itemstack.isEmpty()) {
                    planeEntity.dropStack(itemstack);
                }
            }
        }
//        this.inventory.addListener(this);
    }

    @Override
    public void onInventoryChanged(Inventory invBasic) {

    }


    @Override
    public boolean tick() {
        if (open && this.lidAngle == 0.0F) {
            planeEntity.playSound(SoundEvents.BLOCK_CHEST_OPEN, 0.5F, planeEntity.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (!open && this.lidAngle > 0.0F || open && this.lidAngle < 1.0F) {
            float prevLidAngle = this.lidAngle;
            if (open) {
                this.lidAngle += 0.01F;
            } else {
                this.lidAngle -= 0.01F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            if (this.lidAngle < 0.5F && prevLidAngle >= 0.5F) {
                planeEntity.playSound(SoundEvents.BLOCK_CHEST_CLOSE, 0.5F, planeEntity.world.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }

        return super.tick();
    }

    public void openInventory(PlayerEntity player) {
//        tileEntity.openInventory(player);
//        inventory.openInventory(player);
        this.open = true;
        planeEntity.upgradeChanged();
    }

    public void closeInventory(PlayerEntity player) {
//        tileEntity.closeInventory(player);
//        inventory.openInventory(player);

        this.open = false;
        planeEntity.upgradeChanged();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ListTag listnbt = nbt.getList("Items", 10);
        size = nbt.getInt("size");
        this.initChest();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromTag(compoundnbt));
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("size", size);
        ListTag listnbt = new ListTag();
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.getStack(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundnbt = new CompoundTag();
                compoundnbt.putByte("Slot", (byte) i);
                itemstack.toTag(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }
        compound.put("Items", listnbt);
        return compound;
    }

    @Override
    public CompoundTag serializeNBTData() {
        CompoundTag compound = super.serializeNBTData();
        compound.putBoolean("open", open);
        compound.putInt("size", size);

        return compound;
    }

    @Override
    public void deserializeNBTData(CompoundTag nbt) {
        if (planeEntity.world.isClient) {
            open = nbt.getBoolean("open");
        } else {
            open = false;
        }
        size = nbt.getInt("size");
        super.deserializeNBTData(nbt);
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        this.partialticks = partialticks;
        tileEntity.setLocation(null, BlockPos.ORIGIN);
        BackSeatBlockModel.renderTileBlock(planeEntity, partialticks, matrixStack, buffer, packedLight, tileEntity);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(SimplePlanesMod.MODID + ":chest");
    }

    @Override
    public ScreenHandler createMenu(int id, PlayerInventory playerInventoryIn, PlayerEntity playerEntity) {
        ScreenHandlerType<?> type;
        switch (size) {
            case 1:
                type = ScreenHandlerType.GENERIC_9X1;
                break;
            case 2:
                type = ScreenHandlerType.GENERIC_9X2;
                break;
            case 3:
                type = ScreenHandlerType.GENERIC_9X3;
                break;
            case 4:
                type = ScreenHandlerType.GENERIC_9X4;
                break;
            case 5:
                type = ScreenHandlerType.GENERIC_9X5;
                break;
            case 6:
                type = ScreenHandlerType.GENERIC_9X6;
                break;
            default:
                type = ScreenHandlerType.GENERIC_3X3;
                break;
        }
        return new GenericContainerScreenHandler(type, id, playerInventoryIn, inventory, size);
    }

    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (!world.isClient && itemStack.getItem() == Items.CHEST && size < 6 && !this.open && !planeEntity.isFull()) {
            size++;
            initChest();
            itemStack.decrement(1);
            planeEntity.upgradeChanged();
        }
        return false;
    }

    @Override
    public DefaultedList<ItemStack> getDrops() {
        DefaultedList<ItemStack> items = DefaultedList.of();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemstack = this.inventory.getStack(i);
            if (!itemstack.isEmpty()) {
                items.add(itemstack);
            }
        }
        for (int i = 0; i < size; i++) {
            items.add(getType().getDrops());
        }
        return items;
    }

    @Override
    public int getSeats() {
        return size;
    }
}
