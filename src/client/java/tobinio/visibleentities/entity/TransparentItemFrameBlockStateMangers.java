package tobinio.visibleentities.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import tobinio.visibleentities.VisibleEntitiesClient;

import static tobinio.visibleentities.mixin.client.BlockStateManagersInvoker.createItemFrameStateManager;

/**
 * Created: 05.04.25
 *
 * @author Tobias Frischmann
 */
public class TransparentItemFrameBlockStateMangers {
    public static final StateManager<Block, BlockState> TRANSPARENT_ITEM_FRAME = createItemFrameStateManager();

    public static final StateManager<Block, BlockState> TRANSPARENT_GLOW_ITEM_FRAME = createItemFrameStateManager();

    public static final Identifier TRANSPARENT_ITEM_FRAME_ID = VisibleEntitiesClient.id("transparent_item_frame");
    public static final Identifier TRANSPARENT_GLOW_ITEM_FRAME_ID = VisibleEntitiesClient.id(
            "transparent_glow_item_frame");

}
