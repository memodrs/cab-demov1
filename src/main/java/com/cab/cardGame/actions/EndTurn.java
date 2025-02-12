package com.cab.cardGame.actions;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class EndTurn {
    private Player player;

    public EndTurn(Player player) {
        this.player = player;
    }

    public void execute(CardGame cardGame) {
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
            cardGame.karteSchaden(player, id, 2, false, true);
        }
        for (Integer id : cardsToZerstoeren) {
            cardGame.karteVomBoardInFriedhof(player, id, false, true);
        }
        for (Art art : Art.values()) {
            cardGame.setBlockAufrufArtNextTurn(player, false, art, false);
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