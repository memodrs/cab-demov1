package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class SegenDerDunklen extends CardStateSpell {

	public SegenDerDunklen(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.stapel.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));
    }

	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.stapel.stream()
			.filter(card -> card.art == Art.Nachtgestalt)
			.collect(Collectors.toList())
		);
    }
}