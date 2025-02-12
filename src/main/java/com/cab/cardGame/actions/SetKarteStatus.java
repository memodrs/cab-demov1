package com.cab.cardGame.actions;

import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class SetKarteStatus {
    private int id;
    private boolean isStatus;
    private Status status;

    public SetKarteStatus(int id, boolean isStatus, Status status) {
        this.id = id;
        this.isStatus = isStatus;
        this.status = status;
    }

    public void execute(CardGame cardGame) {
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