package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Waechter extends CardStateEffekt {

	public Waechter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Integer id) {   
        cardGame.setKarteStatus(id, true, Status.Schild, true);
     }

    @Override
    public boolean isEffektPossible(Player p) {
        return p.boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Schild) && !card.isHide) && !isEffectActivateInTurn;	
    }

    @Override
    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Schild);
    }




}
