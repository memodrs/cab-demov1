package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Hofnarr extends EffektCardState {

	public Hofnarr(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer idx) {   
        PunkteArt selectedPunkteArt = PunkteArt.valueOf(cardGame.optionsToSelect.get(idx));
        int punkte = selectedPunkteArt == PunkteArt.Leben? 3 : 1;
        cardGame.spielerPunkteAendern(p, punkte, selectedPunkteArt, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return true;
    }

    @Override
    public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
        for (PunkteArt punkteArt : PunkteArt.values()) {
            cardGame.optionsToSelect.add(punkteArt.toString());
        }
    }
}
