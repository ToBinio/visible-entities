package tobinio.visibleentities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created: 26.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (EntityType.class)
public abstract class EntityTypeMixin {
    @Shadow
    @Mutable
    @Final
    public static EntityType<MarkerEntity> MARKER;

    @Shadow

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return null;
    }

    @Inject (method = "<clinit>", at = @At ("TAIL"))
    private static void setMarkerMaxTrackingRange(CallbackInfo ci) {
        MARKER = register("marker",
                EntityType.Builder.create(MarkerEntity::new, SpawnGroup.MISC)
                        .dimensions(0.15F, 0.15F)
                        .maxTrackingRange(4));
    }
}
