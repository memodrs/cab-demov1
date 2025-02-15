package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class KarteAngriffErhoehen {
    public void execute(CardGame cardGame, int id, int punkte, boolean send) {
        CardState card = cardGame.getCardOfId(id);
        if (cardGame.isCardOnBoard(card)) {
            cardGame.cd.showHealCard(card);
            card.atk = card.atk + punkte;
            cardGame.resolve();
        }
    }
} 