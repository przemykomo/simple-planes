package xyz.przemyk.simpleplanes.upgrades.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.BackSeatBlockModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;


public class ChestUpgrade extends Upgrade implements IInventoryChangedListener, IInteractionObject {
    TileEntityChest tileEntity;
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/storage.png");

    public IInventory inventory;
    private float partialticks = 0;
    private boolean open = false;
    private int size = 1;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST, planeEntity);
        initChest();
    }

    protected void initChest() {
        IInventory inventory = this.inventory;
        this.inventory = new InventoryBasic("chest", false, size * 9) {
            @Override
            public void openInventory(EntityPlayer player) {
                ChestUpgrade.this.openInventory(player);
//                PlaneNetworking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerEntityPlayer) player), true);
            }

            @Override
            public void closeInventory(EntityPlayer player) {
                ChestUpgrade.this.closeInventory(player);
//                PlaneNetworking.OPEN_INVENTORY.send(PacketDistributor.PLAYER.with(() -> (ServerEntityPlayer) player), false);
            }
        };
        tileEntity = new TileEntityChest() {


//            @Override
//            public BlockState getBlockState() {
//                return Blocks.CHEST.getDefaultState();
//            }
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
    public boolean tick() {
        tileEntity.prevLidAngle = tileEntity.lidAngle;
        if (open && tileEntity.lidAngle == 0.0F) {
            planeEntity.playSound(SoundEvents.BLOCK_CHEST_OPEN, 0.5F, planeEntity.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (!open && tileEntity.lidAngle > 0.0F || open && tileEntity.lidAngle < 1.0F) {
            float prevLidAngle = tileEntity.lidAngle;
            if (open) {
                tileEntity.lidAngle += 0.01F;
            } else {
                tileEntity.lidAngle -= 0.01F;
            }

            if (tileEntity.lidAngle > 1.0F) {
                tileEntity.lidAngle = 1.0F;
            }

            if (tileEntity.lidAngle < 0.5F && prevLidAngle >= 0.5F) {
                planeEntity.playSound(SoundEvents.BLOCK_CHEST_CLOSE, 0.5F, planeEntity.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (tileEntity.lidAngle < 0.0F) {
                tileEntity.lidAngle = 0.0F;
            }
        }

        return super.tick();
    }


    public void openInventory(EntityPlayer player) {
//        tileEntity.openInventory(player);
//        inventory.openInventory(player);
        this.open = true;
        planeEntity.upgradeChanged();
    }

    public void closeInventory(EntityPlayer player) {
//        tileEntity.closeInventory(player);
//        inventory.openInventory(player);

        this.open = false;
        planeEntity.upgradeChanged();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList listnbt = nbt.getTagList("Items", 10);
        size = nbt.getInteger("size");
        this.initChest();

        for (int i = 0; i < listnbt.tagCount(); ++i) {
            NBTTagCompound compoundnbt = listnbt.getCompoundTagAt(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < this.inventory.getSizeInventory()) {
                this.inventory.setInventorySlotContents(j, new ItemStack(compoundnbt));
            }
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("size", size);
        NBTTagList listnbt = new NBTTagList();
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                NBTTagCompound compoundnbt = new NBTTagCompound();
                compoundnbt.setByte("Slot", (byte) i);
                itemstack.writeToNBT(compoundnbt);
                listnbt.appendTag(compoundnbt);
            }
        }
        compound.setTag("Items", listnbt);
        return compound;
    }

    @Override
    public NBTTagCompound serializeNBTData() {
        NBTTagCompound compound = super.serializeNBTData();
        compound.setBoolean("open", open);
        compound.setInteger("size", size);

        return compound;
    }

    @Override
    public void deserializeNBTData(NBTTagCompound nbt) {
        if (planeEntity.world.isRemote) {
            open = nbt.getBoolean("open");
        } else {
            open = false;
        }
        size = nbt.getInteger("size");
        super.deserializeNBTData(nbt);
    }


//    @Override
//    public void render(float partialticks, float scale) {
//        this.partialticks = partialticks;
//        tileEntity.setWorld(null);
////        tileEntity.setWorldAndPos(null, BlockPos.ZERO);
//        BackSeatBlockModel.renderTileBlock(planeEntity, partialticks, tileEntity);
//    }
    @Override
    public void render(float partialticks, float scale) {
        BackSeatBlockModel.renderBlock(planeEntity, partialticks, scale);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

//    @Override
//    public ITextComponent getDisplayName() {
//        return new TranslationTextComponent(SimplePlanesMod.MODID + ":chest");
//    }
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
//        this.fillWithLoot(playerIn);
        return new ContainerChest(playerInventory, inventory, playerIn);
    }

    @Override
    public String getGuiID() {
        return "simpleplanes:chest_upgrade";
    }


    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (getType().IsThisItem(itemStack) && size < 6 && !this.open && !planeEntity.isFull()) {
            size++;
            initChest();
            itemStack.shrink(1);
            planeEntity.upgradeChanged();
        }
        return false;
    }

    @Override
    public NonNullList<ItemStack> getDrops() {
        NonNullList<ItemStack> items = NonNullList.create();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
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

    @Override
    public String getName() {
        return "plane chest";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("plane chest");
    }
}
