package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Zombie extends EffektCardState implements EffektCard {

	public Zombie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer id) {
        cardGame.kreaturVomFriedhofAufrufen(p, this.id, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return p.boardCards.size() < 4 && !isEffectActivate;
    }
}
