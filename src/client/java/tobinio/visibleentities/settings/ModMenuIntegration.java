package tobinio.visibleentities.settings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.text.Text;

import java.util.ArrayList;

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
                        .name(Text.literal("General"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Entity Transparency"))
                                .binding(Config.TRANSPARENCY_DEFAULT,
                                        () -> Config.HANDLER.instance().transparency,
                                        newVal -> Config.HANDLER.instance().transparency = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 255).step(1))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Invisible"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Invisible"))
                                .description(OptionDescription.of(Text.literal(
                                        "If invisible entities should be shown when the mod is active")))
                                .binding(Config.SHOW_ENTITIES_DEFAULT,
                                        () -> Config.HANDLER.instance().showEntities,
                                        newVal -> Config.HANDLER.instance().showEntities = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Config.ListType>createBuilder()
                                .name(Text.literal("Filter List Type"))
                                .binding(Config.ListType.BLACKLIST,
                                        () -> Config.HANDLER.instance().listType,
                                        newVal -> Config.HANDLER.instance().listType = newVal)
                                .controller(opt -> EnumControllerBuilder.create(opt)
                                        .enumClass(Config.ListType.class)
                                        .formatValue(t -> Text.of(t.name().toLowerCase())))
                                .build())
                        .option(ListOption.<String>createBuilder()
                                .name(Text.literal("Filter List"))
                                .description(OptionDescription.of(Text.literal(
                                        "List of entity translation keys for rendering when transparent (blacklist/whitelist):\n 'entity.minecraft.chicken' or 'self' to refer to the player.")))
                                .binding(new ArrayList<>(),
                                        () -> Config.HANDLER.instance().entityKeys,
                                        newVal -> Config.HANDLER.instance().entityKeys = newVal)
                                .controller(StringControllerBuilder::create)
                                .initial("")
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Special"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show ItemFrames"))
                                .description(OptionDescription.of(Text.literal(
                                        "If invisible ItemFrames should be shown when the mod is active")))
                                .binding(Config.SHOW_ITEM_FRAMES_DEFAULT,
                                        () -> Config.HANDLER.instance().showItemFrames,
                                        newVal -> Config.HANDLER.instance().showItemFrames = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Interactions"))
                                .description(OptionDescription.of(Text.literal(
                                        "If Interactions should be shown when the mod is active")))
                                .binding(Config.SHOW_INTERACTIONS_DEFAULT,
                                        () -> Config.HANDLER.instance().showInteractions,
                                        newVal -> Config.HANDLER.instance().showInteractions = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Marker"))
                                .description(OptionDescription.of(Text.literal(
                                        "If Marker should be shown when the mod is active")))
                                .binding(Config.SHOW_MARKER_DEFAULT,
                                        () -> Config.HANDLER.instance().showMarker,
                                        newVal -> Config.HANDLER.instance().showMarker = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .save(() -> {
                    Config.HANDLER.save();
                })
                .build()
                .generateScreen(parentScreen);
    }
}
