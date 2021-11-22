package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cloaks {
    protected static final List<Cloak> AVAILABLE_CLOAKS = new ArrayList<>();

    public static void register(){
        add(new UniCloak());
        addNamedCapes("colored", "azure", "crimson", "flamingo", "golden", "lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee");
        addNamedCapes("pride", "pride", "trans", "lesbian", "gay", "pan", "bi", "non-binary", "genderfluid", "aromantic", "demiromantic", "asexual", "demisexual");
        addNamedCapes("patterned", "space");
    }

    private static void addNamedCapes(String thing, String... names) {
        for(String name : names) {
            add(new Cloak(name, (player, world) -> new Identifier("cosmetica:textures/cloak/" + thing + "/" + name + ".png")));
        }
    }

    private static void add(Cloak cloak){
        AVAILABLE_CLOAKS.add(cloak);
    }

    public static Optional<Cloak> getCloakByName(String name) {
        for (Cloak cloak : AVAILABLE_CLOAKS) if (cloak.getName().equals(name)) return Optional.of(cloak);
        return Optional.empty();
    }
}
