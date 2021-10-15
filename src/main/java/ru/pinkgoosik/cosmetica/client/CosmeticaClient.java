package ru.pinkgoosik.cosmetica.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CosmeticaClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cosmetica");
    private static final int FORMAT_VERSION = 0;

	private static PlayerCapes playerCapes;
	private static PlayerCosmetics playerCosmetics;

	@Override
	public void onInitializeClient() {
        if(!isCompatibleFormat()) return;
		ClientTickEvents.END_CLIENT_TICK.register(new LoadCosmeticsEvent());

//		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//			if(entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer){
//				registrationHelper.register(new CosmeticFeatureRenderer(playerEntityRenderer));
//			}
//		});
	}

	public static void initPlayerCosmetics() {
		LOGGER.info("Loading Player Cosmetics...");
		try {
			playerCosmetics = new PlayerCosmetics();
		} catch (IOException e) {
			playerCosmetics = null;
			LOGGER.warn("Failed to load Player Cosmetics due to an exception: " + e);
		} finally {
			if (playerCosmetics != null) LOGGER.info("Player Cosmetics successfully loaded");
		}
	}

	public static void initPlayerCapes() {
		LOGGER.info("Loading Player Capes...");
		try {
			playerCapes = new PlayerCapes();
		} catch (IOException e) {
			playerCapes = null;
			LOGGER.warn("Failed to load Player Capes due to an exception: " + e);
		} finally {
			if (playerCapes != null) LOGGER.info("Player Capes successfully loaded");
		}
	}

	public static PlayerCapes getPlayerCapes() {
		return playerCapes;
	}

	public static PlayerCosmetics getPlayerCosmetics() {
		return playerCosmetics;
	}

    private boolean isCompatibleFormat(){
        try{
            URL url = new URL("https://pinkgoosik.ru/data/cosmetica.json");
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(new InputStreamReader((InputStream)request.getContent())).getAsJsonObject();
            return object.getAsJsonObject().get("formatVersion").getAsInt() == FORMAT_VERSION;
        } catch (IOException ignored) {}
        return false;
    }
}
