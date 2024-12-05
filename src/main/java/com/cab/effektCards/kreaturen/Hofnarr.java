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

    public void effekt(Integer idx) {   
        String value = cardGame.optionsToSelect.values().toArray(new String[0])[idx];
        PunkteArt selectedPunkteArt = PunkteArt.valueOf(value);
        int punkte = selectedPunkteArt == PunkteArt.Leben? 3 : 1;
        cardGame.spielerPunkteAendern(cardGame.player, punkte, selectedPunkteArt, true);
    }

    public boolean isEffektPossible(Player p) {
        return true;
    }

    @Override
    public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
        for (PunkteArt punkteArt : PunkteArt.values()) {
            cardGame.optionsToSelect.put(punkteArt.getTextbaustein(), punkteArt.toString());
        }
    }
}
