package com.cab.cardGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cab.cardGame.Factory.CardGameFactory;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;


public class KI {
    CardGame cardGame;
    CardGame cardGameInHead;

    Player ki;

    List<Integer> possibleActions;
    Map<Integer, Integer> bewertungActions; // action / bewertung

    List<String> selectedZug;

    public KI(CardGame cardGame) {
        this.cardGame = cardGame;
        this.ki = cardGame.oponent;
        this.ki.isKI = true;

		cardGame.kartenMischen(ki, ki.stapel, false);
        cardGame.kartenZiehen(ki, 5, false);
    }
    
    public void startTurn() {
        cardGame.startTurn(ki);
        addPossibleActions();
        reviewPossibleActions();
        resolveBestAction();

        cardGame.endTurn(ki);
    }

    private void addPossibleActions() {
        possibleActions = new ArrayList<>();
        for (CardState card : ki.handCards) {
            if (!card.defaultCard.isSpell()) {
                possibleActions.add(card.id);
            }
        }
    }

    private void reviewPossibleActions() {
        bewertungActions = new HashMap<>();
        for (Integer action : possibleActions) {
            cardGameInHead = CardGameFactory.createCopy(cardGame.gp, cardGame);
            cardGameInHead.karteVonHandAufBoard(cardGameInHead.oponent, action, false, false, false);

            int bewertung = 0;
            for (CardState card : cardGameInHead.oponent.boardCards) {
                bewertung = bewertung + card.atk;
            }
            bewertungActions.put(action, bewertung);
        }
    }

    private void resolveBestAction() {
        int bestBewertung = 0;
        int id =  -1;
        for (Integer action : bewertungActions.keySet()) {
            if (bewertungActions.get(action) > bestBewertung) {
                bestBewertung = bewertungActions.get(action);
                id = action;
            }
        }
        if (id != -1) {
            cardGame.karteVonHandAufBoard(ki, id, false, false, false);
        }
    }

    public void handleSelectState(int selectState) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSelectState'");
    }

}

