package com.cab.cardGame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cab.cardGame.Factory.CardGameFactory;
import com.cab.cardGame.actions.*;
import com.cab.cardGame.cpu.ActionParams;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;


public class KI {
    CardGame cardGame;



    Map<Action, ActionParams> possibleActions;

    List<String> selectedZug;

    public KI(CardGame cardGame) {
        this.cardGame = cardGame;
        
        Player ki = cardGame.oponent;
        ki.isKI = true;

		cardGame.kartenMischen(ki, ki.stapel, false);
        new KartenZiehen().execute(cardGame, ki, 5, true);
    }
    
    public void startTurn() {
        cardGame.startTurn(cardGame.oponent);

        doTurn();
        new EndTurn().execute(cardGame, cardGame.oponent, false);
    }

    private void doTurn() {       
        playBestCreature();
    }

    private void playBestCreature() {
        possibleActions = new HashMap<>();
        int actualBewertung = calculateBewertung(cardGame);

        for (CardState card : cardGame.oponent.handCards) {
            if (!card.defaultCard.isSpell() && cardGame.oponent.isPlayCreatureAllowed(card)) {
                CardGame cardGameInHead = CardGameFactory.createCopy(cardGame.gp, cardGame);
                ActionParams actionParams = new ActionParams();
                KarteVonHandAufBoard action = new KarteVonHandAufBoard();

                actionParams.setId(card.id);
                actionParams.setHide(false);
                actionParams.setSpecial(false);
                action.execute(cardGameInHead, cardGameInHead.player, card.id, actionParams.isHide(), actionParams.isSpecial(), false);

                int bewertung = calculateBewertung(cardGameInHead);
               
                if (bewertung > actualBewertung) {
                    actionParams.setBewertung(100);
                    possibleActions.put(action, actionParams);
                }
            }
        }
        resolveBestAction();
    }

    private int calculateBewertung(CardGame cg) {
        return 0;
    }

    private void resolveBestAction() {
        int bestBewertung = -1;
        Action bestAction = null;
        for (Action action : possibleActions.keySet()) {
            int bewertung = possibleActions.get(action).getBewertung();
            if (bewertung > bestBewertung) {
                bestBewertung = bewertung;
                bestAction = action;
            }
        }

        if (bestAction != null) {
            executeActionWithParams(bestAction, cardGame);
        }
    }

    public void executeActionWithParams(Action action, CardGame cardGame) {
        ActionParams actionParams = possibleActions.get(action);
        if (action instanceof KarteVonHandAufBoard) {
            KarteVonHandAufBoard karteVonHandAufBoard = (KarteVonHandAufBoard) action;
            karteVonHandAufBoard.execute(cardGame, cardGame.oponent, actionParams.getId(), actionParams.isHide(), actionParams.isSpecial(), false);
        }
    }
}

