package xyz.przemyk.simpleplanes.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.List;

public class InformationItem extends Item {

    private final Text information;

    public InformationItem(Text information) {
        super(new Settings().group(SimplePlanesItems.SIMPLE_PLANES_ITEM_GROUP));
        this.information = information;
    }

    @Override
    public void appendTooltip(ItemStack stack,  World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        tooltip.add(information);
    }
}
