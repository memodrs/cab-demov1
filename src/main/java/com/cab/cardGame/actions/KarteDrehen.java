package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class KarteDrehen {
    private int id;
    private boolean isHide;

    public KarteDrehen(int id, boolean isHide) {
        this.id = id;
        this.isHide = isHide;
    }

    public void execute(CardGame cardGame) {
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