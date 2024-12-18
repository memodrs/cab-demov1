package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Banshee extends CardStateEffekt {

	public Banshee(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromGrave, State.ignoreState);
	}

    @Override
    public void effekt(CardGame cardGame, Integer id) {
        cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Fluch, true);
        cardGame.karteVomFriedhofAufBoard(cardGame.player, this.id, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.hasBoardPlace() && p.fluchCounter > 2;
    }
}
