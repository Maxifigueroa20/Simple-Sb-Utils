package com.maxi.simplesbutils;

import com.maxi.simplesbutils.features.DayOverlay;
import com.maxi.simplesbutils.features.LobbyTracker;
import net.fabricmc.api.ClientModInitializer;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;

public class SimpleSbUtilsClient implements ClientModInitializer {
	private final DayOverlay dayOverlay = new DayOverlay();
	private final LobbyTracker lobbyTracker = new LobbyTracker();

	@Override
	public void onInitializeClient() {
		dayOverlay.register();

		HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);
		HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, this::handleLocationUpdate);
	}

	private void handleLocationUpdate(ClientboundLocationPacket packet) {
		String mode = packet.getMode().orElse("");
		String map = packet.getMap().orElse("");
		String serverName = packet.getServerName();

		boolean inCrystalHollows = mode.equalsIgnoreCase("crystal_hollows") ||
				map.equalsIgnoreCase("Crystal Hollows");

		dayOverlay.setEnabled(inCrystalHollows);

		if (inCrystalHollows) {
			lobbyTracker.onServerChange(serverName);
		}
	}
}