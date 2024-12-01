package com.cab.save;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import com.cab.Player;

public class SaveManager {
    String filename = "savegame.txt";
    Path savePath = Paths.get(System.getProperty("user.home"), filename);


    public boolean isSavegameExist() {
        if (!Files.exists(savePath)) {
            return false;
        } else {
            return true;
        }
    }

    public void save(Player p) {
        SaveModel saveModel = new SaveModel(p.punkte, p.stapel.toArray(new Integer[0]), p.truhe.toArray(new Integer[0]), p.savedStapel);

        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
            objectMapper.writeValue(writer, saveModel);
        } catch (IOException e) {
            throw new Error("Fehler beim Speichern in die Datei: " + e.getMessage());
        }
    }


    public void load(Player p) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedReader reader = Files.newBufferedReader(savePath)) {
            SaveModel saveModel = objectMapper.readValue(reader, SaveModel.class);

            p.punkte = saveModel.punkte;
            p.stapel = new ArrayList<Integer>();
            for (int i = 0; i < saveModel.stapel.length; i++) {
                p.stapel.add(saveModel.stapel[i]);
            }
            p.truhe = new ArrayList<Integer>();
            for (int i = 0; i < saveModel.truhe.length; i++) {
                p.truhe.add(saveModel.truhe[i]);
            }

            p.savedStapel = saveModel.savedStapel;
            
        } catch (IOException e) {
            throw new Error("Fehler beim Laden der Datei: " + e.getMessage());
        }
    }
}
