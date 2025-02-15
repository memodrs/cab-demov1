package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;

import com.cab.cardGame.model.PunkteArt;

public class Hetzjagd extends CardStateSpell {
	public Hetzjagd(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		int count = (int) cardGame.getOpOfCard(this).graveCards.stream().filter(card -> card.art == Art.Nachtgestalt).count();
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOpOfCard(this), -count, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasArtOnGrave(Art.Nachtgestalt);
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}