package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Daemon extends EffektCardState implements EffektCard {

	public Daemon(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer id) {
        decreaseAtkOfMenschen(p, Art.Mensch);
        decreaseAtkOfMenschen(p, Art.Tier);
        decreaseAtkOfMenschen(cardGame.getOponentForPlayer(p), Art.Mensch);
        decreaseAtkOfMenschen(cardGame.getOponentForPlayer(p), Art.Tier);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return cardGame.isArtOnBoardOfPlayer(p, Art.Mensch) || cardGame.isArtOnBoardOfPlayer(cardGame.getOponentForPlayer(p), Art.Mensch) || cardGame.isArtOnBoardOfPlayer(p, Art.Tier) || cardGame.isArtOnBoardOfPlayer(cardGame.getOponentForPlayer(p), Art.Tier);
    }

    private void decreaseAtkOfMenschen(Player p, Art art) {
        for (int i = 0; i < p.boardCards.size(); i++) {
            CardState card = p.boardCards.get(i);
            if (card.art == art) {
                cardGame.karteAngriffVerringern(p, p.boardCards.get(i).id, 2, true);
            }
        }
    }
}
