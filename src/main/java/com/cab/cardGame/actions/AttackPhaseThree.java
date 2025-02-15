package com.cab.cardGame.actions;

import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class AttackPhaseThree {
    public void execute(CardGame cardGame, Player player, boolean send) {
        cardGame.send(send, player.isPlayer, cardGame.savedIdPlayerAttack, cardGame.savedIdOpAttack, null, null, null, null, null, Messages.ATTACK_PHASE_THREE);
        cardGame.continueToAttackPhaseThree = false;
        
        Player oponent = cardGame.getOpOfP(player);
        CardState verteidiger = cardGame.getCardOfId(cardGame.savedIdOpAttack);
        CardState angreifer = cardGame.getCardOfId(cardGame.savedIdPlayerAttack);

        angreifer.hasAttackOnTurn = true;

        if (verteidiger.isHide && verteidiger.atk > angreifer.atk) {
            cardGame.cd.showAttackOnCardSelbstzerstoerung(angreifer, verteidiger);
            new KarteVomBoardInFriedhof().execute(cardGame, player, angreifer.id, false, true);
            cardGame.addEffektToList(angreifer.id, Trigger.triggerWurdeDurchAngriffZerstoert, verteidiger.id);
        } else if (verteidiger.isHide && verteidiger.atk == angreifer.atk) {
            cardGame.cd.showAttackOnCardDoppelZerstoerung(angreifer, verteidiger);
            new KarteVomBoardInFriedhof().execute(cardGame, player, angreifer.id, false, true);
            new KarteVomBoardInFriedhof().execute(cardGame, oponent, verteidiger.id, false, true);
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
                new KarteVomBoardInFriedhof().execute(cardGame, oponent, verteidiger.id, false, true);
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