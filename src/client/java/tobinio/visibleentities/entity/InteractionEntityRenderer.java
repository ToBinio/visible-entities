package tobinio.visibleentities.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.util.Identifier;
import tobinio.visibleentities.VisibleEntities;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class InteractionEntityRenderer extends EntityRenderer<InteractionEntity> {
    public InteractionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(InteractionEntity entity) {
        return null;
    }

    @Override
    public void render(InteractionEntity entity, float yaw, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showInteractions) {
            var vertexConsumer = vertexConsumers.getBuffer(RenderLayer.LINES);
            WorldRenderer.drawBox(
                    matrices,
                    vertexConsumer,
                    entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ()),
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
            );
        }
    }
}
