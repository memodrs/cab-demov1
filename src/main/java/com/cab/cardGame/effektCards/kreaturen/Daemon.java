package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffVerringern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;


public class Daemon extends CardStateEffekt {

	public Daemon(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        decreaseAtkOfArt(cardGame.getOwnerOfCard(this), Art.Mensch, cardGame);
        decreaseAtkOfArt(cardGame.getOwnerOfCard(this), Art.Tier, cardGame);
        decreaseAtkOfArt(cardGame.getOpOfCard(this), Art.Mensch, cardGame);
        decreaseAtkOfArt(cardGame.getOpOfCard(this), Art.Tier, cardGame);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Mensch) || cardGame.getOpOfCard(this).hasArtOnBoard(Art.Mensch) || cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Tier) || cardGame.getOpOfCard(this).hasArtOnBoard(Art.Tier);
    }

    private void decreaseAtkOfArt(Player p, Art art, CardGame cardGame) {
        for (int i = 0; i < cardGame.getOwnerOfCard(this).boardCards.size(); i++) {
            CardState card = cardGame.getOwnerOfCard(this).boardCards.get(i);
            if (card.art == art && !card.isHide) {
                new KarteAngriffVerringern().execute(cardGame, cardGame.getOwnerOfCard(this).boardCards.get(i).id, 2, true);
            }
        }
    }
}
