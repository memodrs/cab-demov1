package com.cab.configs;

import java.util.HashMap;
import java.util.Map;

public class Texte {
    public Map<String, String> setDe = new HashMap<>();
    public Map<String, String> setEng = new HashMap<>();


    public Texte() {
        de("firstStateWillkommen", "Herzlich Willkommen zum start erhälst du ein paar zufällige Karten");
        de("fWeiter",              "Drücke F für weiter");
        de("statusSchild",         "Schild: Blockt einen Angriff");
        de("statusFluegel",        "Flügel: Kann nur direkt angreifen");
        de("statusGift",           "Gift: Wird nach 2 Runden Vernichtet");
        de("statusFeuer",          "Feuer: Verliert Leben jede Runde");
        de("statusBlitz",          "Blitz: Kann nicht angreifen");
        de("truhe",                "Truhe");
        de("stapel",               "Stapel");
        de("von",                  "von");


        eng("firstStateWillkommen", "Welcome! At the start, you receive a few random cards");
        eng("fWeiter",              "Press F to continue");
        eng("statusSchild",         "Shield: Blocks one attack");
        eng("statusFluegel",        "Wings: Can only attack directly");
        eng("statusGift",           "Poison: Destroyed after 2 turns");
        eng("statusFeuer",          "Fire: Loses health every turn");
        eng("statusBlitz",          "Lightning: Cannot attack");
        eng("truhe",                "Chest");
        eng("stapel",               "Deck");
        eng("von",                  "of");
    }

    private void de(String key, String valString) {
        setDe.put(key, valString);
    }

    private void eng(String key, String valString) {
        setEng.put(key, valString);
    }
}
