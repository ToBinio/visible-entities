package tobinio.visibleentities.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import tobinio.visibleentities.VisibleEntities;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class InteractionEntityRenderer extends EntityRenderer<InteractionEntity, InteractionEntityRenderer.InteractionEntityRenderState> {
    public InteractionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public InteractionEntityRenderState createRenderState() {
        return new InteractionEntityRenderState();
    }

    @Override
    public void updateRenderState(InteractionEntity entity, InteractionEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.boundingBox = entity.getBoundingBox();
    }

    @Override
    public void render(InteractionEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light) {
        super.render(state, matrices, vertexConsumers, light);

        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showInteractions) {
            var vertexConsumer = vertexConsumers.getBuffer(RenderLayer.LINES);
            VertexRendering.drawBox(
                    matrices,
                    vertexConsumer,
                    state.boundingBox.offset(-state.x, -state.y, -state.z),
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
            );
        }
    }

    public static class InteractionEntityRenderState extends EntityRenderState {
        public Box boundingBox;
    }
}
