package com.cab.configs;

import java.util.HashMap;
import java.util.Map;

public class Texte {
    public Map<String, String> setDe = new HashMap<>();
    public Map<String, String> setEng = new HashMap<>();


    public Texte() {
        setDe.put("firstStateWillkommen", "Herzlich Willkommen zum start erhälst du ein paar zufällige Karten");
        setDe.put("fWeiter",              "Drücke F für weiter");
        setDe.put("statusSchild",         "Schild: Blockt einen Angriff");
        setDe.put("statusFluegel",        "Flügel: Kann nur direkt angreifen");
        setDe.put("statusGift",           "Gift: Wird nach 2 Runden Vernichtet");
        setDe.put("statusFeuer",          "Feuer: Verliert Leben jede Runde");
        setDe.put("statusBlitz",          "Blitz: Kann nicht angreifen");
        setDe.put("truhe",                "Truhe");
        setDe.put("stapel",               "Stapel");
        setDe.put("von",                  "von");

    }
}
