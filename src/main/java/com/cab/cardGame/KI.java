package com.cab.cardGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cab.cardGame.Factory.CardGameFactory;
import com.cab.cardGame.actions.Action;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;


public class KI {
    CardGame cardGame;

    List<Integer> possibleEffektTargets;
    Map<Integer, Integer> possibleEffektTargetsBewertung; // action / bewertung

    Map<Action, CardGame> possibleActions;
    Map<Action, Integer> actionBewertung; // action / bewertung

    List<String> selectedZug;

    public KI(CardGame cardGame) {
        this.cardGame = cardGame;
        Player ki = cardGame.oponent;
        ki.isKI = true;
		cardGame.kartenMischen(ki, ki.stapel, false);
        //cardGame.kartenZiehen(ki, 5, false);
    }
    
    public void startTurn() {
        cardGame.startTurn(cardGame.oponent);
        addPossibleActionsAddCardToBoard();


        reviewPossibleActions();
        resolveBestAction();

        //cardGame.endTurn(cardGame.oponent);
    }

    private void addPossibleActionsAddCardToBoard() {
        possibleActions = new HashMap<>();
        Player ki = cardGame.oponent;
        for (CardState card : ki.handCards) {
            if (!card.defaultCard.isSpell()) {
                //CardGame cardGameInHead = CardGameFactory.createCopy(cardGame.gp, cardGame);
                //possibleActions.put(new KarteVonHandAufBoard(cardGameInHead.oponent, card.id, true, false), cardGameInHead);
            }
        }
    }

    private void reviewPossibleActions() {
        actionBewertung = new HashMap<>();
        Random random = new Random();
        for (Action action : possibleActions.keySet()) {
            action.execute(possibleActions.get(action));
            actionBewertung.put(action, random.nextInt(100));
        }
    }


    private void resolveBestAction() {
        int bestBewertung = -1;
        Action bestAction = null;
        for (Action action : actionBewertung.keySet()) {
            if (actionBewertung.get(action) > bestBewertung) {
                bestBewertung = actionBewertung.get(action);
                bestAction = action;
            }
        }
        if (bestAction != null) {
            bestAction.execute(cardGame);
        }
    }

    public void handleSelectState(int selectState) {
        possibleEffektTargets = new ArrayList<>();
        possibleEffektTargetsBewertung = new HashMap<>();

        if (cardGame.cardGameState.isState(State.effektSelectOponentBoardState)) {
            for (CardState card : cardGame.oponent.boardCards) {
                if (cardGame.activeEffektCard.isCardValidForSelection(card)) {
                    possibleEffektTargets.add(card.id);
                }
            }

            for (Integer target : possibleEffektTargets) {
                CardGame cardGameInHead = CardGameFactory.createCopy(cardGame.gp, cardGame);
                cardGameInHead.handleEffekt(cardGameInHead.activeEffektCard.id, target, true);
                possibleEffektTargetsBewertung.put(target, 0);
            }

            int bewertung = 0;
            int id = -1;
            for (Integer target : possibleEffektTargetsBewertung.keySet()) {
                if (possibleEffektTargetsBewertung.get(target) > bewertung) {
                    bewertung = possibleEffektTargetsBewertung.get(target);
                    id = target;
                }
            }
            cardGame.handleEffekt(cardGame.activeEffektCard.id, id, true);
        }
    }
}

