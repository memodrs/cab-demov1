package com.cab.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class RufDerDunkelheit extends EffektCardState {

	public RufDerDunkelheit(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.kreaturAufrufen(cardGame.player, id, false, true, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.hasBoardPlace() && p.graveCards.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
			.filter(card -> card.art == Art.Nachtgestalt)
			.toList()
		);
    }
}