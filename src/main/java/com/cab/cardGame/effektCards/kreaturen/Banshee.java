package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Banshee extends CardStateEffekt {

	public Banshee(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromGrave, State.ignoreState);
	}

    @Override
    public void effekt(CardGame cardGame, Integer id) {
        new SpielerPunkteAendern().execute(cardGame, cardGame.player, -2, PunkteArt.Fluch, true);
        new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.player, this.id, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.hasBoardPlace() && p.fluchCounter > 2;
    }
}
