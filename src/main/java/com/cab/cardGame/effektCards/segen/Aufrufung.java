package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Aufrufung extends CardStateSpell {

	public Aufrufung(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        new KarteVonStapelAufBoard().execute(cardGame, cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.boardCards.size() == 0 && p.stapel.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
    		cardGame.player.stapel.stream()
        	.filter(card -> card.art == Art.Fabelwesen)
        	.collect(Collectors.toList())
		);
    }
}