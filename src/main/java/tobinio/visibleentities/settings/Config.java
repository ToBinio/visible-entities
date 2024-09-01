package tobinio.visibleentities.settings;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import tobinio.visibleentities.VisibleEntities;

/**
 * Created: 01.09.24
 *
 * @author Tobias Frischmann
 */
public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of(VisibleEntities.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("visible_entities.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public static final boolean SHOW_ENTITIES_DEFAULT = true;
    @SerialEntry
    public boolean showEntities = SHOW_ENTITIES_DEFAULT;

    public static final boolean SHOW_INTERACTIONS_DEFAULT = true;
    @SerialEntry
    public boolean showInteractions = SHOW_INTERACTIONS_DEFAULT;

    public static final int TRANSPARENCY_DEFAULT = 28;
    @SerialEntry
    public int transparency = TRANSPARENCY_DEFAULT;
}
