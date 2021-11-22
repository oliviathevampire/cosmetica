package ru.pinkgoosik.cosmetica.data;

import com.google.gson.*;
import ru.pinkgoosik.cosmetica.CosmeticaMod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEntries {
	public static final List<Entry> ENTRIES = new ArrayList<>();
	private static final String ENTRIES_DATA_URL = "https://pinkgoosik.ru/data/entries.json";

	public static void reload() {
		try {
			ENTRIES.clear();
			URL url = new URL(ENTRIES_DATA_URL);
			URLConnection request = url.openConnection();
			request.connect();
			InputStream stream = request.getInputStream();
			try (InputStreamReader reader = new InputStreamReader(stream)) {
				Entry[] entry = CosmeticaMod.GSON.fromJson(reader, Entry[].class);
				ENTRIES.addAll(Arrays.asList(entry));
			}
		} catch (IOException | JsonIOException | JsonSyntaxException e) {
			PlayerEntries.ENTRIES.clear();
			CosmeticaMod.LOGGER.warn("Failed to load Player Entries due to an exception: " + e);
		} finally {
			if (!PlayerEntries.ENTRIES.isEmpty()) {
				CosmeticaMod.LOGGER.info("Player Entries successfully loaded");
				CachedPlayerEntries.save();
			} else {
				CosmeticaMod.LOGGER.info("Something went wrong, loading entries from cache");
				CachedPlayerEntries.load();
			}
		}
	}

	public static class Entry {
		public Entry.User user;
		public Cloak cloak;
		public String [] attributes;
		public Cosmetic[] cosmetics;

		public static class User {
			public String name, uuid, discord, patreon, kofi;
		}
		public static class Cloak {
			public String name, color;
			public boolean glint;
		}
		public static class Cosmetic {
			public String name, placement, color;
		}
	}
}
