package xyz.przemyk.simpleplanes.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class EngineItem extends Item {
    public EngineItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable(SimplePlanesMod.MODID + ".engine_desc", Component.keybind("key.plane_engine_open.desc")));
    }
}
