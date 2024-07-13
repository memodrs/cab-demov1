package com.cab.cardGame;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;

public class CardState {
	public CardGame cardGame;
	public Card defaultCard;
	public int id;
	public int atk;
	public int life;
	public Art art;
	
	//Effelt States
	public boolean isEffectActivate = false;
	public boolean isEffectActivateInTurn = false;

	
	//Effekt Variablen 
	public boolean isEffekt = false;
	public int selectState;
	public int nextStateForPlayer;
	public int triggerState;

	
	//auf dem Board
	public boolean isHide; //Verdeckt oder Offen
	public boolean hasAttackOnTurn = false; // darf angreifen
	public Status status;


	public int poisenCounter = 0;

	public CardState(Card card, CardGame cardGame) {
		this.defaultCard = card;
		this.atk = card.atk;
		this.life = card.def;
		this.art = card.art;
		this.status = Status.Default;
		this.cardGame = cardGame;
	}
	
	public void resetStatsToLeaveBoard(Player p) {
		atk = defaultCard.atk;
		life = defaultCard.def;
		art = defaultCard.art;
		isHide = false;
		status = Status.Default;
		poisenCounter = 0;
		hasAttackOnTurn = false;
		removeBlocks(p);
	}

	public void setIsEffektActivate(boolean isActivate) {
		isEffectActivate = isActivate;
	}

	public void setIsEffektActivateInTurn(boolean isActivate) {
		isEffectActivateInTurn = isActivate;
	}
	
	//Wird von den Effekt-Cards ueberschrieben
	public boolean isEffektPossible(Player p) {
		return false;
	}
	
	public void effekt(Player p, Integer selectedIdx) {}
	
	public boolean isCardValidForSelection(CardState card) {
		return true;
	}

	public void removeBlocks(Player p) {};
	public void removeBeforeAttackEffekt(Player p) {};
}
