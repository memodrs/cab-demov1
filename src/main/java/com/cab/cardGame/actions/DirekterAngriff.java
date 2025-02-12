package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class DirekterAngriff {
    private Player player;
    private int id;

    public DirekterAngriff(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        cardGame.continueToDirectAttack = false;
        CardState card = cardGame.getCardOfId(id);
        cardGame.cd.showDirectAttack(card);

        card.hasAttackOnTurn = true;
        cardGame.spielerPunkteAendern(cardGame.getOpOfP(player), -card.atk, PunkteArt.Leben, false);

        if (cardGame.getOpOfP(player).lifeCounter > 0) {
            cardGame.addEffektToList(card.id, Trigger.triggerDirekterAngriff, -1);
            cardGame.addEffektToList(card.id, Trigger.triggerhatAngegriffen, -1);
            cardGame.addEffekteToList(cardGame.getOpOfP(player).handCards, Trigger.triggerOnHandDamageDirekterAngriff, card.id);
            cardGame.switchState(State.boardState);
            cardGame.resolve();
        }
    }
} 