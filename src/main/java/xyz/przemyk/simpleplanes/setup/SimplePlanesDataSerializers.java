package xyz.przemyk.simpleplanes.setup;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.DataSerializerEntry;
import xyz.przemyk.simpleplanes.Quaternion;


import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class SimplePlanesDataSerializers {
    @SubscribeEvent
    public static void registerSerializers(RegistryEvent.Register<DataSerializerEntry> event) {
        // Create a new DataSerializerEntry (can't register the serializer directly)
        // and add it to the forge registry list so our classes can use it.
        // The register() function takes an IForgeRegistryEntry so we create that here from the DataSerializerEntry.
        event.getRegistry().register(new DataSerializerEntry(QUATERNION_SERIALIZER).setRegistryName(MODID, "serializerQuaternion"));
    }


    public static final DataSerializer<Quaternion> QUATERNION_SERIALIZER = new DataSerializer<Quaternion>() {

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
        public DataParameter<Quaternion> createKey(int id) {
            return new DataParameter<>(id, this);
        }

        @Override
        public Quaternion copyValue(Quaternion q) {
            return new Quaternion(q);
        }
    };


}