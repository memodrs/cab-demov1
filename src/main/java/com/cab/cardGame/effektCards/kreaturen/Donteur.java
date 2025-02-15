package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteBoardKontrolleUebernehmen;
import com.cab.cardGame.actions.SetKarteBlockAttackOnTurn;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

public class Donteur extends CardStateEffekt {

	public Donteur(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteBoardKontrolleUebernehmen().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		new SetKarteBlockAttackOnTurn().execute(cardGame, id, true, true);
	}
	
	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectOpenCardsArtOnBoard(cardGame.getOpOfCard(this), Art.Tier);
    }
}