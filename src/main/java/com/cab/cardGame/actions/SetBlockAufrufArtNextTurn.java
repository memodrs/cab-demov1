package com.cab.cardGame.actions;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class SetBlockAufrufArtNextTurn {
    public void execute(CardGame cardGame, Player player, boolean isBlock, Art art, boolean send, boolean ignoreResolve) {
        cardGame.send(send, player.isPlayer, null, null, isBlock, null, art, null, null, Messages.SET_BLOCK_AUFRUF_ART_NEXT_TURN);

        if (isBlock) {
            player.blockAufrufArtFromHand.add(art);
        } else {
            player.blockAufrufArtFromHand.remove(art);
        }
        if (!ignoreResolve) {
            cardGame.resolve();
        }
    }
} 