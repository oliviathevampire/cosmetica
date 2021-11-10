package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Cloak {
    private final String name;
    private final IdProvider idProvider;

    public Cloak(String name, IdProvider idProvider){
        this.name = name;
        this.idProvider = idProvider;
    }

    public String getName() {
        return name;
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    @FunctionalInterface
    public interface IdProvider {
        Identifier getId(PlayerEntity player, World world);
    }
}
