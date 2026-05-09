package sappy.simpleplanes.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.SPMod;

public class ItemModelProvider extends ModelProvider {

    public ItemModelProvider(PackOutput output) {
        super(output, SPMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (var entry : SPItems.ITEMS.getEntries()) {
            itemModels.generateFlatItem(entry.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}
