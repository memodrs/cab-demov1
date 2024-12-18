package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;


public class Ratte extends CardStateEffekt {

	public Ratte(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && p.handCards.stream()
		.anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}

	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		for (CardState card : cardGame.player.handCards) {
			if (card.art == Art.Nachtgestalt) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}