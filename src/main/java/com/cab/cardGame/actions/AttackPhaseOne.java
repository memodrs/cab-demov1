package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class AttackPhaseOne {
    public void execute(CardGame cardGame, Player player, int idPlayer, int idOponent, boolean send) {
        cardGame.send(send, player.isPlayer, idPlayer, idOponent, null, null, null, null, null, Messages.ATTACK_PHASE_ONE);

        cardGame.savedIdPlayerAttack = idPlayer;
        cardGame.savedIdOpAttack = idOponent;
        cardGame.addEffektToList(idPlayer, Trigger.triggerAngriffSetupAngreifer, idOponent);
        cardGame.addEffektToList(idOponent, Trigger.triggerAngriffSetupVerteidiger, idPlayer);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToAttackPhaseTwo = true;
            cardGame.resolve();
        } else {
            new AttackPhaseTwo().execute(cardGame, player, false);
        }
    }
} 