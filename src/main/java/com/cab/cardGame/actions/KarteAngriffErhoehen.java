package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class KarteAngriffErhoehen {
    private int id;
    private int punkte;

    public KarteAngriffErhoehen(int id, int punkte) {
        this.id = id;
        this.punkte = punkte;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);
        if (cardGame.isCardOnBoard(card)) {
            cardGame.cd.showHealCard(card);
            card.atk = card.atk + punkte;
            cardGame.resolve();
        }
    }
} 