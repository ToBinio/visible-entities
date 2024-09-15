package tobinio.visibleentities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tobinio.visibleentities.entity.InteractionEntityRenderer;
import tobinio.visibleentities.settings.Config;

public class VisibleEntities implements ClientModInitializer {
    public static final String MOD_ID = "visible-entities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final KeyBinding TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.visible-entities.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "category.visible-entities.visible-entities"));

    public static Boolean isActive = false;

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
        EntityRendererRegistry.register(EntityType.INTERACTION, InteractionEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_KEY.wasPressed()) {
                client.player.sendMessage(Text.translatable(isActive ? "visible-entities.disabled" : "visible-entities.enabled")
                        .formatted(Formatting.GRAY), true);
                isActive = !isActive;
            }
        });
    }
}