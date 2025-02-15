package com.cab.cardGame.model;

import java.util.HashSet;
import java.util.Set;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;

public class CardState {
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
	public boolean wasPlayedInTurn;
	public boolean isHide; //Verdeckt oder Offen
	public boolean hasAttackOnTurn = false; // darf angreifen
	public Set<Status> statusSet;
	public boolean blockAttackOnTurn = false;

	public CardState(Card card) {
		this.defaultCard = card;
		this.atk = card.getAtk();
		this.life = card.getLife();
		this.art = card.getArt();
		this.statusSet = new HashSet<>();
	}

	public void resetStatsToLeaveBoard() {
		atk = defaultCard.getAtk();
		life = defaultCard.getLife();
		art = defaultCard.getArt();
		isHide = false;
		statusSet = new HashSet<>();
		blockAttackOnTurn = false;
		hasAttackOnTurn = false;
	}

	public void resetStatsToHide() {
		atk = defaultCard.getAtk();
		life = defaultCard.getLife();
		art = defaultCard.getArt();
		statusSet = new HashSet<>();
		blockAttackOnTurn = false;
	}

	public void resetStatsOnEndTurn() {
		hasAttackOnTurn = false;
		wasPlayedInTurn = false;
		isEffectActivateInTurn = false;
		blockAttackOnTurn = false;
	}

	public void setDefaultStatus() {
		if (defaultCard.getStatus() != Status.Default) {
			statusSet.add(defaultCard.getStatus());
		}
	}

	public void emptyStausSet() {
		statusSet = new HashSet<>();
	}

	public void setIsEffektActivate(boolean isActivate) {
		isEffectActivate = isActivate;
	}

	public void setIsEffektActivateInTurn(boolean isActivate) {
		isEffectActivateInTurn = isActivate;
	}
	
	
	public boolean isEffektPossible(CardGame cardGame) {return false;}
	public void effekt(CardGame cardGame, Integer arg) {}
	public void setBlock(CardGame cardGame) {};
	public void setUpOptionsToSelect(CardGame cardGame) {};
	public void removeBeforeAttackEffekt(CardGame cardGame) {};
}
