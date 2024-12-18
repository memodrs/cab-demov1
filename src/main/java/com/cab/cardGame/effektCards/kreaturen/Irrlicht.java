package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Irrlicht extends CardStateEffekt {

	public Irrlicht(Card card) {
		super(card, State.graveOponentState, Trigger.triggerAfterDestroyed, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
        cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
    }
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.boardCards.size() > 0;
	}
}
