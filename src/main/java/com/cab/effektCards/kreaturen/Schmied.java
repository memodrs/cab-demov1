package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Schmied extends EffektCardState {

	public Schmied(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Player p, Integer id) {
		cardGame.karteVonStapelAufDieHand(p, id, true);
		cardGame.kartenMischen(p, p.stapel, true);
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
