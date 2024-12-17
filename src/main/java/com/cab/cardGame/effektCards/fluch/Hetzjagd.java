package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Hetzjagd extends EffektCardState {
	public Hetzjagd(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		int count = (int) cardGame.oponent.graveCards.stream().filter(card -> card.art == Art.Nachtgestalt).count();
		cardGame.spielerPunkteAendern(cardGame.oponent, -count, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnGrave(Art.Nachtgestalt);
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}