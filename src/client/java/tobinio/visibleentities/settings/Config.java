package tobinio.visibleentities.settings;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import tobinio.visibleentities.VisibleEntities;

import java.util.ArrayList;
import java.util.List;

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

    @SerialEntry
    public boolean isActive = false;

    @SerialEntry
    public boolean renderEntities = true;

    @SerialEntry
    public boolean showItemFrames = true;

    @SerialEntry
    public boolean showEntities = true;

    @SerialEntry
    public boolean showInteractions = true;

    @SerialEntry
    public boolean showMarker = true;

    public static final int TRANSPARENCY_DEFAULT = 38;
    @SerialEntry
    public int transparency = TRANSPARENCY_DEFAULT;

    @SerialEntry
    public List<String> entityKeys = new ArrayList<>();

    @SerialEntry
    public ListType listType = ListType.BLACKLIST;

    public enum ListType {
        BLACKLIST,
        WHITELIST,
    }
}
