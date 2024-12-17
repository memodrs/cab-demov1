package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Donteur extends EffektCardState {

	public Donteur(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteBoardKontrolleUebernehmen(cardGame.player, id, true);
		cardGame.setKarteBlockAttackOnTurn(id, true, true);
	}
	
	public boolean isEffektPossible(Player p) {
		Player op = cardGame.getOpOfP(p);
		return op.hasArtOnBoard(Art.Tier) && p.hasBoardPlace();
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return card.art == Art.Tier;
	}
}