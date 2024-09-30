package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Chamaeleon extends EffektCardState implements EffektCard {

	public Chamaeleon(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer id) {
        for (int i = 0; i < p.boardCards.size(); i++) {
            cardGame.karteDrehen(p, p.boardCards.get(i).id, true, true);
        }

        cardGame.kartenMischen(p, p.boardCards, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return !isEffectActivateInTurn;
    }
}

