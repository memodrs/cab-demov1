package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Magier extends CardStateEffekt {

	public Magier(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -2, PunkteArt.Fluch, true);
		new KarteVonBoardInHand().execute(cardGame, cardGame.getOpOfCard(this), id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).fluchCounter > 1;
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
    }
}