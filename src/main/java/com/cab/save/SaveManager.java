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
import com.cab.GamePanel;
import com.cab.Player;
import com.cab.configs.Sprache;
import com.cab.states.Language;

import java.util.Base64;

public class SaveManager {
    String filename = "cabSavegame.json";
    Path savePath = Paths.get(System.getProperty("user.home"), "Documents/CurseAndBlessing", filename);

    public boolean isSavegameExist() {
        return Files.exists(savePath);
    }

    public void save(Player p, Sprache language) {
        SaveModel saveModel = new SaveModel(
            p.punkte, 
            p.stapel.toArray(new Integer[0]), 
            p.truhe.toArray(new Integer[0]), 
            p.savedStapel,
            language
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
            String jsonData = objectMapper.writeValueAsString(saveModel);

            String hash = generateHash(jsonData);

            writer.write(hash + "\n" + jsonData);

        } catch (IOException e) {
            throw new Error("Fehler beim Speichern in die Datei: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void load(GamePanel gp) {
        Player p = gp.player;
        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedReader reader = Files.newBufferedReader(savePath)) {
            String savedHash = reader.readLine();
            String jsonData = reader.readLine();

            String calculatedHash = generateHash(jsonData);
            if (!savedHash.equals(calculatedHash)) {
                gp.savegameCorrupt.start();
            } else {
                SaveModel saveModel = objectMapper.readValue(jsonData, SaveModel.class);
                p.punkte = saveModel.getPunkte();
                p.stapel = new ArrayList<>();
                for (int val : saveModel.getStapel()) {
                    p.stapel.add(val);
                }
                p.truhe = new ArrayList<>();
                for (int val : saveModel.getTruhe()) {
                    p.truhe.add(val);
                }
                p.savedStapel = saveModel.savedStapel;

                gp.selectedLanguage = saveModel.getSprache();
            }
        } catch (IOException e) {
            throw new Error("Fehler beim Laden der Datei: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
