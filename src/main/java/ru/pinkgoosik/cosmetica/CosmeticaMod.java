package ru.pinkgoosik.cosmetica;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.Cloaks;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.FancyCloaks;
import ru.pinkgoosik.cosmetica.data.CachedPlayerCloaks;
import ru.pinkgoosik.cosmetica.event.LoadCosmeticsEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CosmeticaMod implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Cosmetica");
    private static final int FORMAT_VERSION = 0;

	@Override
	public void onInitializeClient() {
        Cloaks.register();
        FancyCloaks.register();
        if(isCompatibleFormat()){
            ClientTickEvents.END_CLIENT_TICK.register(new LoadCosmeticsEvent());
        }else {
            CachedPlayerCloaks.load();
        }
	}

    private boolean isCompatibleFormat(){
        try{
            URL url = new URL("https://pinkgoosik.ru/data/cosmetica.json");
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();
            InputStream stream = request.getInputStream();
            JsonObject object = parser.parse(new InputStreamReader(stream)).getAsJsonObject();
            try {
                int remoteVersion = object.get("formatVersion").getAsInt();
                return remoteVersion == FORMAT_VERSION;
            }catch (ClassCastException ignored){}
        } catch (IOException | JsonIOException | JsonSyntaxException ignored) {}
        return false;
    }
}
