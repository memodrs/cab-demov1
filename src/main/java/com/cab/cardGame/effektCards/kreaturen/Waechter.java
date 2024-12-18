package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Waechter extends CardStateEffekt {

	public Waechter(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.effektSelectOwnBoardState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {   
        cardGame.setKarteStatus(id, true, Status.Schild, true);
     }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Schild) && !card.isHide) && !isEffectActivateInTurn;	
    }

    @Override
    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Schild);
    }




}
