package ru.pinkgoosik.cosmetica.data;

import com.google.gson.*;
import ru.pinkgoosik.cosmetica.CosmeticaMod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PlayerCloaks {
	public static final ArrayList<Entry> ENTRIES = new ArrayList<>();
	private static final String CLOAKS_DATA_URL = "https://pinkgoosik.ru/data/cloaks.json";

	public static void reload() {
		try{
			ENTRIES.clear();
			URL url = new URL(CLOAKS_DATA_URL);
			URLConnection request = url.openConnection();
			request.connect();
			JsonParser parser = new JsonParser();
			InputStream stream = request.getInputStream();
			JsonElement element = parser.parse(new InputStreamReader(stream));
			if(element.isJsonArray()){
				element.getAsJsonArray().forEach(entry -> {
					if(entry.isJsonObject()){
						JsonObject object = entry.getAsJsonObject();
						if(object.get("name") != null && object.get("uuid") != null && object.get("cloak") != null){
							try {
								String name, uuid, cloak;
								name = object.get("name").getAsString();
								uuid = object.get("uuid").getAsString();
								cloak = object.get("cloak").getAsString();
								ENTRIES.add(new Entry(name, uuid, cloak));
							}catch (ClassCastException | IllegalStateException ignored){}
						}
					}
				});
			}else {
				CosmeticaMod.LOGGER.warn(CLOAKS_DATA_URL + " is not a json array");
			}
		} catch (IOException | JsonIOException | JsonSyntaxException e) {
			PlayerCloaks.ENTRIES.clear();
			CosmeticaMod.LOGGER.warn("Failed to load Player Cloaks due to an exception: " + e);
		} finally {
			if (!PlayerCloaks.ENTRIES.isEmpty()){
				CosmeticaMod.LOGGER.info("Player Cloaks successfully loaded");
				CachedPlayerCloaks.save();
			}
			else {
				CosmeticaMod.LOGGER.info("Something went wrong, loading cloaks from cache");
				CachedPlayerCloaks.load();
			}
		}
	}

	public static record Entry(String name, String uuid, String cloak) {}
}
