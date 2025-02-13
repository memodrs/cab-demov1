package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.Player;

public class ForceOponentToEndTurn {    
    Player oponent;

    public ForceOponentToEndTurn(Player oponent) {
        this.oponent = oponent;
    }
    public void execute(CardGame cardGame) {
        cardGame.endTurn(oponent);
    }
} 