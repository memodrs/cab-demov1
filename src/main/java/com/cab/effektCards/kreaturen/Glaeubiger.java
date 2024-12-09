package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;;

public class Glaeubiger extends EffektCardState {

	public Glaeubiger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        int anzahlMenschenAufDemBoard = 0;
        for (CardState card : cardGame.player.boardCards) {
            if (card.art == Art.Mensch) {
                anzahlMenschenAufDemBoard++;
            }
        }
		cardGame.spielerPunkteAendern(cardGame.player, anzahlMenschenAufDemBoard, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}