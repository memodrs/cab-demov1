package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.actions.SetArtOfCard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Mimikrie extends CardStateEffekt {

	public Mimikrie(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		CardState copyCard = cardGame.getCardOfId(id);

		new SetArtOfCard().execute(cardGame, this.id, copyCard.art, true);
		this.life = 0;
		new KarteHeilen().execute(cardGame, this.id, copyCard.life, true);
		this.atk = 0;
		new KarteAngriffErhoehen().execute(cardGame, this.id, copyCard.atk, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasOpenCardsOnBoard();
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}
