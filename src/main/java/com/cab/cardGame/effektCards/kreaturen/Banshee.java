package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Banshee extends EffektCardState {

	public Banshee(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void effekt(Integer id) {
        cardGame.spielerPunkteAendern(cardGame.player, -3, PunkteArt.Fluch, true);
        cardGame.karteVomFriedhofAufBoard(cardGame.player, this.id, true);
    }

    public boolean isEffektPossible(Player p) {
        return p.hasBoardPlace() && p.fluchCounter > 1;
    }
}
