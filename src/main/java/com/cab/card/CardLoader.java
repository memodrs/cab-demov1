package com.cab.card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		loadCards("kreaturen.csv", false);
		loadCards("segen.csv", true);
		loadCards("fluch.csv", true);
	}

	private void loadCards(String path, boolean isSpell)  {
		try (InputStream inputStream = CardLoader.class.getClassLoader().getResourceAsStream("cards/" + path);
             
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
		   String line;
		   int idx = 0;

		   while ((line = reader.readLine()) != null) {
			   if (idx == 0) {
				   idx++;
			   } else {
				   String[] cells = line.split(";");		
				   if (isSpell) {
						String beschreibungWithNewLines = insertNewLine(cells[4]);
						allCardIds.add(Integer.parseInt(cells[0]));
						cards.add(new Card(Integer.parseInt(cells[0]), cells[1], Art.valueOf(cells[2]), 0, 0, Integer.parseInt(cells[3]), Status.Default, beschreibungWithNewLines, gp.imageLoader));
				   } else {
						String beschreibungWithNewLines = insertNewLine(cells[6]);
						allCardIds.add(Integer.parseInt(cells[0]));
						cards.add(new Card(Integer.parseInt(cells[0]), cells[1], Art.valueOf(cells[2]), Integer.parseInt(cells[3]), Integer.parseInt(cells[4]), 0, Status.valueOf(cells[5]), beschreibungWithNewLines, gp.imageLoader));
				   }
			   }
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
