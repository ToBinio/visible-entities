package tobinio.visibleentities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
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
    protected static RegistryKey<EntityType<?>> keyOf(String id) {
        return null;
    }

    @Shadow
    protected static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key,
            EntityType.Builder<T> type) {
        return null;
    }

    @Inject (method = "register(Ljava/lang/String;Lnet/minecraft/entity/EntityType$Builder;)Lnet/minecraft/entity/EntityType;", at = @At ("HEAD"), cancellable = true)
    private static <T extends Entity> void registerMarker(String id, EntityType.Builder<T> type,
            CallbackInfoReturnable<EntityType<T>> cir) {

        if (id.equals("marker")) {
            var result = register(keyOf(id),
                    EntityType.Builder.create(MarkerEntity::new, SpawnGroup.MISC)
                            .dimensions(0.15F, 0.15F)
                            .maxTrackingRange(4));

            cir.setReturnValue((EntityType<T>) result);
            cir.cancel();
        }
    }
}
