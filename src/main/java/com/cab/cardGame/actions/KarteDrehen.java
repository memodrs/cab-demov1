package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class KarteDrehen {
    public void execute(CardGame cardGame, int id, boolean isHide, boolean send) {
        cardGame.send(send, null, id, null, isHide, null, null, null, null, Messages.KARTE_DREHEN);
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            card.isHide = isHide;
            if (isHide) {
                card.resetStatsToHide();
            } else {
                card.setDefaultStatus();
            }
            cardGame.updateBlocks();
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 