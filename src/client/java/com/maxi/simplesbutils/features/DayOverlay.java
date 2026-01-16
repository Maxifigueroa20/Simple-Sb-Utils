package com.maxi.simplesbutils.features;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class DayOverlay {
    private boolean enabled = false;
    private long cachedDay = -1;
    private String cachedText = "";

    public void register() {
        HudElementRegistry.addLast(
                Identifier.of("simplesbutils", "day_counter"),
                (drawContext, tickCounter) -> this.render(drawContext));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void render(DrawContext drawContext) {
        // Si no estamos en Crystal Hollows, dormimos inmediatamente (Cero recursos)
        if (!enabled)
            return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null || client.options.hudHidden) {
            return;
        }

        // Lógica de Caché
        long worldTime = client.world.getTimeOfDay();
        long currentDay = worldTime / 24000L;

        if (currentDay != cachedDay) {
            cachedDay = currentDay;
            cachedText = "Day: " + cachedDay;
        }

        // Dibujado
        TextRenderer textRenderer = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int textWidth = textRenderer.getWidth(cachedText);
        int padding = 4;

        drawContext.drawText(textRenderer, cachedText, width - textWidth - padding, padding, 0xFFFFFFFF, true);
    }
}