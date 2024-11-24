package tobinio.visibleentities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created: 26.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (MarkerEntity.class)
public class MarkerEntityMixin {
    @Inject (method = "createSpawnPacket", at = @At ("HEAD"), cancellable = true)
    public void createSpawnPacket(EntityTrackerEntry entityTrackerEntry,
            CallbackInfoReturnable<Packet<ClientPlayPacketListener>> cir) {
        cir.setReturnValue(new EntitySpawnS2CPacket((Entity) (Object) this, entityTrackerEntry));
    }

}