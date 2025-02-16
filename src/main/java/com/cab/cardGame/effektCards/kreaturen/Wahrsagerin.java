package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetBlockAufrufArtNextTurn;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Wahrsagerin extends CardStateEffekt {

	public Wahrsagerin(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer idx) { 
        String value = cardGame.optionsToSelect.values().toArray(new String[0])[idx];  
        Art selectedArt = Art.valueOf(value);
        new SetBlockAufrufArtNextTurn().execute(cardGame, cardGame.getOpOfCard(this), true, selectedArt, true, false);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return true;
    }

    @Override
    public void setupOptions(CardGame cardGame) {
        for (Art art : Art.values()) {
            if (art != Art.Unbekannt) {
                cardGame.optionsToSelect.put(art.getTextbaustein(), art.toString());
            }
        }
    }
}
