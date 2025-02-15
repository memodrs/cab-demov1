package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Fluegel extends CardStateSpell {

	public Fluegel(Card card) {
		super(card, State.boardState, State.effektSelectOwnBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new SetKarteStatus().execute(cardGame, id, true, Status.Fluegel, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Fluegel) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Fluegel);
    }
}