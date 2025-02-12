package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class KarteAngriffVerringern {
    private int id;
    private int punkte;

    public KarteAngriffVerringern(int id, int punkte) {
        this.id = id;
        this.punkte = punkte;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            if (punkte > card.atk) {
                card.atk = 0;
            } else {
                card.atk = card.atk - punkte;
            }
            cardGame.resolve();
        }
    }
} 