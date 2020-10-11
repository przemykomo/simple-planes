package xyz.przemyk.simpleplanes.math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Quaternion {
    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Quaternion q) {
        x = q.x;
        y = q.y;
        z = q.z;
        w = q.w;
    }

    public Quaternion() {
        w = 1;
    }

    public static Quaternion mul(Quaternion left, Quaternion right,
                                 Quaternion dest) {
        if (dest == null)
            dest = new Quaternion();
        dest.set(left.x * right.w + left.w * right.x + left.y * right.z
                - left.z * right.y, left.y * right.w + left.w * right.y
                + left.z * right.x - left.x * right.z, left.z * right.w
                + left.w * right.z + left.x * right.y - left.y * right.x,
            left.w * right.w - left.x * right.x - left.y * right.y
                - left.z * right.z);
        return dest;
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

    public float getW() {
        return w;
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @SideOnly(Side.CLIENT)
    public org.lwjgl.util.vector.Quaternion convert() {
        return new org.lwjgl.util.vector.Quaternion(x, y, z, w);
    }
}
