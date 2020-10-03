package xyz.przemyk.simpleplanes.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

public interface RenderHudEvents {
    Event<RenderHudEvents> EVENT = EventFactory.createArrayBacked(RenderHudEvents.class,
        (listeners) -> (stage,  matrixStack) -> {
            for (RenderHudEvents event : listeners) {
                ActionResult result = event.render(stage,  matrixStack);
                if (result != ActionResult.PASS) {
                    return result;
                }
            }
            return ActionResult.PASS;
        }
    );

    ActionResult render(Stage stage, MatrixStack matrixStack);

    public enum Stage {
        FIRST,
        EXP,
        LAST,

    }
}
