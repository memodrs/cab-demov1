package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVonHandAufBoard extends Action {
    public void execute(CardGame cardGame, Player player, int id, boolean isHide, boolean isSpecial, boolean send) {
      cardGame.send(send, player.isPlayer, id, null, isHide, isSpecial, null, null, null, Messages.KARTE_VON_HAND_AUF_BOARD);

      CardState card = cardGame.getCardOfId(id);
      if (cardGame.isCardInHand(card)) {
        if (!isSpecial) {
          --player.numberOfCreatureCanPlayInTurn;
        }
        cardGame.removeCardFromHand(player, card);
        cardGame.addCardToBoard(player, card, isHide);
        cardGame.playSE(2);	
        cardGame.resolve();
      }
    }
}
