package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteDrehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Chamaeleon extends CardStateEffekt {

	public Chamaeleon(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        for (CardState card : cardGame.player.boardCards) {
            new KarteDrehen().execute(cardGame, card.id, true, true);
        }
        cardGame.kartenMischen(cardGame.player, cardGame.player.boardCards, true);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return !isEffectActivateInTurn;
    }
}

