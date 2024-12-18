package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;


public class Ratte extends CardStateEffekt {

	public Ratte(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && p.handCards.stream()
		.anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.player.handCards) {
			if (card.art == Art.Nachtgestalt) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}