package com.cab.cardGame.actions;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class SetArtOfCard {
    private int id;
    private Art art;

    public SetArtOfCard(int id, Art art) {
        this.id = id;
        this.art = art;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            card.art = art;
            cardGame.resolve();
        }
    }
} 