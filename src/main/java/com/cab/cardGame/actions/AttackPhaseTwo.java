package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class AttackPhaseTwo {
    private Player player;


    public AttackPhaseTwo(Player player) {
        this.player = player;
    }

    public void execute(CardGame cardGame) {
        cardGame.continueToAttackPhaseTwo = false;

        CardState angreifer = cardGame.getCardOfId(cardGame.savedIdPlayerAttack);
        CardState verteidiger = cardGame.getCardOfId(cardGame.savedIdOpAttack);

        cardGame.addEffektToList(angreifer.id, Trigger.triggerBeforeKarteAngreift, angreifer.id);
        cardGame.addEffektToList(verteidiger.id, Trigger.triggerBeforeKarteWirdAngegriffen, angreifer.id);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToAttackPhaseThree = true;
            cardGame.resolve();
        } else {
            cardGame.attackPhaseThree(player, false);
        }
    }
} 