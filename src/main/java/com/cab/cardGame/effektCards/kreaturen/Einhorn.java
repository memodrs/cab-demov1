package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;


public class Einhorn extends EffektCardState {

	public Einhorn(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Mensch && !card.isHide) {
				cardGame.karteAngriffErhoehen(card.id, card.atk, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasArtOnBoard(Art.Mensch);
	}
}