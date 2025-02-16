package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetArtOfCard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;

public class Alchemie extends CardStateSpell {
	public Alchemie(Card card) {
		super(card, State.boardState, State.selectOptionState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {	
		String value = cardGame.optionsToSelect.values().toArray(new String[0])[idx];  
        Art selectedArt = Art.valueOf(value);
		for (CardState card : cardGame.getOpOfCard(this).boardCards) {
			if (!card.isHide) {
				new SetArtOfCard().execute(cardGame, card.id, selectedArt, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}

	@Override
    public void setupOptions(CardGame cardGame) {
		cardGame.optionsToSelect.put(Art.Mensch.getTextbaustein(), Art.Mensch.toString());
		cardGame.optionsToSelect.put(Art.Tier.getTextbaustein(), Art.Tier.toString());
		cardGame.optionsToSelect.put(Art.Fabelwesen.getTextbaustein(), Art.Fabelwesen.toString());
		cardGame.optionsToSelect.put(Art.Nachtgestalt.getTextbaustein(), Art.Nachtgestalt.toString());
    }
}