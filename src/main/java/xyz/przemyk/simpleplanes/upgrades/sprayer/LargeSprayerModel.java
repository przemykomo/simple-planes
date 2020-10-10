package xyz.przemyk.simpleplanes.upgrades.sprayer;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeSprayerModel extends ModelBase {
    public static final LargeSprayerModel INSTANCE = new LargeSprayerModel();

    private final ModelRenderer Body;
    private final ModelRenderer sprayer;
    private final ModelRenderer spray_lest;
    private final ModelRenderer spray_right;

    public LargeSprayerModel() {
        textureWidth = 64;
        textureHeight = 64;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        sprayer = new ModelRenderer(this);
        sprayer.setRotationPoint(0, 0, 0);
        Body.addChild(sprayer);

        spray_lest = new ModelRenderer(this);
        spray_lest.setRotationPoint(0, 0, 0);
        sprayer.addChild(spray_lest);
        setRotationAngle(spray_lest, 0, 0, -0.0873F);
        spray_lest.setTextureOffset(9, 17).addBox(13, 0, -12, 3, 3, 3, 0);
        spray_lest.setTextureOffset(0, 14).addBox(28, 0, -12, 3, 3, 3, 0);
        spray_lest.setTextureOffset(0, 4).addBox(8, -2, -12, 23, 1, 3, 0);

        spray_right = new ModelRenderer(this);
        spray_right.setRotationPoint(0, 3, 0);
        sprayer.addChild(spray_right);
        setRotationAngle(spray_right, 0, 0, 0.0873F);
        spray_right.setTextureOffset(9, 11).addBox(-16, -3, -12, 3, 3, 3, 0);
        spray_right.setTextureOffset(0, 8).addBox(-31, -3, -12, 3, 3, 3, 0);
        spray_right.setTextureOffset(0, 0).addBox(-31, -5, -12, 23, 1, 3, 0);
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