package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Schmied extends CardStateEffekt {

	public Schmied(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.stapel.stream()
		.anyMatch(card -> Art.Segen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		for (CardState card : cardGame.player.stapel) {
			if (card.art == Art.Segen) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
