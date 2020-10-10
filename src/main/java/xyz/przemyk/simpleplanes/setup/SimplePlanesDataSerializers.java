package xyz.przemyk.simpleplanes.setup;

import net.minecraft.client.renderer.Quaternion;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

@SuppressWarnings("unused")
public class SimplePlanesDataSerializers {
    private static final DeferredRegister<DataSerializerEntry> DATA_SERIALIZERS = DeferredRegister
        .create(ForgeRegistries.DATA_SERIALIZERS, SimplePlanesMod.MODID);

    public static void init() {
        DATA_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
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
            try {
                return new Quaternion(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
            } catch (IndexOutOfBoundsException e) {
                // This function would throw anyway, might as well wrap the error with more relevant info
                throw new RuntimeException("packet buffer does not contain enough data to construct plane's Quaternion", e);
            }
        }

        @Override
        public Quaternion copyValue(Quaternion q) {
            return new Quaternion(q);
        }
    };

    public static final RegistryObject<DataSerializerEntry> QUAT_SERIALIZER = DATA_SERIALIZERS
        .register("quaternion", () -> new DataSerializerEntry(QUATERNION_SERIALIZER));


}