package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonHandAufBoard extends Action {
  
    Player p;
    int id;
    boolean isHide;
    boolean isSpecial;
    
    public KarteVonHandAufBoard(Player p, int id, boolean isHide, boolean isSpecial) {
        this.p = p;
        this.id = id;
        this.isHide = isHide;
        this.isSpecial = isSpecial;
    } 

    public void execute(CardGame cardGame) {
      CardState card = cardGame.getCardOfId(id);
      if (cardGame.isCardInHand(card)) {
        if (!isSpecial) {
          --p.numberOfCreatureCanPlayInTurn;
        }
        cardGame.removeCardFromHand(p, card);
        cardGame.addCardToBoard(p, card, isHide);
        cardGame.playSE(2);	
        cardGame.resolve();
      }
    }
}
