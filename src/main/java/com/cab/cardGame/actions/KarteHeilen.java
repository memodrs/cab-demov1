package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class KarteHeilen {
    private int id;
    private int punkte;

    public KarteHeilen(int id, int punkte) {
        this.id = id;
        this.punkte = punkte;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);
        cardGame.cd.showHealCard(card);
        if (cardGame.isCardOnBoard(card)) {
            card.life = card.life + punkte;
            cardGame.resolve();
        }
    }
} 