package com.cab.cardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cab.cardGame.model.Player;


public class KI {
    CardGame cardGame;
    CardGame cardGameInHead;

    Player ki;
    Player player;

    List<String> possibleActions;
    List<List<String>> possibleZuege;
    Map<Integer, List<String>> bewertungZuege;

    List<String> selectedZug;

    public KI(CardGame cardGame) {
        this.cardGame = cardGame;
        this.cardGameInHead = cardGame;
        this.ki = cardGame.oponent;
        this.ki.isKI = true;
        this.player = cardGame.player;

		cardGame.kartenMischen(ki, ki.stapel, false);
        cardGame.kartenZiehen(ki, 5, false);
    }


    public void startTurn() {
        possibleActions = new ArrayList<>();
        cardGame.startTurn(ki);

        cardGame.endTurn(ki);
    }
}
