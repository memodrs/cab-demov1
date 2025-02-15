package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class VerlorenesLand extends CardStateSpell {	
	public VerlorenesLand(Card card) {
		super(card, State.handCardState, State.effektSelectOponentBoardState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.oponent, id, true, false);
		new SpielerPunkteAendern().execute(cardGame, cardGame.oponent, -1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.isBoardEmpty() && !op.isBoardEmpty();
	}
}