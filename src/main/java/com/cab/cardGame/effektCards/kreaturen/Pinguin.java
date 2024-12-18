package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Pinguin extends CardStateEffekt {

	public Pinguin(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, id, true, true, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && p.hasKreaturInHand();
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
				.filter(card -> !card.defaultCard.isSpell())
				.collect(Collectors.toList())
		);
	}
}
