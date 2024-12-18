package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Hofnarr extends CardStateEffekt {

	public Hofnarr(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer idx) {   
        String value = cardGame.optionsToSelect.values().toArray(new String[0])[idx];
        PunkteArt selectedPunkteArt = PunkteArt.valueOf(value);
        int punkte = selectedPunkteArt == PunkteArt.Leben? 3 : 1;
        cardGame.spielerPunkteAendern(cardGame.player, punkte, selectedPunkteArt, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return true;
    }

    @Override
    public void setUpOptionsToSelect(CardGame cardGame) {
        for (PunkteArt punkteArt : PunkteArt.values()) {
            cardGame.optionsToSelect.put(punkteArt.getTextbaustein(), punkteArt.toString());
        }
    }
}
