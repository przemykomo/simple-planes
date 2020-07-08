package xyz.przemyk.simpleplanes;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class MathUtil extends MathHelper {

    public static float getPitch(Vector3d motion) {
        double y = motion.y;
        return (float) Math.toDegrees(Math.atan2(y, Math.sqrt(motion.x * motion.x + motion.z * motion.z)));
    }

    public static float getYaw(Vector3d motion) {
        return (float) Math.toDegrees(Math.atan2(-motion.x, motion.z));
    }

    public static float lerpAngle(float perc, float start, float end) {
        return start + perc * wrapDegrees(end - start);
    }

    public static Vector3f getVecf(double yaw, double pitch) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);
        double xzLen = Math.cos(pitch);
        float x = (float) (-xzLen * Math.sin(yaw));
        float y = (float) Math.sin(pitch);
        float z = (float) (xzLen * Math.cos(-yaw));
        return new Vector3f(x, y, z);

    }

    public static Vector3d getVec(double yaw, double pitch) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);
        double xzLen = Math.cos(pitch);
        double x = -xzLen * Math.sin(yaw);
        double y = Math.sin(pitch);
        double z = xzLen * Math.cos(-yaw);
        return new Vector3d(x, y, z);
    }

    public static Vector3d getVec(double yaw, double pitch, double l) {
        Vector3d vec = getVec(yaw, pitch);
        return vec.scale(l / vec.length());
    }

    public static double getHorizontalLength(Vector3d vector3d){
        return Math.sqrt(vector3d.x*vector3d.x+vector3d.z*vector3d.z);
    }

}
