package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.Player;

public class AttackPhaseOne {
    private Player player;
    private int idPlayer;
    private int idOponent;

    public AttackPhaseOne(Player player, int idPlayer, int idOponent) {
        this.player = player;
        this.idPlayer = idPlayer;
        this.idOponent = idOponent;
    }

    public void execute(CardGame cardGame) {
        cardGame.savedIdPlayerAttack = idPlayer;
        cardGame.savedIdOpAttack = idOponent;
        cardGame.addEffektToList(idPlayer, Trigger.triggerAngriffSetupAngreifer, idOponent);
        cardGame.addEffektToList(idOponent, Trigger.triggerAngriffSetupVerteidiger, idPlayer);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToAttackPhaseTwo = true;
            cardGame.resolve();
        } else {
            cardGame.attackPhaseTwo(player, false);
        }
    }
} 