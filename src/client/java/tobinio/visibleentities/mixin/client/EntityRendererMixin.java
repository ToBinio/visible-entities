package tobinio.visibleentities.mixin.client;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.visibleentities.VisibleEntitiesClient;

/**
 * Created: 28.10.24
 *
 * @author Tobias Frischmann
 */
@Mixin (EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject (at = @At ("HEAD"), method = "shouldRender", cancellable = true)
    private void shouldRender(T entity, Frustum frustum, double x, double y, double z,
            CallbackInfoReturnable<Boolean> cir) {
        if (!VisibleEntitiesClient.showEntities) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
