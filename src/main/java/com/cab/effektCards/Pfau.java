package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;

public class Pfau extends EffektCardState implements EffektCard {

	public Pfau(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public boolean isAngriffVonAngreiferErlaubt(CardState angreifer) {
        return !isHide && angreifer.art != Art.Tier;
    }
}