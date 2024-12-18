package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Zombie extends CardStateEffekt {

	public Zombie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Integer id) {
        cardGame.karteVomFriedhofAufBoard(cardGame.player, this.id, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return p.hasBoardPlace() && !isEffectActivate;
    }
}
