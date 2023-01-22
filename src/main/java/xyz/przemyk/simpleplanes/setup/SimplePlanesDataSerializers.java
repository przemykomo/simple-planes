package xyz.przemyk.simpleplanes.setup;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import com.mojang.math.Quaternion;
import net.minecraft.network.syncher.EntityDataSerializers;

public class SimplePlanesDataSerializers {

    public static void init() {
        EntityDataSerializers.registerSerializer(QUATERNION_SERIALIZER_ENTRY);
    }

    public static final EntityDataSerializer<Quaternion> QUATERNION_SERIALIZER_ENTRY = new EntityDataSerializer<>() {

            @Override
            public void write(FriendlyByteBuf buf, Quaternion q) {
                buf.writeFloat(q.i());
                buf.writeFloat(q.j());
                buf.writeFloat(q.k());
                buf.writeFloat(q.r());
            }

            @Override
            public Quaternion read(FriendlyByteBuf buf) {
                try {
                    return new Quaternion(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
                } catch (IndexOutOfBoundsException e) {
                    // This function would throw anyway, might as well wrap the error with more relevant info
                    throw new RuntimeException("packet buffer does not contain enough data to construct plane's Quaternion", e);
                }
            }

            @Override
            public Quaternion copy(Quaternion q) {
                return new Quaternion(q);
            }
        };
}