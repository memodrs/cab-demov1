package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Zombie extends EffektCardState {

	public Zombie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Integer id) {
        cardGame.kreaturVomFriedhofAufrufen(cardGame.player, this.id, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return p.hasBoardPlace() && !isEffectActivate;
    }
}
