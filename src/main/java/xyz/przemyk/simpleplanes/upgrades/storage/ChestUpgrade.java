package xyz.przemyk.simpleplanes.upgrades.storage;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import javax.annotation.Nullable;

public class ChestUpgrade extends Upgrade implements IInventoryChangedListener, INamedContainerProvider {
    public static final MyChestTileEntity tileEntity = new MyChestTileEntity();

    ;

    public IInventory inventory;
    public float lidAngle;
    private float prevLidAngle = 0;
    private float partialticks = 0;
    private boolean open = false;
    private int size = 3;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST.get(), planeEntity);
        initChest();
        lidAngle = 0f;
    }

    protected void initChest() {
        IInventory inventory = this.inventory;
        this.inventory = new Inventory(size * 9) {
            @Override
            public void openInventory(PlayerEntity player) {
                ChestUpgrade.this.openInventory(player);
//                PlaneNetworking.OPEN_INVENTORY.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), true);
            }

            @Override
            public void closeInventory(PlayerEntity player) {
                ChestUpgrade.this.closeInventory(player);
//                PlaneNetworking.OPEN_INVENTORY.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), false);
            }
        };
//        this.inventory = tileEntity;
        if (inventory != null) {
//            inventory.removeListener(this);
            int min_size = Math.min(inventory.getSizeInventory(), this.inventory.getSizeInventory());
            int j = 0;
            for (; j < min_size; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setInventorySlotContents(j, itemstack.copy());
                }
            }
            int max_size = Math.max(inventory.getSizeInventory(), this.inventory.getSizeInventory());
            for (; j < max_size; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    planeEntity.entityDropItem(itemstack);
                }
            }
        }
//        this.inventory.addListener(this);
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {

    }


    @Override
    public void tick() {
        if (open && this.lidAngle == 0.0F) {
            planeEntity.playSound(SoundEvents.BLOCK_CHEST_OPEN, 0.5F, planeEntity.world.rand.nextFloat() * 0.1F + 0.9F);
        }
        prevLidAngle = this.lidAngle;

        if (!open && this.lidAngle > 0.0F || open && this.lidAngle < 1.0F) {
            if (open) {
                this.lidAngle += 0.01F;
            } else {
                this.lidAngle -= 0.01F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            if (this.lidAngle < 0.5F && prevLidAngle >= 0.5F) {
                planeEntity.playSound(SoundEvents.BLOCK_CHEST_CLOSE, 0.5F, planeEntity.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }

    public void openInventory(PlayerEntity player) {
//        tileEntity.openInventory(player);
//        inventory.openInventory(player);
        this.open = true;
        this.updateClient = true;
    }

    public void closeInventory(PlayerEntity player) {
//        tileEntity.closeInventory(player);
//        inventory.openInventory(player);

        this.open = false;
        this.updateClient = true;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT listnbt = nbt.getList("Items", 10);
        size = nbt.getInt("size");
        this.initChest();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < this.inventory.getSizeInventory()) {
                this.inventory.setInventorySlotContents(j, ItemStack.read(compoundnbt));
            }
        }
    }

    @Override
    public void writePacket(PacketBuffer buffer) {
        buffer.writeBoolean(this.open);
    }

    @Override
    public void readPacket(PacketBuffer buffer) {
        this.open = buffer.readBoolean();
    }

    @Override
    public void dropItems() {
        NonNullList<ItemStack> items = NonNullList.create();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                planeEntity.entityDropItem(itemstack);
            }
        }
        planeEntity.entityDropItem(Items.CHEST);
//        for (int i = 0; i < size; i++) {
//            items.add(getType().getDrops());
//        }
//        return items;

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("size", size);
        ListNBT listnbt = new ListNBT();
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putByte("Slot", (byte) i);
                itemstack.write(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }
        compound.put("Items", listnbt);
        return compound;
    }

//    @Override
//    public CompoundNBT serializeNBTData() {
//        CompoundNBT compound = super.serializeNBTData();
//        compound.putBoolean("open", open);
//        compound.putInt("size", size);
//
//        return compound;
//    }
//
//    @Override
//    public void deserializeNBTData(CompoundNBT nbt) {
//        if (planeEntity.world.isRemote) {
//            open = nbt.getBoolean("open");
//        } else {
//            open = false;
//        }
//        size = nbt.getInt("size");
//        super.deserializeNBTData(nbt);
//    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        this.partialticks = partialticks;
        tileEntity.setWorldAndPos(null, BlockPos.ZERO);
        tileEntity.setLidAngle(MathHelper.lerp(partialticks,prevLidAngle, lidAngle));
        matrixStack.push();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, 0, -0.15);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180));

        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, -1.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);

//        BlockState state = Blocks.TNT.getDefaultState();
//        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();

//        BackSeatBlockModel.renderTileBlock(planeEntity, partialticks, matrixStack, buffer, packedLight, tileEntity);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ":chest");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventoryIn, PlayerEntity playerEntity) {
        ContainerType<?> type;
        switch (size) {
            case 1:
                type = ContainerType.GENERIC_9X1;
                break;
            case 2:
                type = ContainerType.GENERIC_9X2;
                break;
            case 3:
                type = ContainerType.GENERIC_9X3;
                break;
            case 4:
                type = ContainerType.GENERIC_9X4;
                break;
            case 5:
                type = ContainerType.GENERIC_9X5;
                break;
            case 6:
                type = ContainerType.GENERIC_9X6;
                break;
            default:
                type = ContainerType.GENERIC_3X3;
                break;
        }
        return new ChestContainer(type, id, playerInventoryIn, inventory, size);
    }


//    @Override
//    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
//        if (event.getItemStack().getItem() == Items.CHEST && size < 6 && !this.open && !planeEntity.isFull()) {
//            size++;
//            initChest();
//            event.getItemStack().shrink(1);
//            planeEntity.upgradeChanged();
//        }
//    }

//    @Override
//    public int getSeats() {
//        return size;
//    }

    private static class MyChestTileEntity extends ChestTileEntity {

        public void setLidAngle(float lidAngle) {
            this.lidAngle = lidAngle;
        }

        @Override
        public float getLidAngle(float partialTicks) {
            return this.lidAngle;
        }

        @Override
        public BlockState getBlockState() {
            return Blocks.CHEST.getDefaultState();
        }
    }
}