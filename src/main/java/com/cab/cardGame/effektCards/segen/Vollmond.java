package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Vollmond extends EffektCardState {

	public Vollmond(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.hasBoardPlace() && p.handCards.stream().anyMatch(card -> Art.Tier.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
			.filter(card -> card.art == Art.Tier)
			.collect(Collectors.toList())
		);
    }
}