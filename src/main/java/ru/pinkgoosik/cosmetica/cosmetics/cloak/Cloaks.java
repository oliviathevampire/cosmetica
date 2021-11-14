package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Optional;

public class Cloaks {
    public static final ArrayList<Cloak> CLOAKS = new ArrayList<>();

    public static void register(){
        add(new UniCloak());
        addSimple("azure", "crimson", "flamingo", "golden", "lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee");
        addSimple("pride", "trans", "lesbian", "gay", "pan", "bi", "non-binary", "genderfluid", "aromantic", "demiromantic", "asexual", "demisexual");
    }

    private static void add(Cloak cloak){
        CLOAKS.add(cloak);
    }

    private static void addSimple(String... names){
        for(String name : names){
            CLOAKS.add(new Cloak(name, (player, world) -> new Identifier("cosmetica:textures/cloak/" + name + ".png")));
        }
    }

    public static Optional<Cloak> getCloakByName(String name){
        for (Cloak cloak : CLOAKS) if(cloak.getName().equals(name)) return Optional.of(cloak);
        return Optional.empty();
    }
}
