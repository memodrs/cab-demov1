package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Schwarm extends EffektCardState {

	public Schwarm(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.karteVonStapelAufBoard(cardGame.player, id, true);
        cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.hasBoardPlace() &&
				p.boardCards.stream().anyMatch(card -> Art.Tier.equals(card.art)) && 
                p.stapel.stream().anyMatch(card -> Art.Tier.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.stapel.stream()
			.filter(card -> card.art == Art.Tier)
			.collect(Collectors.toList())
		);
    }
}