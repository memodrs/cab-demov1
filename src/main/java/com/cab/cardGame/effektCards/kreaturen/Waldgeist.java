package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;



public class Waldgeist extends CardStateEffekt {

	public Waldgeist(Card card) {
		super(card, State.boardState,Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.art == Art.Tier && !card.isHide) {
				new SetKarteStatus().execute(cardGame, card.id, true, Status.Schild, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Tier);
	}
}