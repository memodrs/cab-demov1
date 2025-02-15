package com.cab.cardGame.actions;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class SetArtOfCard {
    public void execute(CardGame cardGame, int id, Art art, boolean send) {
        cardGame.send(send, null, id, null, null, null, art, null, null, Messages.SET_ART_OF_CARD);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            card.art = art;
            cardGame.resolve();
        }
    }
} 