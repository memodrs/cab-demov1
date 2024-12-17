package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Aufstieg extends EffektCardState {

	public Aufstieg(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.karteVomBoardInFriedhof(cardGame.player, id, true, false);
		cardGame.spielerPunkteAendern(cardGame.player, 3, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}