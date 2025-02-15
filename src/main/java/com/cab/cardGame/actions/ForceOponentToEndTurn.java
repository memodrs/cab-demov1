package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class ForceOponentToEndTurn {    

    public void execute(CardGame cardGame, Player oponent, boolean send) {
        cardGame.send(send, null, null, null, null, null, null, null, null, Messages.FORCE_OPONENT_TO_END_TURN);
        new EndTurn().execute(cardGame, oponent, cardGame.isOnline);
    }
} 