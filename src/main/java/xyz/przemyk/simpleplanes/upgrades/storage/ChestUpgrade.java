package xyz.przemyk.simpleplanes.upgrades.storage;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.render.BackSeatBlockModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import javax.annotation.Nullable;

public class ChestUpgrade extends Upgrade implements IInventoryChangedListener, INamedContainerProvider {
    ChestTileEntity tileEntity;

    public IInventory inventory;
    public float lidAngle;
    private float partialticks;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST.get(), planeEntity);
        initChest();
        lidAngle = 1f;
    }

    protected void initChest() {
        IInventory inventory = this.inventory;
//        this.inventory = new Inventory(27){
//            @Override
//            public void openInventory(PlayerEntity player) {
//                ChestUpgrade.this.openInventory(player);
//            }
//
//            @Override
//            public void closeInventory(PlayerEntity player) {
//                ChestUpgrade.this.closeInventory(player);
//            }
//        };
        tileEntity = new ChestTileEntity() {
            @Override
            public float getLidAngle(float partialTicks) {
                return super.getLidAngle(ChestUpgrade.this.partialticks);
            }

            @Override
            public BlockState getBlockState() {
                return Blocks.CHEST.getDefaultState();
            }

            @Override
            public boolean isUsableByPlayer(PlayerEntity player) {
                return planeEntity.getPositionVec().distanceTo(player.getPositionVec()) < 10;
            }

            @Override
            public void openInventory(PlayerEntity player) {
                super.openInventory(player);
                PlaneNetworking.OPEN_INVENTORY.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), true);
            }

            @Override
            public void closeInventory(PlayerEntity player) {
                super.closeInventory(player);
                PlaneNetworking.OPEN_INVENTORY.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), false);
            }
        };
        this.inventory = tileEntity;
        if (inventory != null) {
//            inventory.removeListener(this);
            int i = Math.min(inventory.getSizeInventory(), this.inventory.getSizeInventory());
            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

//        this.inventory.addListener(this);
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {

    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT listnbt = nbt.getList("Items", 10);
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
    public boolean tick() {
        tileEntity.setWorldAndPos(planeEntity.world, planeEntity.getPosition());
        tileEntity.tick();
        return super.tick();
    }

    public void openInventory(PlayerEntity player) {
        tileEntity.openInventory(player);
    }

    public void closeInventory(PlayerEntity player) {
        tileEntity.closeInventory(player);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
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

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        this.partialticks = partialticks;
//        ChestTileEntityRenderer
//        BackSeatBlockModel.renderBlock(planeEntity,partialticks,matrixStack,buffer,packedLight, Blocks.CHEST.getDefaultState());
        tileEntity.setWorldAndPos(null, BlockPos.ZERO);
        BackSeatBlockModel.renderTileBlock(planeEntity, partialticks, matrixStack, buffer, packedLight, tileEntity);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ":chest");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventoryIn, PlayerEntity playerEntity) {
        return ChestContainer.createGeneric9X3(id, playerInventoryIn, inventory);
    }

    public void openClient(boolean b) {
        tileEntity.receiveClientEvent(1, b ? 1 : 0);
    }
}
