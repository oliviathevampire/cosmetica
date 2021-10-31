package ru.pinkgoosik.cosmetica.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlayerCloaks {
	public static final ArrayList<Entry> ENTRIES = new ArrayList<>();
	public static final ArrayList<String> CLOAKS = new ArrayList<>(List.of("uni", "azure", "crimson", "flamingo", "golden",
			"lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee"));
	public static final String CLOAKS_DATA = "https://pinkgoosik.ru/data/cloaks.json";

	public static void reload() throws IOException {
		ENTRIES.clear();
		URL url = new URL(CLOAKS_DATA);
		URLConnection request = url.openConnection();
		request.connect();
		JsonParser parser = new JsonParser();
		InputStream stream = request.getInputStream();
		JsonArray array = parser.parse(new InputStreamReader(stream)).getAsJsonArray();
		array.forEach(element -> {
			JsonObject object = element.getAsJsonObject();
			String name, uuid, cloak;
			name = object.get("name").getAsString();
			uuid = object.get("uuid").getAsString();
			cloak = object.get("cloak").getAsString();
			ENTRIES.add(new Entry(name, uuid, cloak));
		});
	}

	public static ArrayList<Entry> getDefaultOwners(){
		ArrayList<Entry> defaults = new ArrayList<>();
		defaults.add(new Entry("PinkGoosik", "59abdc87-d23e-4877-beaf-a7bb84b15697", "uni"));
		defaults.add(new Entry("aSomik", "b80d1914-d70a-410f-b4bd-90e384520470", "violet"));
		return defaults;
	}

	public static record Entry(String name, String uuid, String cloak) {}
}
