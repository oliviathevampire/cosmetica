package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import ru.pinkgoosik.cosmetica.render.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FancyCloaks {
    protected static final List<FancyCloak> CLOAKS = new ArrayList<>();

    public static void register(){
        add(new FancyCloak("jeb_", new JebCloakRenderer(false)));
        add(new FancyCloak("enchanted-jeb_", new JebCloakRenderer(true)));
        add(new FancyCloak("cosmic", new CosmicCloakRenderer()));
        add(new FancyCloak("swirly", new SwirlyCloakRenderer()));
        add(new FancyCloak("glowing", new GlowingCloakRenderer()));
    }

    private static void add(FancyCloak cloak){
        CLOAKS.add(cloak);
    }

    public static Optional<FancyCloak> getCloakByName(String name){
        for (FancyCloak cloak : CLOAKS) if(cloak.name.equals(name)) return Optional.of(cloak);
        return Optional.empty();
    }

    public static record FancyCloak(String name, CloakRenderer cloakRenderer) {}

}