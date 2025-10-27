package tobinio.visibleentities.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.util.math.Box;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */

// not using custom EntityRenderState for mod compatibility
public class MarkerEntityRenderer extends EntityRenderer<MarkerEntity, EntityRenderState> {
    public MarkerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public MarkerEntityRenderState createRenderState() {
        return new MarkerEntityRenderState();
    }

    @Override
    public void updateRenderState(MarkerEntity entity, EntityRenderState state,
            float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);

        if(state instanceof MarkerEntityRenderState markerEntityRenderState) {
            markerEntityRenderState.boundingBox = entity.getBoundingBox();
        }
    }

    @Override
    public void render(EntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue,
            CameraRenderState cameraState) {
        super.render(state, matrices, queue, cameraState);

        Config instance = Config.HANDLER.instance();

        if(state instanceof MarkerEntityRenderState markerEntityRenderState) {
            if (instance.isActive && instance.showMarker) {
                Box box = markerEntityRenderState.boundingBox.offset(-state.x, -state.y - (state.height / 2), -state.z);

                queue.submitCustom(matrices, RenderLayer.LINES, (matricesEntry, vertexConsumer) -> {
                    VertexRendering.drawBox(matricesEntry, vertexConsumer, box, 1.0F, 1.0F, 1.0F, 1.0F);
                });

                queue.submitCustom(matrices, RenderLayer.getDebugFilledBox(), (matricesEntry, vertexConsumer) -> {
                    MatrixStack matrixStack = new MatrixStack();
                    matrixStack.peek().copy(matricesEntry);
                    VertexRendering.drawFilledBox(matrixStack,
                            vertexConsumer,
                            box.minX,
                            box.minY,
                            box.minZ,
                            box.maxX,
                            box.maxY,
                            box.maxZ,
                            0.8F,
                            0.4F,
                            1.0F,
                            0.5F);
                });
            }
        }
    }

    public static class MarkerEntityRenderState extends EntityRenderState {
        public Box boundingBox;
    }
}
