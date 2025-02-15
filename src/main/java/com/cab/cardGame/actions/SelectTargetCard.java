package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.configs.Messages;

public class SelectTargetCard {
    public void execute(CardGame cardGame, int id, boolean send) {
        cardGame.send(send, null, id, null, null, null, null, null, null, Messages.SELECT_TARGET_CARD);
        cardGame.cd.showCardTargeted(cardGame.getCardOfId(id));
    }
} 