package com.cab.cardGame.actions;

import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class AttackPhaseThree {
    private Player player;

    public AttackPhaseThree(Player player) {
        this.player = player;
    }

    public void execute(CardGame cardGame) {
        cardGame.continueToAttackPhaseThree = false;
        
        Player oponent = cardGame.getOpOfP(player);
        CardState verteidiger = cardGame.getCardOfId(cardGame.savedIdOpAttack);
        CardState angreifer = cardGame.getCardOfId(cardGame.savedIdPlayerAttack);

        angreifer.hasAttackOnTurn = true;

        if (verteidiger.isHide && verteidiger.atk > angreifer.atk) {
            cardGame.cd.showAttackOnCardSelbstzerstoerung(angreifer, verteidiger);
            new KarteVomBoardInFriedhof(player, angreifer.id, true).execute(cardGame);
            cardGame.addEffektToList(angreifer.id, Trigger.triggerWurdeDurchAngriffZerstoert, verteidiger.id);
        } else if (verteidiger.isHide && verteidiger.atk == angreifer.atk) {
            cardGame.cd.showAttackOnCardDoppelZerstoerung(angreifer, verteidiger);
            cardGame.karteVomBoardInFriedhof(player, angreifer.id, false, true);
            cardGame.karteVomBoardInFriedhof(oponent, verteidiger.id, false, true);
            cardGame.addEffektToList(verteidiger.id, Trigger.triggerWurdeDurchAngriffZerstoert, angreifer.id);
            cardGame.addEffektToList(angreifer.id, Trigger.triggerWurdeDurchAngriffZerstoert, verteidiger.id);
            cardGame.addEffektToList(verteidiger.id, Trigger.triggerhatDurchAngriffZerstoert, angreifer.id);
            cardGame.addEffektToList(angreifer.id, Trigger.triggerhatDurchAngriffZerstoert, verteidiger.id);
        } else {
            if (verteidiger.statusSet.contains(Status.Schild)) {
                verteidiger.statusSet.remove(Status.Schild);
                cardGame.cd.showAttackOnSchild(angreifer, verteidiger);
                cardGame.switchState(State.boardState);
            } else if (verteidiger.life > angreifer.atk) {
                cardGame.cd.showAttackOnCardSchaden(angreifer, verteidiger);
                verteidiger.life = verteidiger.life - angreifer.atk;
            } else {
                cardGame.cd.showAttackOnCardZersteorung(angreifer, verteidiger);
                cardGame.karteVomBoardInFriedhof(oponent, verteidiger.id, false, true);
                cardGame.addEffektToList(angreifer.id, Trigger.triggerhatDurchAngriffZerstoert, verteidiger.id);
                cardGame.addEffektToList(verteidiger.id, Trigger.triggerWurdeDurchAngriffZerstoert, angreifer.id);
            }
        }

        cardGame.addEffektToList(verteidiger.id, Trigger.triggerWurdeAngegriffen, angreifer.id);
        cardGame.addEffektToList(angreifer.id, Trigger.triggerhatAngegriffen, verteidiger.id);

        verteidiger.isHide = false;    

        if (angreifer.isEffectActivateInTurn && cardGame.isCardOnBoard(angreifer)) {
            angreifer.removeBeforeAttackEffekt(cardGame);
        }

        cardGame.switchState(State.boardState);
        cardGame.resolve();
    }
} 