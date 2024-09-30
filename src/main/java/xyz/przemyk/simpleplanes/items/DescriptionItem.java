package xyz.przemyk.simpleplanes.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DescriptionItem extends Item {

    private final Component component;

    public DescriptionItem(Properties properties, Component component) {
        super(properties);
        this.component = component;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(component);
    }
}
