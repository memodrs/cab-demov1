package com.cab.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Alchemie extends EffektCardState {

	public Alchemie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {	
		String value = cardGame.optionsToSelect.values().toArray(new String[0])[idx];  
        Art selectedArt = Art.valueOf(value);
		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				cardGame.setArtOfCard(card.id, selectedArt, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}

	@Override
    public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
        for (Art art : Art.values()) {
            if (art != Art.Unbekannt) {
                cardGame.optionsToSelect.put(art.getTextbaustein(), art.toString());
            }
        }
    }
}