package ru.pinkgoosik.cosmetica.data;

import com.google.gson.*;

import java.io.*;

public class CachedPlayerCloaks {

    public static void load(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cosmetica/cached_cloaks.json"));
            JsonParser parser = new JsonParser();
            try {
                JsonArray array = parser.parse(reader).getAsJsonArray();
                PlayerCloaks.ENTRIES.clear();
                array.forEach(element -> {
                    JsonObject object = element.getAsJsonObject();
                    try {
                        String name, uuid, cloak;
                        name = object.get("name").getAsString();
                        uuid = object.get("uuid").getAsString();
                        cloak = object.get("cloak").getAsString();
                        PlayerCloaks.ENTRIES.add(new PlayerCloaks.Entry(name, uuid, cloak));
                    }catch (ClassCastException ignored){}
                });
            }catch (IllegalStateException ignored){}
        } catch (FileNotFoundException | JsonSyntaxException | JsonIOException ignored) {}
    }

    public static void save(){
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            File dir = new File("cosmetica");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter writer = new FileWriter("cosmetica/cached_cloaks.json");
            writer.write(gson.toJson(PlayerCloaks.ENTRIES));
            writer.close();
        } catch (IOException ignored) {}
    }
}
