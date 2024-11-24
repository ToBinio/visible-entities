package tobinio.visibleentities.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.visibleentities.VisibleEntitiesClient;
import tobinio.visibleentities.settings.Config;

import java.util.List;
import java.util.UUID;

/**
 * Created: 31.08.24
 *
 * @author Tobias Frischmann
 */
@Mixin (Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    public abstract UUID getUuid();

    @Inject (method = "isInvisibleTo", at = @At ("HEAD"), cancellable = true)
    private void overwriteVisibility(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (isInFilterList(player) == (Config.HANDLER.instance().listType == Config.ListType.BLACKLIST)) {
            return;
        }

        if (VisibleEntitiesClient.isActive && Config.HANDLER.instance().showEntities) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Unique
    private boolean isInFilterList(PlayerEntity renderingPlayer) {
        List<String> entityKeys = Config.HANDLER.instance().entityKeys;

        if (this.getUuid() == renderingPlayer.getUuid() && entityKeys.contains("self")) {
            return true;
        }

        var key = getType().getTranslationKey();
        return entityKeys.contains(key);
    }
}
