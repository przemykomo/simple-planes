package xyz.przemyk.simpleplanes;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.network.datasync.DataSerializers.registerSerializer;

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

    public static float lerpAngle180(float perc, float start, float end) {
        if (degreesDifferenceAbs(start, end) > 90)
            end += 180;
        return start + perc * wrapDegrees(end - start);
    }

    public static double lerpAngle180(double perc, double start, double end) {
        if (degreesDifferenceAbs(start, end) > 90)
            end += 180;
        return start + perc * wrapDegrees(end - start);
    }

    public static double lerpAngle(double perc, double start, double end) {
        return start + perc * wrapDegrees(end - start);
    }

    public static double degreesDifferenceAbs(double p_203301_0_, double p_203301_1_) {
        return Math.abs(wrapSubtractDegrees(p_203301_0_, p_203301_1_));
    }

    public static double wrapSubtractDegrees(double p_203302_0_, double p_203302_1_) {
        return wrapDegrees(p_203302_1_ - p_203302_0_);
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

    public static Vector3d getVec(Quaternion q) {
        return new Vector3d(q.getX(), q.getY(), q.getZ());
    }

    public static Vector3d getVecSized(double x, double y, double z, double l) {

        Vector3d vector3d = new Vector3d(x, y, z);
        if (l != 0)
            vector3d.scale(l / vector3d.length());
        return vector3d;
    }

    public static double getHorizontalLength(Vector3d vector3d) {
        return Math.sqrt(vector3d.x * vector3d.x + vector3d.z * vector3d.z);
    }

    public static Angels ToEulerAngles(Quaternion q) {
        Angels angles = new Angels();

        // roll (x-axis rotation)
        double sinr_cosp = 2 * (q.getW() * q.getZ() + q.getX() * q.getY());
        double cosr_cosp = 1 - 2 * (q.getZ() * q.getZ() + q.getX() * q.getX());
        angles.roll = Math.toDegrees(Math.atan2(sinr_cosp, cosr_cosp));

        // pitch (y-axis rotation)
        double sinp = 2 * (q.getW() * q.getX() - q.getY() * q.getZ());
        if (Math.abs(sinp) >= 0.99) {
            angles.pitch = -Math.toDegrees(Math.signum(sinp) * Math.PI / 2); // use 90 degrees if out of range
            angles.roll = 180;
//            angles.yaw = Math.toDegrees(Math.atan2(q.getX(),q.getW()));
//            return angles;
        } else {
            angles.pitch = -Math.toDegrees(Math.asin(sinp));
        }

        // yaw (z-axis rotation)
        double siny_cosp = 2 * (q.getW() * q.getY() + q.getZ() * q.getX());
        double cosy_cosp = 1 - 2 * (q.getX() * q.getX() + q.getY() * q.getY());
        angles.yaw = Math.toDegrees(Math.atan2(siny_cosp, cosy_cosp));

        return angles;
    }

    public static Quaternion ToQuaternion(double yaw, double pitch, double roll) // yaw (Z), pitch (Y), roll (X)
    {
        // Abbreviations for the various angular functions
        yaw = Math.toRadians(yaw);
        pitch = -Math.toRadians(pitch);
        roll = Math.toRadians(roll);

        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        Quaternion q;
        float w = (float) (cr * cp * cy + sr * sp * sy);
        float z = (float) (sr * cp * cy - cr * sp * sy);
        float x = (float) (cr * sp * cy + sr * cp * sy);
        float y = (float) (cr * cp * sy - sr * sp * cy);


        return new Quaternion(x, y, z, w);
    }

    public static Quaternion lerpQ(float perc, Quaternion start, Quaternion end) {
        return new Quaternion(
                start.getX() * (1 - perc) + end.getX() * perc,
                start.getY() * (1 - perc) + end.getY() * perc,
                start.getZ() * (1 - perc) + end.getZ() * perc,
                start.getW() * (1 - perc) + end.getW() * perc
        );
    }

    //    public static Quaternion lerpQ(float perc,Quaternion start,Quaternion end){
//        return new Quaternion(
//                start.getX()*(1-perc)+end.getX()*perc,
//                start.getY()*(1-perc)+end.getY()*perc,
//                start.getZ()*(1-perc)+end.getZ()*perc,
//                start.getW()*(1-perc)+end.getW()*perc
//        );
//    }
    public static class Angels {
        public double pitch, yaw, roll;

        public Angels() {
        }

        public Angels(double pitch, double yaw, double roll) {
            this.pitch = pitch;
            this.yaw = yaw;
            this.roll = roll;
        }

        public Angels(Angels a) {
            this.pitch = a.pitch;
            this.yaw = a.yaw;
            this.roll = a.roll;
        }

        public Angels copy() {
            return new Angels(this);
        }
    }

    public static final IDataSerializer<Quaternion> QUATERNION_SERIALIZER = new IDataSerializer<Quaternion>() {
        @Override
        public void write(PacketBuffer buf, Quaternion q) {
            buf.writeFloat(q.getX());
            buf.writeFloat(q.getY());
            buf.writeFloat(q.getZ());
            buf.writeFloat(q.getW());
        }

        @Override
        public Quaternion read(PacketBuffer buf) {
            return new Quaternion(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
        }

        @Override
        public Quaternion copyValue(Quaternion q) {
            return new Quaternion(q);
        }
    };

    static {
        registerSerializer(QUATERNION_SERIALIZER);
    }
}
