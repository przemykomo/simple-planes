package xyz.przemyk.simpleplanes.math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vector3f() {
        x = y = z = 0;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @SideOnly(Side.CLIENT)
    public org.lwjgl.util.vector.Vector3f convert() {
        return new org.lwjgl.util.vector.Vector3f(x, y, z);
    }
}
