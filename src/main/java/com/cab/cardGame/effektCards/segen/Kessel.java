package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufStapel;
import com.cab.cardGame.actions.KartenZiehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class Kessel extends CardStateSpell {

	public Kessel(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {
        int size = cardGame.getOwnerOfCard(this).handCards.size();

        for (int i = 0; i < size; i++) {
			new KarteVonHandAufStapel().execute(cardGame, cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).handCards.get(0).id, true);
        }
        cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
		new KartenZiehen().execute(cardGame, cardGame.getOwnerOfCard(this), size, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).handCards.size() > 1;
	}
}