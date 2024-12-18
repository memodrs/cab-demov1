package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Verstaerkung extends CardStateSpell {

	public Verstaerkung(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.hasBoardPlace() && p.handCards.stream().anyMatch(card -> Art.Mensch.equals(card.art));
    }

	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
			.filter(card -> card.art == Art.Mensch)
			.collect(Collectors.toList())
		);
    }
}