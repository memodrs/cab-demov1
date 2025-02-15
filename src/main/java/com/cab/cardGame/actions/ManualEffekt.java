package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class ManualEffekt {
    public void execute(CardGame cardGame, int id, boolean send) {
        cardGame.send(send, null, id, null, null, null, null, null, null, Messages.MANUAL_EFFEKT);

        CardState effektCard = cardGame.getCardOfId(id);
        cardGame.addEffektToList(id, effektCard.triggerState, -1);
        cardGame.resolve();
    }
} 