package tobinio.visibleentities.mixin.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.Sprite;
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
    @Redirect (method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void defineTransparency(OrderedRenderCommandQueue instance, Model model, Object o, MatrixStack matrixStack,
            RenderLayer renderLayer, int light, int overlay, int tintColor, Sprite sprite, int outlineColor,
            ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
        LivingEntityRenderState livingEntityRenderState = (LivingEntityRenderState) o;

        if (tintColor != -1) {
            tintColor = ColorHelper.getArgb(Config.HANDLER.instance().transparency, ColorHelper.getRed(tintColor), ColorHelper.getGreen(tintColor), ColorHelper.getBlue(tintColor));
        }

        instance.submitModel(
                model, livingEntityRenderState, matrixStack, renderLayer, livingEntityRenderState.light, overlay, tintColor, null, livingEntityRenderState.outlineColor, null
        );
    }
}
