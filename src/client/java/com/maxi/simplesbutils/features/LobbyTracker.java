package com.maxi.simplesbutils.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class LobbyTracker {
    // Usamos un Set porque la búsqueda es instantánea (O(1)) y evita duplicados
    // automáticamente
    private final Set<String> visitedServers = new HashSet<>();

    /**
     * Se llama SOLO cuando se confirma que estamos entrando a una instancia de
     * Crystal Hollows.
     */
    public void onServerChange(String serverId) {
        if (serverId == null || serverId.isEmpty())
            return;

        if (visitedServers.contains(serverId)) {
            // Caso: Servidor repetido -> Notificar al usuario
            notifyUser(serverId);
        } else {
            // Caso: Servidor nuevo -> Registrar y silencio absoluto
            visitedServers.add(serverId);
        }
    }

    private void notifyUser(String serverId) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // Mensaje formateado: [SbUtils] Servidor repetido detectado (mXXX)
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