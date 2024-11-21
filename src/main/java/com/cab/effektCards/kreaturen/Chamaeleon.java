package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Chamaeleon extends EffektCardState {

	public Chamaeleon(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void effekt(Integer id) {
        for (CardState card : cardGame.player.boardCards) {
            cardGame.karteDrehen(cardGame.player, card.id, true, true);
        }
        cardGame.kartenMischen(cardGame.player, cardGame.player.boardCards, true);
    }

    public boolean isEffektPossible(Player p) {
        return !isEffectActivateInTurn;
    }
}

