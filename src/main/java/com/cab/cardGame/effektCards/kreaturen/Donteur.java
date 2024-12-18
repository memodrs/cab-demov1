package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Donteur extends CardStateEffekt {

	public Donteur(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteBoardKontrolleUebernehmen(cardGame.player, id, true);
		cardGame.setKarteBlockAttackOnTurn(id, true, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasArtOnBoard(Art.Tier) && p.hasBoardPlace();
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return card.art == Art.Tier;
	}
}