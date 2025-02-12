package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVomBoardInFriedhof {
    private Player player;
    private int id;
    private boolean ignoreResolve;

    public KarteVomBoardInFriedhof(Player player, int id, boolean ignoreResolve) {
        this.player = player;
        this.id = id;
        this.ignoreResolve = ignoreResolve;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            cardGame.removeCardFromBoard(player, card);
            cardGame.addCardToGrave(player, card, true);

            cardGame.addEffektToList(card.id, Trigger.triggerAfterDestroyed, -1);

            cardGame.addEffekteToList(player.boardCards, Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
            cardGame.addEffekteToList(player.boardCards, Trigger.triggerOnZerstoertPlayerKreaturZerstoert, card.id);

            cardGame.addEffekteToList(cardGame.getOpOfP(player).boardCards, 
                Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
            cardGame.addEffekteToList(cardGame.getOpOfP(player).boardCards, 
                Trigger.triggerOnZerstoertOponentKreaturZerstoert, card.id);

            if (player.isPlayer) {
                cardGame.switchState(State.graveState);
            } else {
                cardGame.switchState(State.graveOponentState);
            }

            if (!ignoreResolve) {
                cardGame.resolve();
            }
        }
    }
} 