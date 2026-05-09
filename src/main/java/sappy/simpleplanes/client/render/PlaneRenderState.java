package sappy.simpleplanes.client.render;

import org.joml.Quaternionfc;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class PlaneRenderState extends EntityRenderState {
    public Quaternionfc quat;
    public float propellerRotation;
}
