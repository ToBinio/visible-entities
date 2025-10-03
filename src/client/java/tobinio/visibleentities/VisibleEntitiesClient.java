package tobinio.visibleentities;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import tobinio.visibleentities.entity.InteractionEntityRenderer;
import tobinio.visibleentities.entity.MarkerEntityRenderer;
import tobinio.visibleentities.settings.Config;

import static tobinio.visibleentities.VisibleEntities.MOD_ID;

public class VisibleEntitiesClient implements ClientModInitializer {
    public static final KeyBinding.Category VISIBLE_ENTITIES = KeyBinding.Category.create(id("visible-entities"));

    private static final KeyBinding TOGGLE_ACTIVE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.visible-entities.active",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            VISIBLE_ENTITIES));

    private static final KeyBinding TOGGLE_VISIBILITY_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.visible-entities.visibility",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            VISIBLE_ENTITIES));

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
        EntityRendererRegistry.register(EntityType.INTERACTION, InteractionEntityRenderer::new);
        EntityRendererRegistry.register(EntityType.MARKER, MarkerEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_ACTIVE_KEY.wasPressed()) {
                boolean isActive = Config.HANDLER.instance().isActive;

                client.player.sendMessage(Text.translatable(isActive ? "visible-entities.active.disabled" : "visible-entities.active.enabled")
                        .formatted(Formatting.GRAY), true);

                Config.HANDLER.instance().isActive = !isActive;
                Config.HANDLER.save();
            }

            while (TOGGLE_VISIBILITY_KEY.wasPressed()) {
                boolean renderEntities = Config.HANDLER.instance().renderEntities;

                client.player.sendMessage(Text.translatable(renderEntities ? "visible-entities.visibility.disabled" : "visible-entities.visibility.enabled")
                        .formatted(Formatting.GRAY), true);

                Config.HANDLER.instance().renderEntities = !renderEntities;
                Config.HANDLER.save();
            }
        });
    }
}