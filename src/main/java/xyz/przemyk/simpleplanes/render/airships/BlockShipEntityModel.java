package xyz.przemyk.simpleplanes.render.airships;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import xyz.przemyk.simpleplanes.entities.BlockShip.BlockShipData;
import xyz.przemyk.simpleplanes.entities.BlockShip.BlockShipEntity;

public class BlockShipEntityModel extends EntityModel<BlockShipEntity> {
    private BlockShipEntity ship;
    private int direction;
    private float xOffset;
    private float zOffset;
    private int yOffset;

    private float getxOffset() {
        switch (this.direction) {
            case 90:
                return 1f;
            case 180:
            case 0:
                return -0.5f;
            case 270:
                return -2f;
            default:
                return 0f;
        }
    }

    private float getzOffset() {
        switch (this.direction) {
            case 90:
            case 270:
                return -0.5f;
            case 180:
                return 1f;
            case 0:
                return -2f;
            default:
                return 0f;
        }
    }


    @Override
    public void setRotationAngles(BlockShipEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (this.entities == null) {
//            this.entities = new HashMap<>();
//        }
        this.ship = entityIn;
//        if (entity.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.OAK_PLANKS) {
//            CompoundTag tag = entity.getEquippedStack(EquipmentSlot.CHEST).getTag();
//            this.ship = tag.getString("model");
//            this.entities.put(this.ship, (ListTag) tag.get("parts"));
//            this.direction = tag.getInt("direction");
        this.xOffset = getxOffset();
        this.zOffset = getzOffset();
//            this.yOffset = tag.getInt("offset");
//        }

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {


    }

    public static void renderEngine(BlockShipEntity ship, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLight,
                                    int combinedOverlayIn) {
        if (ship != null) {
//            VertexConsumerProvider vertexConsumerProvider = Minecraft.getInstance().getBufferBuilders().getEntityVertexConsumers();
            matrixStackIn.push();
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
//            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90));
            BlockShipData data = ship.getData();
//            BlockPos center;
//            if (data.seats.size() > 0) {
//                center = data.seats.get(0);
//                matrixStackIn.translate(center.getX(),center.getY(),center.getZ());
//            }
            Vector3d center = data.getCenter();
            matrixStackIn.translate(-center.getX(), 0 , -center.getZ());

            matrixStackIn.translate(0, -data.p1.getY()-1.5, 0);

            for (BlockPos blockpos2 : BlockPos.getAllInBoxMutable(data.p1, data.p2)) {
                matrixStackIn.push();
                int x = blockpos2.getX();
                int y = blockpos2.getY();
                int z = blockpos2.getZ();
                BlockState state = data.get(x, y, z);
                if (state.getBlock() == Blocks.LAPIS_BLOCK) {
                    System.out.println("lapiz = " + blockpos2);
                }

                matrixStackIn.translate(x, y, z);
                Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStackIn, bufferIn, packedLight, combinedOverlayIn);
                matrixStackIn.pop();
            }
            ;
            matrixStackIn.pop();
        }
    }

}