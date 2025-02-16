package com.cab.cardGame.cpu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.Factory.CardGameFactory;
import com.cab.cardGame.actions.*;
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
        ki.isFirstTurn = false;
        ki.isOnTurn = false;
		ki.inactiveMode = true;

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
                for (boolean hideValue : Arrays.asList(false, true)) {
                    CardGame cardGameInHead = CardGameFactory.createCopy(cardGame.gp, cardGame);
                    ActionParams actionParams = new ActionParams();
                    KarteVonHandAufBoard action = new KarteVonHandAufBoard();

                    actionParams.setId(card.id);
                    actionParams.setHide(hideValue);
                    actionParams.setSpecial(false);
                    action.execute(cardGameInHead, cardGameInHead.oponent, card.id, actionParams.isHide(), actionParams.isSpecial(), false);

                    int bewertung = calculateBewertung(cardGameInHead);
                   
                    if (bewertung > actualBewertung) {
                        actionParams.setBewertung(actualBewertung);
                        possibleActions.put(action, actionParams);
                    }
                }
            }
        }
        resolveBestAction();
    }

    private int calculateBewertung(CardGame cg) {
        int bewertung = 0;
        Player ki = cg.oponent;

        for (CardState card : ki.boardCards) {
            if (ki.isAttackAlowed(card)) {
                bewertung += card.atk + 10;
            }

            if (card.isHide && card.art == Art.Fabelwesen && !ki.hasArtOnBoard(Art.Nachtgestalt)) {
                bewertung += 10;
            }

            bewertung++;
        }
        return bewertung;
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

