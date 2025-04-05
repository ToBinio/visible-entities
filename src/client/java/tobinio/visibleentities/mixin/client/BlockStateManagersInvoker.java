package tobinio.visibleentities.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStateManagers;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


/**
 * Created: 14.09.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockStateManagers.class)
public interface BlockStateManagersInvoker {

    @Invoker ("createItemFrameStateManager")
    static StateManager<Block, BlockState> createItemFrameStateManager() {
        throw new AssertionError();
    }
}
