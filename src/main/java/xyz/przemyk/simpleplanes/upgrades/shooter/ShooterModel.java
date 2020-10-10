package xyz.przemyk.simpleplanes.upgrades.shooter;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class ShooterModel extends ModelBase {
    public static final ShooterModel INSTANCE = new ShooterModel();

    private ModelRenderer shooter;

    public ShooterModel() {
        rebuild();
    }

    private void rebuild() {
        textureWidth = 64;
        textureHeight = 64;

        shooter = new ModelRenderer(this);
        shooter.setRotationPoint(0, 17, 0);
        setRotationAngle(shooter, 0, 0, 0);

        shooter.setTextureOffset(0, 0).addBox(0, 0, -12, 16, 16, 16, 0);

    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.translate(-2, -0.35, -0.75);
        shooter.render(scale);
        GlStateManager.translate(3, 0, 0);

        shooter.render(scale);
        GlStateManager.popMatrix();

    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}