package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Igel extends CardStateEffekt {

	public Igel(Card card) {
		super(card, State.boardState, Trigger.triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteSchaden(cardGame.oponent, id, 2, true, false);
    }
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
