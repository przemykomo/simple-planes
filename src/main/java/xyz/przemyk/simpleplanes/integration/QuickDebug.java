package xyz.przemyk.simpleplanes.integration;

import net.fabricmc.loader.ModContainer;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

/**
 * Makes F3+T not require a class hotswap for resources to reload
 * By ramidzkh:
 * https://gist.github.com/ramidzkh/7084005b478ecebfdb7ef3c1a9f69ffb
 */
public class QuickDebug {

	private static final Field ROOT;

	static {
		try {
			ROOT = ModContainer.class.getDeclaredField("root");
			ROOT.setAccessible(true);
		} catch (NoSuchFieldException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void init() {
		Path path = Paths.get("../src/main/resources").toAbsolutePath(); // Relative to your run directory
		if (FabricLoader.getInstance().isDevelopmentEnvironment() && Files.isDirectory(path)) {
			FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
				try {
					ROOT.set(modContainer, path);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
