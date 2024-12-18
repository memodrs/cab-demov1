package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class HimmlicherRuf extends CardStateSpell {

	public HimmlicherRuf(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.hasBoardPlace() && p.graveCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

public void setUpOptionsToSelect(CardGame cardGame) {
    cardGame.optionsCardsToSelect.addAll(
        cardGame.player.graveCards.stream()
            .filter(card -> card.art == Art.Fabelwesen)
            .collect(Collectors.toList())
    );
}
}