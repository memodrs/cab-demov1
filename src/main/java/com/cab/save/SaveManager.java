package com.cab.save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cab.Player;
import java.util.Base64;

public class SaveManager {
    String filename = "cabSavegame.json";
    Path savePath = Paths.get(System.getProperty("user.home"), "Documents/CurseAndBlessing", filename);

    public boolean isSavegameExist() {
        return Files.exists(savePath);
    }

    public void save(Player p) {
        SaveModel saveModel = new SaveModel(
            p.punkte, 
            p.stapel.toArray(new Integer[0]), 
            p.truhe.toArray(new Integer[0]), 
            p.savedStapel
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
            // Serialisieren der Daten
            String jsonData = objectMapper.writeValueAsString(saveModel);

            // Hash erstellen
            //String hash = generateHash(jsonData);

            // Daten und Hash speichern
            //writer.write(hash + "\n" + jsonData);
            writer.write(jsonData);

        } catch (IOException e) {
            throw new Error("Fehler beim Speichern in die Datei: " + e.getMessage());
        }
    }

    public void load(Player p) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedReader reader = Files.newBufferedReader(savePath)) {
            // Hash und JSON-Daten lesen
            //String savedHash = reader.readLine();
            String jsonData = reader.readLine();

            // Hash überprüfen
            /*String calculatedHash = generateHash(jsonData);
            if (!savedHash.equals(calculatedHash)) {
                throw new Error("Die gespeicherten Daten wurden manipuliert!");
            }*/

            // Daten deserialisieren
            SaveModel saveModel = objectMapper.readValue(jsonData, SaveModel.class);

            // Player-Objekt aktualisieren
            p.punkte = saveModel.punkte;
            p.stapel = new ArrayList<>();
            for (int val : saveModel.stapel) {
                p.stapel.add(val);
            }
            p.truhe = new ArrayList<>();
            for (int val : saveModel.truhe) {
                p.truhe.add(val);
            }
            p.savedStapel = saveModel.savedStapel;

        } catch (IOException e) {
            throw new Error("Fehler beim Laden der Datei: " + e.getMessage());
        }
    }

    private String generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
