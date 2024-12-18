package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class VodooPriester extends CardStateEffekt {

	public VodooPriester(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Fluch, true);
		cardGame.karteSchaden(cardGame.oponent, id, 2, true, false);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !op.isBoardEmpty() && p.fluchCounter > 0 && !this.isEffectActivateInTurn;
	}
}
