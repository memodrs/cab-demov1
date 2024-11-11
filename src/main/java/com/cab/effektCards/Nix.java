package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Nix extends EffektCardState {
    public Nix(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

		@Override
    public void setBlock(Player p) {
        cardGame.getOpOfP(p).blockAngriffMenschen = true;
    }

}
