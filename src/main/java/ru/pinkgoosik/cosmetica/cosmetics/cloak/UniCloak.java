package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class UniCloak extends Cloak {

    public UniCloak() {
        super("uni", (player, world) -> {
            String id = "cosmetica:textures/cloak/name.png";
            RegistryKey<World> worldKey = world.getRegistryKey();
            if (worldKey.equals(World.OVERWORLD)) id = id.replaceAll("name", "turtle");
            else if (worldKey.equals(World.NETHER)) id = id.replaceAll("name", "crimson");
            else if (worldKey.equals(World.END)) id = id.replaceAll("name", "violet");
            else id = id.replaceAll("name", "turtle");
            return new Identifier(id);
        });
    }
}
