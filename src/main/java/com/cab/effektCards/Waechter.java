package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Waechter extends EffektCardState implements EffektCard {

	public Waechter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer id) {   
        cardGame.setKarteStatus(p, id, true, Status.Schild, true);
     }

    @Override
    public boolean isEffektPossible(Player p) {
        return isCardWithoutSchildOnBoard(p) && !isEffectActivateInTurn;
    }

    @Override
    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Schild);
    }



    private boolean isCardWithoutSchildOnBoard(Player p) {
        boolean tmp = false; 

        for (int i = 0; i < p.boardCards.size(); i++) {
            if (!p.boardCards.get(i).statusSet.contains(Status.Schild) && !p.boardCards.get(i).isHide) {
                tmp = true;
            }
        }
        
        return tmp;
    }
}
