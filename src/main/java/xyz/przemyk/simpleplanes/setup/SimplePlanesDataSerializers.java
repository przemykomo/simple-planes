package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Quaternion;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class SimplePlanesDataSerializers {
//    private static final DeferredRegister<> DATA_SERIALIZERS = DeferredRegister
//        .create(ForgeRegistries.DATA_SERIALIZERS, SimplePlanesMod.MODID);
//
    public static void init() {
        TrackedDataHandlerRegistry.register(QUATERNION_SERIALIZER);
    }

    public static final TrackedDataHandler<Quaternion> QUATERNION_SERIALIZER = new TrackedDataHandler<Quaternion>() {

        @Override
        public void write(PacketByteBuf buf, Quaternion q) {
            buf.writeFloat(q.getX());
            buf.writeFloat(q.getY());
            buf.writeFloat(q.getZ());
            buf.writeFloat(q.getW());
        }

        @Override
        public Quaternion read(PacketByteBuf buf) {
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

//    public static final RegistryObject<DataSerializerEntry> QUAT_SERIALIZER = DATA_SERIALIZERS
//        .register("quaternion", () -> new DataSerializerEntry(QUATERNION_SERIALIZER));


}