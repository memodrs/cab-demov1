package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.configs.Messages;

public class ChangeSavedIdPlayerAttack {
    public void execute(CardGame cardGame, int id, boolean send) {
        cardGame.send(send, null, id, null, null, null, null, null, null, Messages.CHANGE_SAVED_ID_PLAYER_ATTACK);
        cardGame.savedIdPlayerAttack = id;
    }
} 