package com.cab.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Pinguin extends EffektCardState {

	public Pinguin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.kreaturAufrufen(cardGame.player, id, true, true, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && p.hasKreaturInHand();
	}


	@Override
	public void setUpOptionsToSelect() {
		super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
				.filter(card -> !card.defaultCard.isSpell())
				.collect(Collectors.toList())
		);
	}
}
