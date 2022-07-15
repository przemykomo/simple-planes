package xyz.przemyk.simpleplanes.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.PlaneWorkbenchScreen;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRecipes;

@JeiPlugin
public class SimplePlanesPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SimplePlanesMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new PlaneWorkbenchRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(PlaneWorkbenchRecipeCategory.RECIPE_TYPE, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(SimplePlanesRecipes.PLANE_WORKBENCH_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(PlaneWorkbenchContainer.class, SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchRecipeCategory.RECIPE_TYPE, 0, 2, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SimplePlanesItems.PLANE_WORKBENCH.get()), PlaneWorkbenchRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(PlaneWorkbenchScreen.class, 102, 48, 22, 15, PlaneWorkbenchRecipeCategory.RECIPE_TYPE);
    }
}
