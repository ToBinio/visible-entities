package tobinio.visibleentities.entity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class MarkerEntityRenderer extends EntityRenderer<MarkerEntity> {
    public MarkerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(MarkerEntity entity) {
        return null;
    }

    @Override
    public void render(MarkerEntity entity, float yaw, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light) {

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        Config instance = Config.HANDLER.instance();

        if (instance.isActive && instance.showMarker) {
            Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());

            var vertexConsumerLine = vertexConsumers.getBuffer(RenderLayer.LINES);
            WorldRenderer.drawBox(matrices, vertexConsumerLine, box, 1.0F, 1.0F, 1.0F, 1.0F);

            VertexConsumer vertexConsumerBox = vertexConsumers.getBuffer(RenderLayer.getDebugFilledBox());
            WorldRenderer.renderFilledBox(matrices,
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
}
