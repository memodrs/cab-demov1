package com.cab.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Aufrufung extends EffektCardState {

	public Aufrufung(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.kreaturAufrufenVomStapel(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.boardCards.size() == 0 && p.stapel.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
    		cardGame.player.stapel.stream()
        	.filter(card -> card.art == Art.Fabelwesen)
        	.collect(Collectors.toList())
		);
    }
}