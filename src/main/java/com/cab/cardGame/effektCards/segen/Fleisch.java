package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Fleisch extends EffektCardState {

	public Fleisch(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Tier) {
				cardGame.karteAngriffErhoehen(card.id, 2, true);
				cardGame.karteHeilen(card.id, 2, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasArtOnBoard(Art.Tier);
	}
}