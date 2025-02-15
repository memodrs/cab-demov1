package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class KarteHeilen {
    public void execute(CardGame cardGame, int id, int punkte, boolean send) {
        cardGame.send(send, null, id, punkte, null, null, null, null, null, Messages.KARTE_HEILEN);
        CardState card = cardGame.getCardOfId(id);
        cardGame.cd.showHealCard(card);
        if (cardGame.isCardOnBoard(card)) {
            card.life = card.life + punkte;
            cardGame.resolve();
        }
    }
} 