package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Hetzjagd extends CardStateSpell {
	public Hetzjagd(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		int count = (int) cardGame.oponent.graveCards.stream().filter(card -> card.art == Art.Nachtgestalt).count();
		cardGame.spielerPunkteAendern(cardGame.oponent, -count, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasArtOnGrave(Art.Nachtgestalt);
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}