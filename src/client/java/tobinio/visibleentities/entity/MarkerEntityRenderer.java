package tobinio.visibleentities.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class MarkerEntityRenderer extends EntityRenderer<MarkerEntity, MarkerEntityRenderer.MarkerEntityRenderState> {
    public MarkerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public MarkerEntityRenderState createRenderState() {
        return new MarkerEntityRenderState();
    }

    @Override
    public void updateRenderState(MarkerEntity entity, MarkerEntityRenderer.MarkerEntityRenderState state,
            float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.boundingBox = entity.getBoundingBox();
    }

    @Override
    public void render(MarkerEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light) {
        super.render(state, matrices, vertexConsumers, light);

        Config instance = Config.HANDLER.instance();

        if (instance.isActive && instance.showMarker) {
            Box box = state.boundingBox.offset(-state.x, -state.y, -state.z);

            var vertexConsumerLine = vertexConsumers.getBuffer(RenderLayer.LINES);
            VertexRendering.drawBox(matrices, vertexConsumerLine, box, 1.0F, 1.0F, 1.0F, 1.0F);

            VertexConsumer vertexConsumerBox = vertexConsumers.getBuffer(RenderLayer.getDebugFilledBox());
            VertexRendering.drawFilledBox(matrices,
                    vertexConsumerBox,
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

        }
    }

    public static class MarkerEntityRenderState extends EntityRenderState {
        public Box boundingBox;
    }
}
