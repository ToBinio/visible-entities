package tobinio.visibleentities.mixin.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

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

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;getHeldItemStack()Lnet/minecraft/item/ItemStack;"), method = "render(Lnet/minecraft/entity/decoration/ItemFrameEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void renderTransparent(T itemFrameEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showItemFrames && itemFrameEntity.isInvisible()) {
            ItemStack itemStack = itemFrameEntity.getHeldItemStack();

            BakedModelManager bakedModelManager = this.blockRenderManager.getModels().getModelManager();
            ModelIdentifier modelIdentifier = this.getTransparentModelId(itemFrameEntity, itemStack);
            matrixStack.push();
            matrixStack.translate(-0.5F, -0.5F, -0.5F);
            this.blockRenderManager.getModelRenderer()
                    .render(matrixStack.peek(),
                            vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntityTranslucentCull()),
                            null,
                            bakedModelManager.getModel(modelIdentifier),
                            1.0F,
                            1.0F,
                            1.0F,
                            i,
                            OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;getMapId(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/component/type/MapIdComponent;"), method = "render(Lnet/minecraft/entity/decoration/ItemFrameEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void offSetMap(T itemFrameEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showItemFrames && itemFrameEntity.isInvisible()) {
            matrixStack.translate(0, 0, 0.4375F - 0.5F);
        }
    }

    @Unique
    private static final ModelIdentifier TRANSPARENT_NORMAL_FRAME = of("transparent_item_frame", false);
    @Unique
    private static final ModelIdentifier TRANSPARENT_MAP_FRAME = of("transparent_item_frame", true);
    @Unique
    private static final ModelIdentifier TRANSPARENT_GLOW_FRAME = of("transparent_glow_item_frame", false);
    @Unique
    private static final ModelIdentifier TRANSPARENT_MAP_GLOW_FRAME = of("transparent_glow_item_frame", true);

    @Unique
    private static ModelIdentifier of(String id, boolean map) {
        return new ModelIdentifier(VisibleEntitiesClient.id(id), "map=%s".formatted(map));
    }

    @Unique
    private ModelIdentifier getTransparentModelId(T entity, ItemStack stack) {

        boolean bl = entity.getType() == EntityType.GLOW_ITEM_FRAME;
        if (stack.isOf(Items.FILLED_MAP)) {
            return bl ? TRANSPARENT_MAP_GLOW_FRAME : TRANSPARENT_MAP_FRAME;
        } else {
            return bl ? TRANSPARENT_GLOW_FRAME : TRANSPARENT_NORMAL_FRAME;
        }
    }
}
