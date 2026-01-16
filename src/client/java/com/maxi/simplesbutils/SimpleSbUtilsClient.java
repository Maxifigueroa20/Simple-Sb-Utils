package com.maxi.simplesbutils;

import com.maxi.simplesbutils.features.DayOverlay;
import com.maxi.simplesbutils.features.LobbyTracker;
import net.fabricmc.api.ClientModInitializer;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;

public class SimpleSbUtilsClient implements ClientModInitializer {

	// Instancias de nuestros módulos
	private final DayOverlay dayOverlay = new DayOverlay();
	private final LobbyTracker lobbyTracker = new LobbyTracker();

	@Override
	public void onInitializeClient() {
		// 1. Inicializar el renderizado (aunque empieza apagado)
		dayOverlay.register();

		// 2. Suscribirse a la API de Hypixel
		HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);

		// 3. Manejar el cambio de ubicación (El cerebro del mod)
		HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, this::handleLocationUpdate);
	}

	private void handleLocationUpdate(ClientboundLocationPacket packet) {
		// Extraer datos con seguridad (Optional)
		String mode = packet.getMode().orElse("");
		String map = packet.getMap().orElse("");
		String serverName = packet.getServerName();

		// Verificación estricta: SOLO Crystal Hollows
		// Ignoramos "mining_3" deliberadamente como pediste
		boolean inCrystalHollows = mode.equalsIgnoreCase("crystal_hollows") ||
				map.equalsIgnoreCase("Crystal Hollows");

		// Activar o desactivar módulos según la zona
		dayOverlay.setEnabled(inCrystalHollows);

		if (inCrystalHollows) {
			// Solo rastreamos si estamos dentro de la zona objetivo
			lobbyTracker.onServerChange(serverName);
		}
	}
}