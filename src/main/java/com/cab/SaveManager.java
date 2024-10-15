package com.cab;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveManager {
    String filename = "test/savegame.txt";
    Path savePath = Paths.get(System.getProperty("user.home"), filename);


    public boolean isSavegameExist() {
        if (!Files.exists(savePath)) {
            return false;
        } else {
            return true;
        }
    }

    public void save(Player p) {
        try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
            // Truhe-Daten speichern
            for (Integer id : p.truhe) {
                writer.write(id.toString() + " ");
            }
            writer.newLine();

            // Stapel-Daten speichern
            for (Integer id : p.stapel) {
                writer.write(id.toString() + " ");
            }
            writer.newLine();

            // Punktestand speichern
            writer.write(String.valueOf(p.punkte));
        } catch (IOException e) {
            throw new Error("Fehler beim Speichern in die Datei: " + e.getMessage());
        }
    }

    public void load(Player p) {
        if (!Files.exists(savePath)) {
            throw new Error("Speicherdatei nicht gefunden");
        }

        try (BufferedReader reader = Files.newBufferedReader(savePath)) {
            String line;
            int idx = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (idx == 0) {
                        String[] strArray = line.split(" ");
                        for (String numStr : strArray) {
                            p.truhe.add(Integer.parseInt(numStr)); 
                        }
                    } else if (idx == 1) { 
                        String[] strArray = line.split(" ");
                        for (String numStr : strArray) {
                            p.stapel.add(Integer.parseInt(numStr)); 
                        }
                    } else if (idx == 2) {
                        p.punkte = Integer.parseInt(line);
                    }
                }
                idx++;
            }
        } catch (IOException e) {
            throw new Error("Fehler beim Laden der Truhe: " + e.getMessage(), e);
        }
    }
}
