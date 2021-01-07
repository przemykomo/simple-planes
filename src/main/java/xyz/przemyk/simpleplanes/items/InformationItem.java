package xyz.przemyk.simpleplanes.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import javax.annotation.Nullable;
import java.util.List;

public class InformationItem extends Item {

    private final ITextComponent information;

    public InformationItem(ITextComponent information) {
        super(new Properties().group(SimplePlanesItems.ITEM_GROUP));
        this.information = information;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(information);
    }
}
