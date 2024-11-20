package com.cab.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Vollmond extends EffektCardState {

	public Vollmond(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
        cardGame.kreaturAufrufen(p, id, false, true, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.hasBoardPlace() && p.handCards.stream().anyMatch(card -> Art.Tier.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
			.filter(card -> card.art == Art.Tier)
			.toList()
		);
    }
}