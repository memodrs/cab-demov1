package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class SetUpDirekterAngriff {
    private Player player;
    private int idx;

    public SetUpDirekterAngriff(Player player, int idx) {
        this.player = player;
        this.idx = idx;
    }

    public void execute(CardGame cardGame) {
        CardState card = player.boardCards.get(idx);
        cardGame.savedIdPlayerAttack = card.id;

        cardGame.addEffektToList(card.id, Trigger.triggerBeforeDirekterAngriff, -1);
        cardGame.addEffekteToList(cardGame.getOpOfP(player).handCards, 
            Trigger.triggerOnHandBeforeDamageDirekterAngriff, card.id);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToDirectAttack = true;
            cardGame.resolve();
        } else {
            cardGame.direkterAngriff(player, card.id, false);
        }
    }
} 