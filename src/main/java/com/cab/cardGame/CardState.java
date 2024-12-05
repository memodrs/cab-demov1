package com.cab.cardGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	public boolean wasPlayedInTurn;
	public boolean isHide; //Verdeckt oder Offen
	public boolean hasAttackOnTurn = false; // darf angreifen
	public Set<Status> statusSet;
	public boolean blockAttackOnTurn = false;


	public int poisenCounter = 0;

	public CardState(Card card, CardGame cardGame) {
		this.defaultCard = card;
		this.atk = card.getAtk();
		this.life = card.getLife();
		this.art = card.getArt();
		this.statusSet = new HashSet<>();
		this.cardGame = cardGame;
	}

	public void resetStatsToLeaveBoard() {
		atk = defaultCard.getAtk();
		life = defaultCard.getLife();
		art = defaultCard.getArt();
		isHide = false;
		statusSet = new HashSet<>();
		poisenCounter = 0;
		blockAttackOnTurn = false;
		hasAttackOnTurn = false;
	}

	public void resetStatsToHide() {
		atk = defaultCard.getAtk();
		life = defaultCard.getLife();
		art = defaultCard.getArt();
		statusSet = new HashSet<>();
		blockAttackOnTurn = false;
		poisenCounter = 0;
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
	
	public boolean isEffektPossible(Player p) {
		return false;
	}
	
	public void effekt(Integer arg) {}
	
	public boolean isCardValidForSelection(CardState card) {
		return true;
	}

	public void setBlock(Player p) {};

	public void setUpOptionsToSelect() {
		cardGame.optionsCardsToSelect = new ArrayList<>();
		cardGame.optionsToSelect = new ArrayList<>();
	};

	public void removeBeforeAttackEffekt(Player p) {};
}
