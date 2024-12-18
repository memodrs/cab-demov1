package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class VerlorenesLand extends CardStateSpell {	
	public VerlorenesLand(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
	}
	
	public void effekt(Integer id) {
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
		cardGame.spielerPunkteAendern(cardGame.oponent, -1, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.isBoardEmpty() && !cardGame.getOpOfP(p).isBoardEmpty();
	}
}