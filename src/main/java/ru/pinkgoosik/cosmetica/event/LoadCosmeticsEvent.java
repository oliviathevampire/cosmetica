package ru.pinkgoosik.cosmetica.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import ru.pinkgoosik.cosmetica.CosmeticaMod;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

import java.io.IOException;

public class LoadCosmeticsEvent implements ClientTickEvents.EndTick {
    private static boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if(client.world != null){
            if(!isLoaded){
                loadCloaks();
                isLoaded = true;
            }
        }else isLoaded = false;
    }

    private static void loadCloaks() {
        try {
            PlayerCloaks.reload();
        } catch (IOException e) {
            PlayerCloaks.ENTRIES.clear();
            CosmeticaMod.LOGGER.warn("Failed to load Player Cloaks due to an exception: " + e);
        } finally {
            if (!PlayerCloaks.ENTRIES.isEmpty()){
                CosmeticaMod.LOGGER.info("Player Cloaks successfully loaded");
            }
            else {
                PlayerCloaks.ENTRIES.addAll(PlayerCloaks.getDefaultOwners());
            }
        }
    }
}
