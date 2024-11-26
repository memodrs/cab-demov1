package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Himmliche extends EffektCardState {

	public Himmliche(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {		

		int counter = (int) cardGame.player.graveCards.stream()
                           .filter(card -> card.art == Art.Fabelwesen)
                           .count();

		cardGame.spielerPunkteAendern(cardGame.player, counter, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.graveCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));	
	}
}	