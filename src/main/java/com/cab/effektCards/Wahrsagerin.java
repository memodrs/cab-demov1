package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Wahrsagerin extends EffektCardState implements EffektCard {

	public Wahrsagerin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer idx) {   
        System.out.println("Seleted Option " + cardGame.optionsToSelect.get(idx));
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return true;
    }

    @Override
    public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
        for (Art art : Art.values()) {
            if (art != Art.Unbekannt) {
                cardGame.optionsToSelect.add(art.toString());
            }
        }
    }
}