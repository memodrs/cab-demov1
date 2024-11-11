package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;


public class Waldgeist extends EffektCardState {

	public Waldgeist(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			CardState card = p.boardCards.get(i);
			if (card.art == Art.Tier && !card.isHide) {
				cardGame.setKarteStatus(p, card.id, true, Status.Schild, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Tier);
	}
}