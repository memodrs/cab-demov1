package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Opfergabe extends CardStateSpell {
	public Opfergabe(Card card) {
		super(card, State.handCardState, State.effektSelectOwnBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		cardGame.karteVomBoardInFriedhof(cardGame.player, id, true, false);
		cardGame.spielerPunkteAendern(cardGame.player, 3, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}