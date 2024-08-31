package tobinio.visibleentities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.visibleentities.VisibleEntities;

/**
 * Created: 31.08.24
 *
 * @author Tobias Frischmann
 */
@Mixin (Entity.class)
public class EntityMixin {
    @Inject (method = "isInvisibleTo", at = @At ("HEAD"), cancellable = true)
    private void isInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (VisibleEntities.isActive) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
