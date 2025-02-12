package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class ManualEffekt {
    private int id;

    public ManualEffekt(int id) {
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState effektCard = cardGame.getCardOfId(id);
        cardGame.addEffektToList(id, effektCard.triggerState, -1);
        cardGame.resolve();
    }
} 