package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Finsternis extends EffektCardState {

	public Finsternis(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void setBlock(Player p) {
        cardGame.player.blockEffektMenschen = true;
        cardGame.player.blockEffektTiere = true;
        cardGame.player.blockEffektFabelwesen = true;
        cardGame.player.blockEffektNachtgestalten = true;

        cardGame.oponent.blockEffektMenschen = true;
        cardGame.oponent.blockEffektTiere = true;
        cardGame.oponent.blockEffektFabelwesen = true;
        cardGame.oponent.blockEffektNachtgestalten = true;
    }
}