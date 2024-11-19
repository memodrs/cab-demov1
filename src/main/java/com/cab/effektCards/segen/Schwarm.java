package com.cab.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Schwarm extends EffektCardState {

	public Schwarm(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
        cardGame.kreaturAufrufen(p, id, false, true, true);
        cardGame.kartenMischen(p, p.stapel, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.boardCards.stream().anyMatch(card -> Art.Tier.equals(card.art)) && 
                p.stapel.stream().anyMatch(card -> Art.Tier.equals(card.art));	
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		for (CardState card : cardGame.player.stapel) {
			if (card.art == Art.Tier) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}