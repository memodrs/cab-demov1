package com.cab.card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;

public class CardLoader {

	private GamePanel gp;
	public List<Card> cards = new ArrayList<>();
	public List<Integer> allCardIds = new ArrayList<>();
	
	public CardLoader(GamePanel gp) {
		this.gp = gp;
		loadAllCards();
	}

	private void loadAllCards() {
		loadKreaturen("kreaturen.csv");
		loadSpell("segen.csv");
		loadSpell("fluch.csv");
	}

	private void loadKreaturen(String path) {
		try (InputStream inputStream = CardLoader.class.getClassLoader().getResourceAsStream("cards/" + path);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) { 
			 
			String line = reader.readLine(); 
			while ((line = reader.readLine()) != null) {
				String[] cells = line.split(";");
				String beschreibungDeWithNewLines = insertNewLine(cells[7]);
				String beschreibungEngWithNewLines = insertNewLine(cells[8]);
				int id = Integer.parseInt(cells[0]);
				allCardIds.add(id);
				cards.add(new Card(
					id, 
					Art.valueOf(cells[1]), 
					Integer.parseInt(cells[2]), 
					Integer.parseInt(cells[3]), 
					0, 
					Status.valueOf(cells[4]), 
					cells[5], 
					cells[6], 
					beschreibungDeWithNewLines, 
					beschreibungEngWithNewLines, 
					gp
				));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void loadSpell(String path)  {
		try (InputStream inputStream = CardLoader.class.getClassLoader().getResourceAsStream("cards/" + path);
             
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) { 
		   String line;
		   line = reader.readLine();
		   while ((line = reader.readLine()) != null) {
				String[] cells = line.split(";");		
				String beschreibungDeWithNewLines = insertNewLine(cells[5]);
				String beschreibungEngWithNewLines = insertNewLine(cells[6]);
				int id = Integer.parseInt(cells[0]);
				allCardIds.add(id);
				cards.add(new Card(id, Art.valueOf(cells[1]), 0, 0, Integer.parseInt(cells[2]), Status.Default, cells[3], cells[4], beschreibungDeWithNewLines, beschreibungEngWithNewLines, gp));
			}
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	}

	private String insertNewLine(String input) {
		if (input == null || input.length() <= 26) {
			return input;
		}
		
		StringBuilder result = new StringBuilder();
		int index = 0;
		
		while (index < input.length()) {
			// Berechne das End-Index für die aktuelle Zeile
			int endIndex = Math.min(index + 26, input.length());
			
			// Finde das letzte Leerzeichen innerhalb der aktuellen Zeile
			int lastSpaceIndex = input.lastIndexOf(' ', endIndex);
			
			// Wenn kein Leerzeichen gefunden wurde oder es vor dem aktuellen Index liegt
			if (lastSpaceIndex == -1 || lastSpaceIndex < index) {
				lastSpaceIndex = endIndex;
			}
			
			// Füge den Teil des Strings bis zum gefundenen Leerzeichen oder Endindex ein
			result.append(input, index, lastSpaceIndex);
			
			// Wenn wir noch nicht am Ende des Strings sind, füge einen Zeilenumbruch ein
			if (lastSpaceIndex < input.length()) {
				result.append("\n");
			}
			
			// Aktualisiere den Index, um hinter das Leerzeichen zu springen
			index = lastSpaceIndex + 1;
		}
		
		return result.toString();
	}
	

	
	public Card getCard(int id) {
		for (Card card : cards) {
			if (card.id == id) {
				return card;
			}
		} return null;
	}
}
