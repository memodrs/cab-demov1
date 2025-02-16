package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Elf extends CardStateEffekt {

	public Elf(Card card) {                 
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		int leben = cardGame.getCardOfId(id).life;
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.art == Art.Fabelwesen) {
				new KarteHeilen().execute(cardGame, card.id, leben, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
	}

}