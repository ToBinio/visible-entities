package tobinio.visibleentities.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.visibleentities.settings.Config;

import static tobinio.visibleentities.entity.TransparentItemFrameBlockStateMangers.TRANSPARENT_GLOW_ITEM_FRAME;
import static tobinio.visibleentities.entity.TransparentItemFrameBlockStateMangers.TRANSPARENT_ITEM_FRAME;

/**
 * Created: 14.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin<T extends ItemFrameEntity> {
    @Shadow
    @Final
    private BlockRenderManager blockRenderManager;

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V", ordinal = 1), method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V")
    private void renderTransparent(ItemFrameEntityRenderState state, MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        Config instance = Config.HANDLER.instance();

        if (instance.isActive && instance.showItemFrames && state.invisible) {
            BlockState blockState = this.getTransparentModelId(state);
            BlockStateModel model = this.blockRenderManager.getModel(blockState);

            matrixStack.push();
            matrixStack.translate(-0.5F, -0.5F, -0.5F);

            orderedRenderCommandQueue.submitBlockStateModel(matrixStack, RenderLayers.entityTranslucent(
                            SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE), model,
                    1.0F,
                    1.0F,
                    1.0F,
                    state.light,
                    OverlayTexture.DEFAULT_UV,
                    state.outlineColor
                    );
            matrixStack.pop();
        }
    }

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V", ordinal = 2), method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V")
    private void offSetMap(ItemFrameEntityRenderState state, MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        Config instance = Config.HANDLER.instance();

        if (instance.isActive && instance.showItemFrames && state.invisible) {
            matrixStack.translate(0, 0, 0.4375F - 0.5F);
        }
    }

    @Unique
    private BlockState getTransparentModelId(ItemFrameEntityRenderState state) {
        return ((state.glow ? TRANSPARENT_GLOW_ITEM_FRAME : TRANSPARENT_ITEM_FRAME).getDefaultState()).with(Properties.MAP,
                state.mapId != null);

    }
}
