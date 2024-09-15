package tobinio.visibleentities.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.visibleentities.VisibleEntities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created: 14.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockStatesLoader.class)
public class BlockStatesLoaderMixin {
    @Mutable
    @Shadow
    @Final
    private static Map<Identifier, StateManager<Block, BlockState>> STATIC_DEFINITIONS;

    @Shadow
    @Final
    private static StateManager<Block, BlockState> ITEM_FRAME_STATE_MANAGER;

    @Inject (method = "<clinit>", at = @At ("TAIL"))
    private static void modifySnowball(CallbackInfo ci) {
        STATIC_DEFINITIONS = new HashMap<>();

        STATIC_DEFINITIONS.put(Identifier.ofVanilla("item_frame"), ITEM_FRAME_STATE_MANAGER);
        STATIC_DEFINITIONS.put(Identifier.ofVanilla("glow_item_frame"), ITEM_FRAME_STATE_MANAGER);
        STATIC_DEFINITIONS.put(VisibleEntities.id("transparent_item_frame"),
                ITEM_FRAME_STATE_MANAGER);
        STATIC_DEFINITIONS.put(VisibleEntities.id("transparent_glow_item_frame"),
                ITEM_FRAME_STATE_MANAGER);
    }
}
