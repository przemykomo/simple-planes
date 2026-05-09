package sappy.simpleplanes.items;

import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class DescriptionItem extends Item {

    private final Component component;

    public DescriptionItem(Properties properties, Component component) {
        this.component = component;
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display,
            Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(component);
    }

}
