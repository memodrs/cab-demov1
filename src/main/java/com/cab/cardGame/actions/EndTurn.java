package com.cab.cardGame.actions;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class EndTurn {
    public void execute(CardGame cardGame, Player player, boolean send) {
        cardGame.send(send, player.isPlayer, null, null, null, null, null, null, null, Messages.END_TURN);

        player.resetStatsOnEndTurn();

        List<Integer> cardsToSchaden = new ArrayList<>();
        List<Integer> cardsToZerstoeren = new ArrayList<>();

        for (CardState card : player.boardCards) {
            card.resetStatsOnEndTurn();
            
            if (card.statusSet.contains(Status.Feuer)) {
                cardsToSchaden.add(card.id);
            }
            if (card.statusSet.contains(Status.Gift)) {
                cardsToZerstoeren.add(card.id);
            }   
        }

        for (Integer id : cardsToSchaden) {
            new KarteSchaden().execute(cardGame, player, id, 2, false, true);
        }
        for (Integer id : cardsToZerstoeren) {
            new KarteVomBoardInFriedhof().execute(cardGame, player, id, false, true);
        }
        for (Art art : Art.values()) {
            new SetBlockAufrufArtNextTurn().execute(cardGame, player, false, art, false, true);
        }
        cardGame.resolve();

        if (player.isPlayer && cardGame.oponent.isKI) {
            cardGame.ki.startTurn();
        } 

        if (!player.isPlayer) {
            cardGame.startTurn(cardGame.player);
        }
    }
} 