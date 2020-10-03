package xyz.przemyk.simpleplanes.integration;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import xyz.przemyk.simpleplanes.SimplePlanesConfig;

import java.util.Optional;
import java.util.function.Supplier;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) screen -> AutoConfig.getConfigScreen(SimplePlanesConfig.class, screen).get();
    }

}
