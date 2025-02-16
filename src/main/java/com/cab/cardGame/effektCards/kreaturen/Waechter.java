package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Waechter extends CardStateEffekt {

	public Waechter(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {   
        new SetKarteStatus().execute(cardGame, id, true, Status.Schild, true);
     }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return !isEffectActivateInTurn;	
    }

    @Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectOpenCardsHasStatusNotOnBoard(cardGame.getOwnerOfCard(this), Status.Schild);
    }
}
