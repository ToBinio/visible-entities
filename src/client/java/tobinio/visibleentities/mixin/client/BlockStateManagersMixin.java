package tobinio.visibleentities.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStateManagers;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

import static tobinio.visibleentities.entity.TransparentItemFrameBlockStateMangers.*;


/**
 * Created: 14.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockStateManagers.class)
public abstract class BlockStateManagersMixin {

    @Mutable
    @Shadow
    @Final
    private static Map<Identifier, StateManager<Block, BlockState>> STATIC_MANAGERS;

    @Inject (method = "<clinit>", at = @At ("TAIL"))
    private static void addCustomItemFrameModels(CallbackInfo ci) {
        STATIC_MANAGERS = new HashMap<>(STATIC_MANAGERS);

        STATIC_MANAGERS.put(TRANSPARENT_ITEM_FRAME_ID, TRANSPARENT_ITEM_FRAME);
        STATIC_MANAGERS.put(TRANSPARENT_GLOW_ITEM_FRAME_ID, TRANSPARENT_GLOW_ITEM_FRAME);
    }
}
