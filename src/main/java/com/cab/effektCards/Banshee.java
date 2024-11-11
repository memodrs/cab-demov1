package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Banshee extends EffektCardState {

	public Banshee(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer id) {
        cardGame.spielerPunkteAendern(p, -2, PunkteArt.Fluch, true);
        cardGame.kreaturVomFriedhofAufrufen(p, this.id, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return p.boardCards.size() < 4 && p.fluchCounter > 1;
    }
}
