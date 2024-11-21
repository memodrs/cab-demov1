package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Sylphe extends EffektCardState {

	public Sylphe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {		

		int counter = (int) cardGame.player.boardCards.stream()
                           .filter(card -> card.art == Art.Fabelwesen)
                           .count();

		cardGame.spielerPunkteAendern(cardGame.player, counter, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn;
	}
}	