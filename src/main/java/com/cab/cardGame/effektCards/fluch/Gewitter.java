package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Gewitter extends CardStateSpell {
	public Gewitter(Card card) {
		super(card, State.handCardState, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		cardGame.setKarteStatus(id, true, Status.Blitz, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return op.boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Blitz) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Blitz);
    }
}