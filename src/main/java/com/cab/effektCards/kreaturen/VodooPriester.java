package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class VodooPriester extends EffektCardState {

	public VodooPriester(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Fluch, true);
		cardGame.karteSchaden(cardGame.oponent, id, 2, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		return !cardGame.getOpOfP(p).isBoardEmpty() && p.fluchCounter > 0 && !this.isEffectActivateInTurn;
	}
}
