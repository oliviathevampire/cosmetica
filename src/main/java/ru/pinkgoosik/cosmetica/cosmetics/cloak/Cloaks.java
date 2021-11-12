package ru.pinkgoosik.cosmetica.cosmetics.cloak;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Optional;

public class Cloaks {
    public static final ArrayList<Cloak> CLOAKS = new ArrayList<>();
    public static final ArrayList<String> CLOAK_NAMES = new ArrayList<>();

    public static void register(){
        add(new UniCloak());
        addSimples(new String[]{"azure", "crimson", "flamingo", "golden", "lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee"});
        addSimples(new String[]{"pride", "trans", "lesbian", "gay", "pan", "bi", "non-binary", "genderfluid", "aromantic", "demiromantic", "asexual", "demisexual"});
    }

    private static void add(Cloak cloak){
        CLOAKS.add(cloak);
        CLOAK_NAMES.add(cloak.getName());
    }

    private static void addSimples(String[] names){
        for(String name : names){
            addSimple(name);
            CLOAK_NAMES.add(name);
        }
    }

    private static void addSimple(String name){
        CLOAKS.add(new Cloak(name, (player, world) -> new Identifier("cosmetica:textures/cloak/" + name + ".png")));
        CLOAK_NAMES.add(name);
    }

    public static Optional<Cloak> getCloakByName(String name){
        for (Cloak cloak : CLOAKS){
            if(cloak.getName().equals(name)) return Optional.of(cloak);
        }
        return Optional.empty();
    }
}
