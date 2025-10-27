package tobinio.visibleentities.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.util.math.Box;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */

// not using custom EntityRenderState for mod compatibility
public class InteractionEntityRenderer extends EntityRenderer<InteractionEntity, EntityRenderState> {
    public InteractionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public InteractionEntityRenderState createRenderState() {
        return new InteractionEntityRenderState();
    }

    @Override
    public void updateRenderState(InteractionEntity entity, EntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);

        if(state instanceof InteractionEntityRenderState interactionEntityRenderState) {
            interactionEntityRenderState.boundingBox = entity.getBoundingBox();
        }
    }

    @Override
    public void render(EntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue,
            CameraRenderState cameraState) {
        super.render(state, matrices, queue, cameraState);

        if(state instanceof InteractionEntityRenderState interactionEntityRenderState) {
            Config instance = Config.HANDLER.instance();

            if (instance.isActive && instance.showInteractions) {
                queue.submitCustom(matrices,RenderLayer.LINES, (matricesEntry, vertexConsumer) -> {
                    VertexRendering.drawBox(
                            matricesEntry,
                            vertexConsumer,
                            interactionEntityRenderState.boundingBox.offset(-state.x, -state.y, -state.z),
                            1.0F,
                            1.0F,
                            1.0F,
                            1.0F
                    );
                });
            }
        }


    }

    public static class InteractionEntityRenderState extends EntityRenderState {
        public Box boundingBox;
    }
}
