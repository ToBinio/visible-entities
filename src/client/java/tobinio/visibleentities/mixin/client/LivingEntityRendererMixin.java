package tobinio.visibleentities.mixin.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Redirect (method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void defineTransparency(EntityModel instance, MatrixStack matrixStack, VertexConsumer vertexConsumer,
            int light, int overlay, int color) {
        if (color != -1) {
            color = ColorHelper.Argb.getArgb(Config.HANDLER.instance().transparency, 255, 255, 255);
        }

        instance.render(matrixStack, vertexConsumer, light, overlay, color);
    }
}
