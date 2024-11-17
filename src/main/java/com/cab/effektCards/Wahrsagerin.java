package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Wahrsagerin extends EffektCardState {

	public Wahrsagerin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer idx) {   
        Art selectedArt = Art.valueOf(cardGame.optionsToSelect.get(idx));
        cardGame.setBlockAufrufArtNextTurn(cardGame.getOpOfP(p), true, selectedArt, true);
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
