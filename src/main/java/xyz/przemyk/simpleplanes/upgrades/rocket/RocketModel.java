package xyz.przemyk.simpleplanes.upgrades.rocket;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class RocketModel extends ModelBase {
    public static final RocketModel INSTANCE = new RocketModel();

    private final ModelRenderer Body;

    public RocketModel() {
        textureWidth = 32;
        textureHeight = 32;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        ModelRenderer booster = new ModelRenderer(this);
        booster.setRotationPoint(0, 0, 0);
        Body.addChild(booster);
        booster.setTextureOffset(0, 0).addBox(8, -5, 9, 4, 4, 9, 0);
        booster.setTextureOffset(0, 13).addBox(-12, -5, 9, 4, 4, 9, 0);
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