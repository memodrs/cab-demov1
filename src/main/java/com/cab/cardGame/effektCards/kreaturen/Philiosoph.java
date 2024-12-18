package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Philiosoph extends CardStateEffekt {

	public Philiosoph(Card card) {
		super(card, State.handCardState, Trigger.triggerOnStartRunde, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.stapel.stream()
		.anyMatch(card -> Art.Segen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.player.stapel) {
			if (card.art == Art.Segen) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
