package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Banshee extends CardStateEffekt {

	public Banshee(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromGrave, State.ignoreState);
	}

    @Override
    public void effekt(CardGame cardGame, Integer id) {
        new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -2, PunkteArt.Fluch, true);
        new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).fluchCounter > 2;
    }
}
