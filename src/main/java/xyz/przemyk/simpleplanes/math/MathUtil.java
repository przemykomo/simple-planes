package xyz.przemyk.simpleplanes.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil extends MathHelper {
    public static void hamiltonProduct(Quaternion this_, Quaternion other) {
        float f = this_.getX();
        float g = this_.getY();
        float h = this_.getZ();
        float i = this_.getW();
        float j = other.getX();
        float k = other.getY();
        float l = other.getZ();
        float m = other.getW();
        this_.x = i * j + f * m + g * l - h * k;
        this_.y = i * k - f * l + g * m + h * j;
        this_.z = i * l + f * k - g * j + h * m;
        this_.w = i * m - f * j - g * k - h * l;
    }

    public static Quaternion rotationDegreesX(float rotationAngle) {
        rotationAngle *= 0.017453292F;
        float f = sin(rotationAngle / 2.0F);
        float w = cos(rotationAngle / 2.0F);
        return new Quaternion(f, 0, 0, w);
    }

    public static Quaternion rotationDegreesY(float rotationAngle) {
        rotationAngle *= 0.017453292F;
        float f = sin(rotationAngle / 2.0F);
        float w = cos(rotationAngle / 2.0F);
        return new Quaternion(0, f, 0, w);
    }

    public static Quaternion rotationDegreesZ(float rotationAngle) {
        rotationAngle *= 0.017453292F;
        float f = sin(rotationAngle / 2.0F);
        float w = cos(rotationAngle / 2.0F);
        return new Quaternion(0, 0, f, w);
    }


    public static double angelBetweenVec(Vec3d v1, Vec3d v2) {
        return Math.toDegrees(Math.acos(normalizedDotProduct(v1, v2)));
    }

    public static double normalizedDotProduct(Vec3d v1, Vec3d v2) {
        return v1.dotProduct(v2) / (v1.length() * v2.length());
    }

    public static float getPitch(Vec3d motion) {
        double y = motion.y;
        return (float) Math.toDegrees(Math.atan2(y, Math.sqrt(motion.x * motion.x + motion.z * motion.z)));
    }

    public static float getYaw(Vec3d motion) {
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

    public static Vec3d rotationToVector(double yaw, double pitch) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);
        double xzLen = Math.cos(pitch);
        double x = -xzLen * Math.sin(yaw);
        double y = Math.sin(pitch);
        double z = xzLen * Math.cos(-yaw);
        return new Vec3d(x, y, z);
    }

    public static Vec3d rotationToVector(double yaw, double pitch, double size) {
        Vec3d vec = rotationToVector(yaw, pitch);
        return vec.scale(size / vec.length());
    }

    public static double getHorizontalLength(Vec3d Vec3d) {
        return Math.sqrt(Vec3d.x * Vec3d.x + Vec3d.z * Vec3d.z);
    }

    public static EulerAngles toEulerAngles(Quaternion q) {
        EulerAngles angles = new EulerAngles();

        // roll (x-axis rotation)
        double sinr_cosp = 2 * (q.getW() * q.getZ() + q.getX() * q.getY());
        double cosr_cosp = 1 - 2 * (q.getZ() * q.getZ() + q.getX() * q.getX());
        angles.roll = Math.toDegrees(Math.atan2(sinr_cosp, cosr_cosp));

        // pitch (z-axis rotation)
        double sinp = 2 * (q.getW() * q.getX() - q.getY() * q.getZ());
        if (Math.abs(sinp) >= 0.98) {
            angles.pitch = -Math.toDegrees(Math.signum(sinp) * Math.PI / 2); // use 90 degrees if out of range
        } else {
            angles.pitch = -Math.toDegrees(Math.asin(sinp));
        }

        // yaw (y-axis rotation)
        double siny_cosp = 2 * (q.getW() * q.getY() + q.getZ() * q.getX());
        double cosy_cosp = 1 - 2 * (q.getX() * q.getX() + q.getY() * q.getY());
        angles.yaw = Math.toDegrees(Math.atan2(siny_cosp, cosy_cosp));

        return angles;
    }

    public static float fastInvSqrt(float number) {
        float f = 0.5F * number;
        int i = Float.floatToIntBits(number);
        i = 1597463007 - (i >> 1);
        number = Float.intBitsToFloat(i);
        return number * (1.5F - f * number * number);
    }

    public static Quaternion normalizeQuaternion(Quaternion q) {
        float f = q.getX() * q.getX() + q.getY() * q.getY() + q.getZ() * q.getZ() + q.getW() * q.getW();
        float x = q.getX();
        float y = q.getY();
        float z = q.getZ();
        float w = q.getW();
        if (f > 1.0E-6F) {
            float f1 = fastInvSqrt(f);
            x *= f1;
            y *= f1;
            z *= f1;
            w *= f1;
            return new Quaternion(x, y, z, w);
        } else {
            return new Quaternion(0, 0, 0, 0);
        }

    }

    public static Quaternion toQuaternion(double yaw, double pitch, double roll) { // yaw (Z), pitch (Y), roll (X)
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

        float w = (float) (cr * cp * cy + sr * sp * sy);
        float z = (float) (sr * cp * cy - cr * sp * sy);
        float x = (float) (cr * sp * cy + sr * cp * sy);
        float y = (float) (cr * cp * sy - sr * sp * cy);

        return new Quaternion(x, y, z, w);
    }

    public static Quaternion lerpQ(float perc, Quaternion start, Quaternion end) {
        // Only unit quaternions are valid rotations.
        // Normalize to avoid undefined behavior.
        start = normalizeQuaternion(start);
        end = normalizeQuaternion(end);

        // Compute the cosine of the angle between the two vectors.
        double dot = start.getX() * end.getX() + start.getY() * end.getY() + start.getZ() * end.getZ() + start.getW() * end.getW();

        // If the dot product is negative, slerp won't take
        // the shorter path. Note that v1 and -v1 are equivalent when
        // the negation is applied to all four components. Fix by
        // reversing one quaternion.
        if (dot < 0.0f) {
            end = new Quaternion(-end.getX(), -end.getY(), -end.getZ(), -end.getW());
            dot = -dot;
        }

        double DOT_THRESHOLD = 0.9995;
        if (dot > DOT_THRESHOLD) {
            // If the inputs are too close for comfort, linearly interpolate
            // and normalize the result.

            Quaternion quaternion = new Quaternion(
                start.getX() * (1 - perc) + end.getX() * perc,
                start.getY() * (1 - perc) + end.getY() * perc,
                start.getZ() * (1 - perc) + end.getZ() * perc,
                start.getW() * (1 - perc) + end.getW() * perc
            );
            return normalizeQuaternion(quaternion);
        }

        // Since dot is in range [0, DOT_THRESHOLD], acos is safe
        double theta_0 = Math.acos(dot);        // theta_0 = angle between input vectors
        double theta = theta_0 * perc;          // theta = angle between v0 and result
        double sin_theta = Math.sin(theta);     // compute this value only once
        double sin_theta_0 = Math.sin(theta_0); // compute this value only once

        float s0 = (float) (Math.cos(theta) - dot * sin_theta / sin_theta_0);  // == sin(theta_0 - theta) / sin(theta_0)
        float s1 = (float) (sin_theta / sin_theta_0);

        Quaternion quaternion = new Quaternion(
            start.getX() * (s0) + end.getX() * s1,
            start.getY() * (s0) + end.getY() * s1,
            start.getZ() * (s0) + end.getZ() * s1,
            start.getW() * (s0) + end.getW() * s1
        );
        return normalizeQuaternion(quaternion);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static void transformVec(Vector3f vec, Quaternion rotation) {
        Quaternion quaternion = new Quaternion(rotation);
        hamiltonProduct(quaternion,new Quaternion(vec.getX(), vec.getY(), vec.getZ(), 0.0F));
        Quaternion quaternion2 = new Quaternion(rotation);
        quaternion2.negate();
        hamiltonProduct(quaternion,quaternion2);
        vec.set(quaternion.getX(), quaternion.getY(), quaternion.getZ());
    }

    public static class EulerAngles {
        public double pitch, yaw, roll;

        public EulerAngles() {
        }

        //TODO: remove unused constructor?
        @SuppressWarnings("unused")
        public EulerAngles(double pitch, double yaw, double roll) {
            this.pitch = pitch;
            this.yaw = yaw;
            this.roll = roll;
        }

        public EulerAngles(EulerAngles a) {
            this.pitch = a.pitch;
            this.yaw = a.yaw;
            this.roll = a.roll;
        }

        public EulerAngles copy() {
            return new EulerAngles(this);
        }

        @Override
        public String toString() {
            return "EulerAngles{" +
                "pitch=" + pitch +
                ", yaw=" + yaw +
                ", roll=" + roll +
                '}';
        }
    }
}
