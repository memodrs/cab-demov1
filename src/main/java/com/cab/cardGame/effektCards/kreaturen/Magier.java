package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Magier extends CardStateEffekt {

	public Magier(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new SpielerPunkteAendern().execute(cardGame, cardGame.player, -2, PunkteArt.Fluch, true);
		new KarteVonBoardInHand().execute(cardGame, cardGame.oponent, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.fluchCounter > 1 && op.hasOpenCardsOnBoard();
	}
}