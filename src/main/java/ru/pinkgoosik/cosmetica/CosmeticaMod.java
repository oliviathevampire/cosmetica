package ru.pinkgoosik.cosmetica;

import com.google.gson.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.Cloaks;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.FancyCloaks;
import ru.pinkgoosik.cosmetica.data.CachedPlayerEntries;
import ru.pinkgoosik.cosmetica.event.LoadEntriesEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CosmeticaMod implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cosmetica");
    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private static final int FORMAT_VERSION = 1;

	@Override
	public void onInitializeClient() {
        Cloaks.register();
        FancyCloaks.register();
        if (isCompatibleFormat()) ClientTickEvents.END_CLIENT_TICK.register(new LoadEntriesEvent());
        else CachedPlayerEntries.load();
	}

    private boolean isCompatibleFormat(){
        try {
            URL url = new URL("https://pinkgoosik.ru/data/revamped_cosmetica.json");
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();
            InputStream stream = request.getInputStream();
            JsonElement element = parser.parse(new InputStreamReader(stream));
            if(element.isJsonObject()){
                if(element.getAsJsonObject().get("formatVersion") != null){
                    try {
                        int remoteVersion = element.getAsJsonObject().get("formatVersion").getAsInt();
                        return remoteVersion == FORMAT_VERSION;
                    }catch (ClassCastException | IllegalStateException ignored){}
                }
            }
        } catch (IOException | JsonIOException | JsonSyntaxException ignored) {}
        return false;
    }
}
