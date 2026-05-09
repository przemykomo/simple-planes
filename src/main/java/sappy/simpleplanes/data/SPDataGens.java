package sappy.simpleplanes.data;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import sappy.simpleplanes.SPMod;
import net.minecraft.data.PackOutput;

@EventBusSubscriber(modid = SPMod.MODID)
public class SPDataGens {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var pack = generator.getVanillaPack(true);

        pack.addProvider(output -> new ItemModelProvider(output));
    }
}
