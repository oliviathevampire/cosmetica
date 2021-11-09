package ru.pinkgoosik.cosmetica.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

public class LoadCosmeticsEvent implements ClientTickEvents.EndTick {
    private static boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if(client.world != null){
            if(!isLoaded){
                PlayerCloaks.reload();
                isLoaded = true;
            }
        }else isLoaded = false;
    }
}
