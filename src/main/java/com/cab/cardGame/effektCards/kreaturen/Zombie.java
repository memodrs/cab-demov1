package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Zombie extends CardStateEffekt {

	public Zombie(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromGrave, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.player, this.id, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.hasBoardPlace() && !isEffectActivate;
    }
}
