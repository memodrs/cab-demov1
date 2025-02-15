package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteBlockAttackOnTurn;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;




public class Armor extends CardStateEffekt {
	public Armor(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new SetKarteBlockAttackOnTurn().execute(cardGame, id, true, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return !isEffectActivateInTurn;
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
    }
}
