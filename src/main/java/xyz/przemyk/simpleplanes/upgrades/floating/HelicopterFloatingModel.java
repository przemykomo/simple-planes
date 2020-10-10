package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class HelicopterFloatingModel extends ModelBase {
    public static final HelicopterFloatingModel INSTANCE = new HelicopterFloatingModel();

    private final ModelRenderer Body;
    private final ModelRenderer balloon;

    public HelicopterFloatingModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        balloon = new ModelRenderer(this);
        balloon.setRotationPoint(0, 0, 0);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addBox(-8, 0, -17, 16, 1, 36, 0);
        balloon.setTextureOffset(38, 39).addBox(8, -1, -17, 1, 2, 36, 0);
        balloon.setTextureOffset(0, 37).addBox(-9, -1, -17, 1, 2, 36, 0);
        balloon.setTextureOffset(68, 23).addBox(-9, -1, -18, 18, 2, 1, 0);
        balloon.setTextureOffset(68, 20).addBox(-9, -1, 19, 18, 2, 1, 0);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}