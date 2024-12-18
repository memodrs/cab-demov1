package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Djinn extends CardStateEffekt {

	public Djinn(Card card) {
		super(card, State.handCardState, Trigger.triggerKreaturAufrufen, State.effektSelectOwnGraveState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {        
        cardGame.karteVomFriedhofInHand(cardGame.player, id, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return !p.isGraveEmpty();
    }
}