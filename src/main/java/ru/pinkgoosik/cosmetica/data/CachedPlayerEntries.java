package ru.pinkgoosik.cosmetica.data;

import com.google.gson.*;
import ru.pinkgoosik.cosmetica.CosmeticaMod;

import java.io.*;
import java.util.Arrays;

public class CachedPlayerEntries {

    public static void load(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config/cosmetica/cached_entries.json"));
            PlayerEntries.Entry[] entry = CosmeticaMod.GSON.fromJson(reader, PlayerEntries.Entry[].class);
            PlayerEntries.ENTRIES.addAll(Arrays.asList(entry));
        } catch (FileNotFoundException | JsonSyntaxException | JsonIOException ignored) {}
    }

    public static void save(){
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            File dir = new File("config/cosmetica");
            if (!dir.exists()) dir.mkdirs();
            try (FileWriter writer = new FileWriter("config/cosmetica/cached_entries.json")) {
                writer.write(gson.toJson(PlayerEntries.ENTRIES));
            }
        } catch (IOException ignored) {}
    }
}
