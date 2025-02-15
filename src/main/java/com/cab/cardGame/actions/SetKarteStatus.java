package com.cab.cardGame.actions;

import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class SetKarteStatus {
    public void execute(CardGame cardGame, int id, boolean isStatus, Status status, boolean send) {
        cardGame.send(send, null, id, null, isStatus, null, null, null, status.toString(), Messages.SET_KARTE_STATUS);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            if (isStatus && !card.statusSet.contains(status)) {
                card.statusSet.add(status);
            } else if (!isStatus && card.statusSet.contains(status)) {
                card.statusSet.remove(status);
            }
            cardGame.resolve();
        }
    }
} 