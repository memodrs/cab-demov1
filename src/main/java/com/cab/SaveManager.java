package com.cab;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class SaveManager {
    String filename = "save/savegame.txt";


    public boolean isSavegameExist() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.readLine() != null;  
        } catch (IOException e) {
            throw new Error("Speicherdatei nicht gefunden");
        }
    }

    public void save(Player p) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(getClass().getClassLoader().getResource(filename).getFile());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
            for (Integer id : p.truhe) {
                writer.write(id.toString());
                writer.write(" ");
            }
            writer.newLine();
            for (Integer id : p.stapel) {
                writer.write(id.toString());
                writer.write(" ");
            }
            writer.write(String.valueOf(p.punkte));
        } catch (IOException e) {
            throw new Error("Fehler beim Speichern in die Datei: " + e.getMessage());
        }
    }

    public void loadTruhe(Player p) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line = reader.readLine();
            String[] strArray = line.split(" ");
            for (String numStr : strArray) {
                p.truhe.add(Integer.parseInt(numStr));
            }
         } catch (IOException e) {
            throw new Error("Speicherdatei nicht gefunden");
        }
    }

    public void loadStapel(Player p) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            
            String line;
            int idx = 0;

            while ((line = reader.readLine()) != null) {
                if (idx == 1) {
                    String[] strArray = line.split(" ");
                    for (String numStr : strArray) {
                        p.stapel.add(Integer.parseInt(numStr));
                    }
                }
                idx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPunkte(Player p) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            
            String line;
            int idx = 0;

            while ((line = reader.readLine()) != null) {
                if (idx == 2) {
                    p.stapel.add(Integer.parseInt(line));
                }
                idx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
