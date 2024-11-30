package com.cab.configs;

import java.util.HashMap;
import java.util.Map;

public class Texte {
    public Map<String, String> setDe = new HashMap<>();
    public Map<String, String> setEng = new HashMap<>();


    public Texte() {
        de("firstStateWillkommen",    "Herzlich Willkommen zum start erhälst du ein paar zufällige Karten");
        de("fWeiter",                 "Drücke F für weiter");
        de("statusSchild",            "Schild: Blockt einen Angriff");
        de("statusFluegel",           "Flügel: Kann nur direkt angreifen");
        de("statusGift",              "Gift: Wird nach 2 Runden Vernichtet");
        de("statusFeuer",             "Feuer: Verliert Leben jede Runde");
        de("statusBlitz",             "Blitz: Kann nicht angreifen");
        de("truhe",                   "Truhe");
        de("stapel",                  "Stapel");
        de("von",                     "von");
        de("karteSchieben",           "Karte schieben");
        de("wechselnTruheStapel",     "Wechseln Truhe/Stapel");
        de("fabelwesenHinweis",       "Fabelwesen können nur angreifen wenn sich ein Mensch auf deinem Board befindet");
        de("nachtgestalenHinweis",    "Nachtgestalten können nur angreifen wenn sich kein Mensch auf deinem Board befindet");
        de("zuWenigKartenStapel",     "Du hast zu wenig Karten in deinem Stapel");
        de("deckBearbeiten",          "Deck bearbeiten");
        de("singlePlayer",            "Singleplayer (in Arbeit)");
        de("serverErstellen",         "Server erstellen");
        de("serverBeitreten",         "Server beitreten");
        de("shop",                    "Shop");
        de("lexikon",                 "Lexikon");
        de("optionen",                "Optionen (in Arbeit)");
        de("beenden",                 "Spiel beenden");
        de("belohnungSieg",           "Belohnung: 50 Punkte");
        de("punkteStand",             "Neuer Punktestand:");
        de("gewonnen",                "Du hast gewonnen");
        de("belohnungNiedrerlage",    "Belohnung: 10 Punkte");
        de("verloren",                "Du hast verloren");
        de("bonus",                   "Bonus: 20 Punkte");
        de("oeffentlich",             "Öffentlich");
        de("privat",                  "Privat (in Arbeit)");
        de("serverOeffentlichB",      "Alle Spieler können deinem Spiel beitreten");
        de("ServerPrivatBeschr",      "Lege ein Passwort fest, dass bei beitreten abgefragt wird");
        de("server",                  "Server");
        de("serverGestarted",         "gestartet, warten bis ein Spieler betritt oder abbrechen...");
        de("spielerMitID",            "Spieler mit der ID");
        de("beigetreten",             "beigetreten");
        de("auswahltasteBestaetigen", "Mit der Auswahltaste bestätigen um Spiel zu starten oder abbrechen");
        de("laufendeServer",          "laufende Server");
        de("serverAuswahlHinweis",    "Wähle einen Server aus, dem du beitreten möchtest");
        de("keineServerGefunden",     "Es wurden keine Server gefunden");
        de("verbundenMitSpieler",     "Verbunden mit dem Spieler, mit der ID");
        de("wartenAufServerStart",    "Warten auf Start vom Server");
        de("effekt",                  "Effekt:");
        de("zusatzinfo",              "Zusatzinfo (IN ARBEIT)");
        de("kosten",                  "Kosten");
        de("punkte",                  "Punkte");
        de("shop",                    "Shop");
        de("packPreis",               "-Pack Preis: ");
        de("zuWenigPunkte",           "Dein Punktestand ist zu gering, Punktestand: ");
        de("packKaufBestaetigung",    "Bist du dir sicher, dass du diese Pack für ");
        de("kaufenWollen",            " kaufen willst?");
        de("ja",                      "Ja");
        de("nein",                    "Nein");
        de("neueKarteErhalten",       "Neue Karte erhalten");
        de("alleKartenBesitzt",       "Du besitzt bereits alle Karten aus diesem Pack");
        de("verlassen",               "Verlassen");
        de("aufgebenFrage",           "Aufgeben?");
        de("effektAktivieren",        "Effekt aktivieren");
        de("optionGewaehlt",          "Option gewählt:");
        de("waehleZiel",              "Wähle ein Ziel");
        de("gegnerWaehltZiel",        "Dein Gegner wählt ein Ziel");
        de("duBistDran",              "Du bist dran");
        de("spielZuEnde",             "Das Spiel ist zu Ende");
        de("ok",                      "Ok");
        de("punkteArtLeben",          "Leben");
        de("punkteArtFluch",          "Fluch");
        de("punkteArtSegen",          "Segen");
        de("artMensch",               "Mensch");
        de("artTier",                 "Tier");
        de("artFabelwesen",           "Fabelwesen");
        de("artNachtgestalt",         "Nachtgestalt");
        de("artFluch",                "Fluch");
        de("artSegen",                "Segen");
        de("artUnbekannt",            "Unbekannt");
        de("statusFeuer",             "Feuer");
        de("statusGift",              "Gift");
        de("statusSchild",            "Schild");
        de("statusBlitz",             "Blitz");
        de("statusFluegel",           "Flügel");
        de("statusDefault",           "Standard");


        eng("firstStateWillkommen",    "Welcome! At the start, you receive a few random cards");
        eng("fWeiter",                 "Press F to continue");
        eng("statusSchild",            "Shield: Blocks one attack");
        eng("statusFluegel",           "Wings: Can only attack directly");
        eng("statusGift",              "Poison: Destroyed after 2 turns");
        eng("statusFeuer",             "Fire: Loses health every turn");
        eng("statusBlitz",             "Lightning: Cannot attack");
        eng("truhe",                   "Chest");
        eng("stapel",                  "Deck");
        eng("von",                     "of");
        eng("karteSchieben",           "Move card");
        eng("wechselnTruheStapel",     "Switch chest/pile");
        eng("fabelwesenHinweis",       "Mythical creatures can only attack if a human is on your board");
        eng("nachtgestalenHinweis",    "Night creatures can only attack if no human is on your board");
        eng("zuWenigKartenStapel",     "You have too few cards in your pile");
        eng("deckBearbeiten",          "Edit deck");
        eng("singlePlayer",            "Singleplayer (in progress)");
        eng("serverErstellen",         "Create server");
        eng("serverBeitreten",         "Join server");
        eng("shop",                    "Shop");
        eng("lexikon",                 "Lexicon");
        eng("optionen",                "Options (in progress)");
        eng("beenden",                 "Quit game");
        eng("belohnungSieg",           "Reward: 50 points");
        eng("punkteStand",             "New score:");
        eng("gewonnen",                "You have won");
        eng("belohnungNiedrerlage",    "Reward: 10 points");
        eng("verloren",                "You have lost");
        eng("bonus",                   "Bonus: 20 points");
        eng("oeffentlich",             "Public");
        eng("privat",                  "Private (in progress)");
        eng("serverOeffentlichB",      "All players can join your game");
        eng("ServerPrivatBeschr",      "Set a password that will be required to join");
        eng("server",                  "Server");
        eng("serverGestarted",         "Started, waiting for a player to join or cancel...");
        eng("spielerMitID",            "Player with ID");
        eng("beigetreten",             "joined");
        eng("auswahltasteBestaetigen", "Confirm with the selection key to start the game or cancel");
        eng("laufendeServer",          "Running servers");
        eng("serverAuswahlHinweis",    "Select a server to join");
        eng("keineServerGefunden",     "No servers found");
        eng("verbundenMitSpieler",     "Connected with player, ID");
        eng("wartenAufServerStart",    "Waiting for server to start");
        eng("effekt",                  "Effect:");
        eng("zusatzinfo",              "Additional info (IN PROGRESS)");
        eng("kosten",                  "Cost");
        eng("punkte",                  "Points");
        eng("shop",                    "Shop");
        eng("packPreis",               "-Pack Price: ");
        eng("zuWenigPunkte",           "Your points are too low, current points: ");
        eng("packKaufBestaetigung",    "Are you sure you want to buy this pack for ");
        eng("kaufenWollen",            "?");
        eng("ja",                      "Yes");
        eng("nein",                    "No");
        eng("neueKarteErhalten",       "New card received");
        eng("alleKartenBesitzt",       "You already own all the cards from this pack");
        eng("verlassen",               "Quit");
        eng("aufgebenFrage",           "Give up?");
        eng("effektAktivieren",        "Activate effect");
        eng("optionGewaehlt",          "Option selected:");
        eng("waehleZiel",              "Select a target");
        eng("gegnerWaehltZiel",        "Your opponent is selecting a target");
        eng("duBistDran",              "It's your turn");
        eng("spielZuEnde",             "The game is over");
        eng("ok",                      "Ok");
        eng("punkteArtLeben",          "Life");
        eng("punkteArtFluch",          "Curse");
        eng("punkteArtSegen",          "Blessing");
        eng("artMensch",               "Human");
        eng("artTier",                 "Animal");
        eng("artFabelwesen",           "Mythical creature");
        eng("artNachtgestalt",         "Night creature");
        eng("artFluch",                "Curse");
        eng("artSegen",                "Blessing");
        eng("artUnbekannt",            "Unknown");
        eng("statusFeuer",             "Fire");
        eng("statusGift",              "Poison");
        eng("statusSchild",            "Shield");
        eng("statusBlitz",             "Lightning");
        eng("statusFluegel",           "Wings");
        eng("statusDefault",           "Default");
    }

    private void de(String key, String valString) {
        setDe.put(key, valString);
    }

    private void eng(String key, String valString) {
        setEng.put(key, valString);
    }
}
