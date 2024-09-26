package tobinio.visibleentities.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

/**
 * Created: 31.08.24
 *
 * @author Tobias Frischmann
 */
@Mixin (Entity.class)
public class EntityMixin {
    @Inject (method = "isInvisibleTo", at = @At ("HEAD"), cancellable = true)
    private void overwriteVisibility(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showEntities) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
