package xyz.przemyk.simpleplanes.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface OnRenderEvent {
    public interface PRE{
        Event<OnRenderEvent.PRE> EVENT = EventFactory.createArrayBacked(OnRenderEvent.PRE.class,
            (listeners) -> (entity, tickDelta, matrices, vertexConsumers, light) -> {
                for (OnRenderEvent.PRE event : listeners) {
                    ActionResult result = event.pre(entity, tickDelta, matrices, vertexConsumers, light);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
        );
        ActionResult pre(Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
    }
    public interface POST{
        Event<OnRenderEvent.POST> EVENT = EventFactory.createArrayBacked(OnRenderEvent.POST.class,
            (listeners) -> (entity, tickDelta, matrices, vertexConsumers, light) -> {
                for (OnRenderEvent.POST event : listeners) {
                    ActionResult result = event.post(entity, tickDelta, matrices, vertexConsumers, light);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
        );
        ActionResult post(Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
    }
}
