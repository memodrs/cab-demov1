package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kappa extends EffektCardState {

	public Kappa(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
            cardGame.setKarteStatus(cardGame.player, card.id, false, Status.Feuer, true);
            cardGame.setKarteStatus(cardGame.player, card.id, false, Status.Blitz, true);
        }
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn && (p.boardCards.stream().anyMatch(card -> card.statusSet.contains(Status.Feuer) || card.statusSet.contains(Status.Blitz)));
	}
}