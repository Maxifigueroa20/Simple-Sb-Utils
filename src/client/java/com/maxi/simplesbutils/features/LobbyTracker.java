package com.maxi.simplesbutils.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class LobbyTracker {
    private final Set<String> visitedServers = new HashSet<>();

    public void onServerChange(String serverId) {
        if (serverId == null || serverId.isEmpty())
            return;

        if (visitedServers.contains(serverId)) {
            notifyUser(serverId);
        } else {
            visitedServers.add(serverId);
        }
    }

    private void notifyUser(String serverId) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {

            client.inGameHud.setTitleTicks(10, 50, 20);

            Text title = Text.literal("¡SERVIDOR REPETIDO!")
                    .formatted(Formatting.RED, Formatting.BOLD);

            Text subtitle = Text.literal("Ya visitaste la instancia: " + serverId)
                    .formatted(Formatting.GRAY);

            client.inGameHud.setTitle(title);
            client.inGameHud.setSubtitle(subtitle);

            Text message = Text.literal("[SbUtils] ")
                    .formatted(Formatting.GREEN)
                    .append(Text.literal("¡Ya visitaste este servidor! ")
                            .formatted(Formatting.RED))
                    .append(Text.literal("(" + serverId + ")")
                            .formatted(Formatting.GRAY));

            client.player.sendMessage(message, false);
        }
    }
}