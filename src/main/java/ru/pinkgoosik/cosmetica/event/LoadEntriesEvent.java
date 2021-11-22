package ru.pinkgoosik.cosmetica.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import ru.pinkgoosik.cosmetica.data.PlayerEntries;

public class LoadEntriesEvent implements ClientTickEvents.EndTick {
    private static boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if (client.world != null) {
            if(!isLoaded){
                PlayerEntries.reload();
                isLoaded = true;
            }
        } else isLoaded = false;
    }
}
