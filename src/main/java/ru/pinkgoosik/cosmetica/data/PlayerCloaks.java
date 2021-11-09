package ru.pinkgoosik.cosmetica.data;

import com.google.gson.*;
import ru.pinkgoosik.cosmetica.CosmeticaMod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlayerCloaks {
	public static final ArrayList<Entry> ENTRIES = new ArrayList<>();
	public static final ArrayList<String> CLOAK_NAMES = new ArrayList<>(List.of("uni", "azure", "crimson", "flamingo", "golden",
			"lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee", "pride"));
	public static final String CLOAKS_DATA_URL = "https://pinkgoosik.ru/data/cloaks.json";

	public static void reload() {
		try{
			ENTRIES.clear();
			URL url = new URL(CLOAKS_DATA_URL);
			URLConnection request = url.openConnection();
			request.connect();
			JsonParser parser = new JsonParser();
			InputStream stream = request.getInputStream();
			JsonArray array = parser.parse(new InputStreamReader(stream)).getAsJsonArray();
			array.forEach(element -> {
				if(element.isJsonObject()){
					JsonObject object = element.getAsJsonObject();
					try {
						String name, uuid, cloak;
						name = object.get("name").getAsString();
						uuid = object.get("uuid").getAsString();
						cloak = object.get("cloak").getAsString();
						ENTRIES.add(new Entry(name, uuid, cloak));
					}catch (ClassCastException ignored){}
				}
			});
		} catch (IOException | JsonIOException | JsonSyntaxException e) {
			PlayerCloaks.ENTRIES.clear();
			CosmeticaMod.LOGGER.warn("Failed to load Player Cloaks due to an exception: " + e);
		} finally {
			if (!PlayerCloaks.ENTRIES.isEmpty()){
				CosmeticaMod.LOGGER.info("Player Cloaks successfully loaded");
				CachedPlayerCloaks.save();
			}
			else CachedPlayerCloaks.load();
		}
	}

	public static record Entry(String name, String uuid, String cloak) {}
}
