package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class KarteAngriffVerringern {
    public void execute(CardGame cardGame, int id, int punkte,boolean send) {
        cardGame.send(send, null, id, punkte, null, null, null, null, null, Messages.KARTE_ANGRIFF_VERRINGERN);
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