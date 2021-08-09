package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneModel extends EntityModel<LargePlaneEntity> {

    private final ModelPart Body;
    private final ModelPart bone9;
    private final ModelPart bb_main;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0, 17, 0));
        body.addOrReplaceChild("bone3", CubeListBuilder.create()
                .texOffs(0, 64).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 53.0F)
                .texOffs(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 53.0F)
                .texOffs(55, 77).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 53.0F)
                .texOffs(0, 64).addBox(-7.0F, -13.0F, 18.0F, 14.0F, 13.0F, 2.0F)
                .texOffs(0, 38).addBox(-8.0F, -13.0F, -36.0F, 16.0F, 13.0F, 2.0F)
                .texOffs(110, 77).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F)
                .texOffs(0, 10).addBox(-1.0F, -12.0F, 20.0F, 2.0F, 11.0F, 17.0F)
                .texOffs(0, 0).addBox(-1.0F, -12.0F, 37.0F, 2.0F, 9.0F, 3.0F)
                .texOffs(55, 64).addBox(-19.0F, -8.0F, 23.0F, 38.0F, 2.0F, 12.0F)
                .texOffs(21, 10).addBox(-1.0F, -16.0F, 23.0F, 2.0F, 4.0F, 14.0F), PartPose.offset(0, 0, 18));

        body.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(-2, 1, -7));
        partDefinition.addOrReplaceChild("bone9", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-6.0F, -1.0F, -8.0F, 12.0F, 2.0F, 3.0F)
                .texOffs(37, 45).addBox(-8.0F, -3.0F, -9.0F, 2.0F, 5.0F, 5.0F)
                .texOffs(12, 59).addBox(-3.0F, -7.0F, -8.0F, 1.0F, 6.0F, 3.0F)
                .texOffs(16, 59).addBox(2.0F, -7.0F, -8.0F, 1.0F, 6.0F, 3.0F)
                .texOffs(1, 2).addBox(6.0F, -3.0F, -9.0F, 2.0F, 5.0F, 5.0F), PartPose.offset(0, 24, 0));

        PartDefinition bb_main = partDefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
                .texOffs(30, 45).addBox(-27.9564F, -28.0019F, -11.0F, 2.0F, 19.0F, 2.0F)
                .texOffs(74, 58).addBox(9.0F, -10.0F, -15.0F, 25.0F, 2.0F, 10.0F)
                .texOffs(74, 58).addBox(-33.5413F, -9.7605F, -15.0F, 25.0F, 2.0F, 10.0F)
                .texOffs(74, 58).addBox(-33.1968F, -29.7295F, -15.0F, 66.0F, 2.0F, 10.0F)
                .texOffs(30, 45).addBox(-6.989F, -27.7476F, -11.0F, 2.0F, 20.0F, 2.0F)
                .texOffs(8, 59).addBox(5.324F, -28.1751F, -11.0F, 2.0F, 20.0F, 2.0F), PartPose.offset(0, 24, 0));

        bb_main.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(8, 59).addBox(-1.0F, -9.5F, -1.0F, 2.0F, 19.0F, 2.0F),
                PartPose.offsetAndRotation(25.8637F, -18.0544F, -9.8508F, 0.0F, 0.0436F, 0.0F));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public LargePlaneModel(ModelPart part) {
        Body = part.getChild("body");
        bone9 = part.getChild("bone9");
        bb_main = part.getChild("bb_main");
    }

    @Override
    public void setupAnim(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        bone9.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
