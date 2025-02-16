package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Sphynx extends CardStateEffekt {

	public Sphynx(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new KarteVonBoardInHand().execute(cardGame, cardGame.getOpOfCard(this), id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
	
	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectOpenCardsArtOnBoard(cardGame.getOpOfCard(this), Art.Mensch);
    }
}
