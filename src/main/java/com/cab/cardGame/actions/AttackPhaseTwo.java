package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class AttackPhaseTwo {
    public void execute(CardGame cardGame, Player player, boolean send) {
        cardGame.send(send, player.isPlayer, cardGame.savedIdPlayerAttack, cardGame.savedIdOpAttack, null, null, null, null, null, Messages.ATTACK_PHASE_TWO);

        cardGame.continueToAttackPhaseTwo = false;

        CardState angreifer = cardGame.getCardOfId(cardGame.savedIdPlayerAttack);
        CardState verteidiger = cardGame.getCardOfId(cardGame.savedIdOpAttack);

        cardGame.addEffektToList(angreifer.id, Trigger.triggerBeforeKarteAngreift, angreifer.id);
        cardGame.addEffektToList(verteidiger.id, Trigger.triggerBeforeKarteWirdAngegriffen, angreifer.id);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToAttackPhaseThree = true;
            cardGame.resolve();
        } else {
            new AttackPhaseThree().execute(cardGame, player, false);
        }
    }
} 