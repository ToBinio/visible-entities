package tobinio.visibleentities.settings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.text.Text;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Visible Entities"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Visible Entities"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show ItemFrames"))
                                .binding(Config.SHOW_ITEM_FRAMES_DEFAULT,
                                        () -> Config.HANDLER.instance().showItemFrames,
                                        newVal -> Config.HANDLER.instance().showItemFrames = newVal)
                                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Entities"))
                                .binding(Config.SHOW_ENTITIES_DEFAULT,
                                        () -> Config.HANDLER.instance().showEntities,
                                        newVal -> Config.HANDLER.instance().showEntities = newVal)
                                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Entity Transparency"))
                                .binding(Config.TRANSPARENCY_DEFAULT,
                                        () -> Config.HANDLER.instance().transparency,
                                        newVal -> Config.HANDLER.instance().transparency = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 255).step(1))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Interactions"))
                                .binding(Config.SHOW_INTERACTIONS_DEFAULT,
                                        () -> Config.HANDLER.instance().showInteractions,
                                        newVal -> Config.HANDLER.instance().showInteractions = newVal)
                                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Marker"))
                                .binding(Config.SHOW_MARKER_DEFAULT,
                                        () -> Config.HANDLER.instance().showMarker,
                                        newVal -> Config.HANDLER.instance().showMarker = newVal)
                                .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                                .build())
                        .build())
                .save(() -> {
                    Config.HANDLER.save();
                })
                .build()
                .generateScreen(parentScreen);
    }
}
