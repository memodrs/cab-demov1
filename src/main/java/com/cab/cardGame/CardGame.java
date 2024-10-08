package com.cab.cardGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Status;

public class CardGame {
	public GamePanel gp;
	public CardGameDrawer cd;
	public CardGameUpdater cu;
	public EffekteMangaer effekteMangaer;

	//Config
	int limitCardsInHand = 7;
	int limitBoardCards = 4;

	public boolean isOnline;
	public Player player;
	public Player oponent;
	
	//Selections	
	int selectedIdx;
	int selectedHandCardIdx;
	int selectedBoardCardIdx;
	int selectGraveCardIdx;
	
	CardState activeEffektCard;
	
	//States
	final int handCardState = 0;
	final int handCardSelectedState = 1;	
	final int boardState = 2;
	final int boardCardSelectedState = 3;	
	final int boardOponentState = 4;
	final int selectCardToAttackState = 5;
	final int graveState = 6;
	final int graveSelectedState = 7;
	final int graveOponentState = 8;
	final int graveSelectedOponentState = 9;
	final int effektSelectOponentBoardState = 10;
	final int effektSelectOponentGraveState = 11;
	final int effektSelectOwnBoardState = 12;
	final int effektSelectOwnGraveState = 13;
	final int effektQuestionStateBoard = 14;
	final int effektQuestionStateHand = 15;
	final int effektQuestionStateGrave = 16;
	final int spellGraveState = 17;
	final int spellGraveOponentState = 18;
	final int gameFinishedState = 20;

	int currentState;
	
	boolean continueToAttackPhaseTwo;
	boolean continueToAttackPhaseThree;

	public int savedIdPlayerAttack;
	public int savedIdOpAttack;

	//Duell Logik
	boolean isOnTurn;
	boolean isFirstTurn;
	boolean inactiveMode; 
	public boolean creatureWasPlayedInTurn;
	public boolean hasAttackInTurn;
	
	//BoardStates
	public int blockEffektNachtgestalten;
	public int blockAngriffTiereOponent;
	public int blockAngriffTierePlayer;
	public int blockAngriffMenschenOponent;
	public int blockAngriffMenschenPlayer;

	List<Effekt> effektList;


	public CardGame(GamePanel gp) {
		this.gp = gp;
	}
	
	public void createGame(List<Integer> stapelOponent, boolean isPlayerStart, boolean isOnline) {
		this.effekteMangaer = new EffekteMangaer(this);
		this.cd = new CardGameDrawer(this);
		this.cu = new CardGameUpdater(this, gp.keyH);

		selectedIdx = 0;
		currentState= handCardState;
		continueToAttackPhaseTwo = false;
		continueToAttackPhaseThree = false;

		isOnTurn = false;
		isFirstTurn = false;
		inactiveMode = false; 
		creatureWasPlayedInTurn = false;
		hasAttackInTurn = false;

		blockEffektNachtgestalten = 0;
		blockAngriffTiereOponent = 0;
		blockAngriffTierePlayer = 0;
		blockAngriffMenschenOponent = 0;
		blockAngriffMenschenPlayer = 0;
	
		isOnTurn = isPlayerStart;
		inactiveMode = !isPlayerStart;
		isFirstTurn = isPlayerStart;

		effektList = new ArrayList<>();

		this.isOnline = isOnline;		
		this.player = new Player(gp.player.stapel, "Spieler", this, true);
		this.oponent = new Player(stapelOponent, "Gegner", this, false);
		
		if (isOnTurn) {
			cd.showMsg("Du bist am Zug");
		} else {
			cd.showMsg("Dein Gegner ist am Zug");
		}

		int startwertPlayer = isPlayerStart ? 0 : 100;
		int startwertOponent = isPlayerStart ? 100 : 0;
		
		for (int i = 0; i < player.stapel.size(); i++) {
			player.stapel.get(i).id = i + startwertPlayer;
		}
		
		for (int i = 0; i < oponent.stapel.size(); i++) {
			oponent.stapel.get(i).id = i + startwertOponent;
		}

		kartenMischen(player, player.stapel, isOnline);
		kartenZiehen(player, 5, true);

		if (!isOnline) {
			kartenMischen(oponent, oponent.stapel, false);
			kartenZiehen(oponent, 5, false);
		}

		gp.stopMuic();
		gp.playMusic(5);
	}

	public void switchState(int state) {
		if (currentState != gameFinishedState) {
			selectedIdx = 0;
			currentState = state;
		}
	}

	public void send(Boolean send, Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (send && isOnline) {
			gp.connection.send(isPlayer, argIntOne, argIntTwo, argBoolean, aBooleanTwo, art, array, argString, msg);
		}
	}

	public void setBlockEffektNachtgestalt(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffektNachtgestalt");
		blockEffektNachtgestalten += isBlock ? 1 : -1;
	}

	public void setBlockAngriffTierePlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffTiereOponent");
		blockAngriffTierePlayer += isBlock ? 1 : -1;
	}

	public void setBlockAngriffMenschenPlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffMenschenOponent");
		blockAngriffMenschenPlayer += isBlock ? 1 : -1;
	}

	public void setBlockAngriffTiereOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffTierePlayer");
		blockAngriffTiereOponent += isBlock ? 1 : -1;
	}

	public void setBlockAngriffMenschenOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffMenschenPlayer");
		blockAngriffMenschenOponent += isBlock ? 1 : -1;
	}

	public boolean containsCardId(List<CardState> cards, int id) {
		for (CardState card : cards) {
			if (card.id == id) {
				return true;
			}
		} 
		return false;
	}

	public void resumeState() {
		if (effektList.size() > 0) {
			resolve();
		} else {
			if (isOnTurn) {
				inactiveMode = false;
				if (continueToAttackPhaseTwo) {
					attackPhaseTwo(player, savedIdPlayerAttack, savedIdOpAttack, true);
				} else if (continueToAttackPhaseThree) {
					attackPhaseThree(player, savedIdPlayerAttack, savedIdOpAttack, true);
				}
			}
		}
	}
	
	public void addEffektToChain(Player p, int id, int trigger, int idArgForEffekt) {
		CardState effektCard = getCardOfId(id);
		if (isEffektPossible(p, trigger, effektCard)) {
			effektList.add(new Effekt(p, id, trigger, idArgForEffekt));
		}
	}

	public void manualEffekt(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "manualEffekt");
		CardState effektCard = getCardOfId(id);
		effektCard.setIsEffektActivate(true);
		effektCard.setIsEffektActivateInTurn(true);
		cd.showEffektCard(effektCard);
	}

	public void resolve() {

		if (effektList.size() > 0) {
			Effekt effekt = effektList.get(0);
			effektList.remove(0);
	
			if (isEffektPossible(effekt.p, effekt.trigger, getCardOfId(effekt.id))) {
				CardState effektCard = getCardOfId(effekt.id);
				effektCard.setIsEffektActivate(true);
				effektCard.setIsEffektActivateInTurn(true);
				cd.showEffektCard(effektCard);
		
				if (effekt.p.isPlayer) {
					handleEffekt(effekt.p, effekt.id, effekt.idArgForEffekt, false);
				} else {
					inactiveMode = true;
				}
			} else if (effektList.size() > 0) {
				resolve();
			}
		}
	}

	public void handleEffekt(Player p, int id, int idArgForEffekt, boolean isSelected) {
		CardState effektCard = getCardOfId(id);
		if (effektCard.selectState == effekteMangaer.ignoreState || isSelected) {
			effektCard.effekt(p, idArgForEffekt);
			
			if (effektCard.defaultCard.isSpell) {
				if (effektCard.art == Art.Segen) {
					spielerPunkteAendern(p, -effektCard.defaultCard.kosten, PunkteArt.Segen, true);
				} else if (effektCard.art == Art.Fluch) {
					spielerPunkteAendern(p, -effektCard.defaultCard.kosten, PunkteArt.Fluch, true);
				} else {
					throw new Error("Activate Effekt Unbekannte Spell Art " + effektCard.art);
				}
				karteVonHandAufSpellGrave(p, id, true);
			} 

			send(true, null, null, null, null, null, null, null, null, "resumeAfterEffekt");
			
			if (!isOnTurn) {
				inactiveMode = true;
			}

			switchState(effektCard.nextStateForPlayer);
			resumeState();

		} else {
			inactiveMode = false;
			activeEffektCard = effektCard;
			switchState(effektCard.selectState);
		}
	}

	public void kartenZiehen(Player p, int numberOfCards, boolean send) {
		send(send, p.isPlayer, numberOfCards, null, null, null, null, null, null, "moveCardFromStapelToHand");

		for (int i = 0; i < numberOfCards; i++) {
			if (p.stapel.size() > 0 && p.handCards.size() < limitCardsInHand) {
				int idx = p.stapel.size() - 1;
				CardState card = p.stapel.get(idx);
				p.stapel.remove(card);
				p.handCards.add(card);
				gp.playSE(1);	
			} 
		}
	}

	public void kreaturAufrufen(Player p, int idx, boolean hide, boolean isSpecial, boolean send) {
		send(send, p.isPlayer, idx, null, hide, isSpecial, null, null, null, "moveCardFromHandToBoard");
		
		CardState card = p.handCards.get(idx);

		card.isHide = hide;

		p.handCards.remove(card);
		p.boardCards.add(card);

		gp.playSE(1);	

		if (!isSpecial) {
			creatureWasPlayedInTurn = true;
		}

		if (!hide) {
			card.setDefaultStatus();

			if (card.art == Art.Fabelwesen) {
				spielerPunkteAendern(p, 1, PunkteArt.Segen, false);
			} else if (card.art == Art.Nachtgestalt) {
				spielerPunkteAendern(p, 1, PunkteArt.Fluch, false);
			}

			for (int i = 0; i < p.boardCards.size(); i++) {
				addEffektToChain(p, p.boardCards.get(i).id, effekteMangaer.triggerOnBoardPlayerKreaturAufgerufen, card.id);
			}

			addEffektToChain(p, card.id, effekteMangaer.triggerKreaturAufrufen, -1);

			for (int i = 0; i < getOponentForPlayer(p).boardCards.size(); i++) {
				addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).boardCards.get(i).id, effekteMangaer.triggerOnBoardOponentKreaturAufgerufen, card.id);
			}
		} 
		
		resolve();
	}

	public void kreaturAufrufenVomStapel(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "moveCardFromStapelToBoard");
		CardState card = p.stapel.get(idx);
		
		p.stapel.remove(card);
		p.boardCards.add(card);

		gp.playSE(1);	

		card.setDefaultStatus();

		if (card.art == Art.Fabelwesen) {
			spielerPunkteAendern(p, 1, PunkteArt.Segen, false);
		} else if (card.art == Art.Nachtgestalt) {
			spielerPunkteAendern(p, 1, PunkteArt.Fluch, false);
		}


		for (int i = 0; i < p.boardCards.size(); i++) {
			addEffektToChain(p, p.boardCards.get(i).id, effekteMangaer.triggerOnBoardPlayerKreaturAufgerufen, card.id);
		}
		for (int i = 0; i < getOponentForPlayer(p).boardCards.size(); i++) {
			addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).boardCards.get(i).id, effekteMangaer.triggerOnBoardOponentKreaturAufgerufen, card.id);
		}
		resolve();
	}

	public void direkterAngriff(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "directAttack");
		CardState card = p.boardCards.get(idx);
		cd.showDirectAttack(card);

		card.hasAttackOnTurn = true;
		spielerPunkteAendern(getOponentForPlayer(p), -card.atk, PunkteArt.Leben, false);
		addEffektToChain(p, card.id, effekteMangaer.triggerDirekterAngriff, -1);

		for (int i = 0; i < getOponentForPlayer(p).handCards.size(); i++) {
			addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).handCards.get(i).id, effekteMangaer.triggerOnHandDamageDirekterAngriff, card.id);
		}
		switchState(boardState);
		resolve();
	}

	public void changeSavedIdPlayerAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "changeSavedIdPlayerAttack");
		savedIdPlayerAttack = id;
	}

	public void changeSavedIdOponentAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "changeSavedIdOponentAttack");
		savedIdOpAttack = id;
	}

	public void attackPhaseOne(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, "attackPhaseOne");
		savedIdPlayerAttack = idPlayer;
		savedIdOpAttack = idOponent;
		addEffektToChain(p, idPlayer, effekteMangaer.triggerAngriffSetupAngreifer, idOponent);
		addEffektToChain(getOponentForPlayer(p), idOponent, effekteMangaer.triggerAngriffSetupVerteidiger, idPlayer);

		if (effektList.size() > 0) {
			continueToAttackPhaseTwo = true;
			resolve();
		} else {
			if (isOnTurn) {
				attackPhaseTwo(p, savedIdPlayerAttack, savedIdOpAttack, true);
			}
		}
	}

	public void attackPhaseTwo(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, savedIdPlayerAttack, savedIdOpAttack, null, null, null, null, null, "attackPhaseTwo");
		continueToAttackPhaseTwo = false;

		CardState angreifer = getCardOfId(savedIdPlayerAttack);
		CardState verteidiger = getCardOfId(savedIdOpAttack);

		if (verteidiger.statusSet.contains(Status.Schild)) {
			angreifer.hasAttackOnTurn = true;
			verteidiger.statusSet.remove(Status.Schild);
			cd.showAttackOnSchild(angreifer, verteidiger);
			switchState(boardState);
		} else {
			addEffektToChain(p, angreifer.id, effekteMangaer.triggerBeforeKarteAngreift, angreifer.id);
			addEffektToChain(getOponentForPlayer(p), verteidiger.id, effekteMangaer.triggerBeforeKarteWirdAngegriffen, angreifer.id);

			if (effektList.size() > 0) {
				continueToAttackPhaseThree = true;
				resolve();
			} else {
				if (isOnTurn) {
					attackPhaseThree(p, savedIdPlayerAttack, savedIdOpAttack, true);
				}
			}
		}
	}

	public void attackPhaseThree(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, "attackPhaseThree");
		continueToAttackPhaseThree = false;
		hasAttackInTurn = true;
		
		Player op = getOponentForPlayer(p);

		CardState verteidiger = getCardOfId(idOponent);
		CardState angreifer = getCardOfId(idPlayer);

		if (verteidiger.isHide && verteidiger.atk > angreifer.atk) {
			cd.showAttackOnCardSelbstzerstoerung(angreifer, verteidiger);
			karteVomBoardZerstoeren(p, angreifer.id, false, true);
			addEffektToChain(p, angreifer.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
		} else if (verteidiger.isHide && verteidiger.atk == angreifer.atk) {
			cd.showAttackOnCardDoppelZerstoerung(angreifer, verteidiger);
			karteVomBoardZerstoeren(p, angreifer.id, false, true);
			addEffektToChain(p, angreifer.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToChain(p, angreifer.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
			karteVomBoardZerstoeren(op, verteidiger.id, false, true);
			addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
		} else {
			if (verteidiger.life > angreifer.atk) {
				cd.showAttackOnCardSchaden(angreifer, verteidiger);
				verteidiger.life = verteidiger.life - angreifer.atk;
				addEffektToChain(p, angreifer.id, effekteMangaer.triggerSchadenZugefuegtDurchAngriff, verteidiger.id);
			} else {
				cd.showAttackOnCardZersteorung(angreifer, verteidiger);
				karteVomBoardZerstoeren(op, verteidiger.id, false, true);
				addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, angreifer.id);
				addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, angreifer.id);
				addEffektToChain(p, angreifer.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
			}

			addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, angreifer.id);
		}

		addEffektToChain(p, angreifer.id, effekteMangaer.triggerAfterDoAttack, verteidiger.id);

		angreifer.hasAttackOnTurn = true;
		verteidiger.isHide = false;	

		angreifer.removeBeforeAttackEffekt(p);
		verteidiger.removeBeforeAttackEffekt(p);

		switchState(boardState);
		resolve();
	}

	public void karteVomBoardZerstoeren(Player p, int id, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToGrave");
		if (p.isPlayer) {
			switchState(graveState);
		} else {
			switchState(graveOponentState);
		}
		CardState card = getCardOfId(id);
		p.boardCards.remove(card);
		p.graveCards.add(card);

		card.resetStatsToLeaveBoard(p);


		for (int i = 0; i < p.boardCards.size(); i++) {
			addEffektToChain(p, p.boardCards.get(i).id, effekteMangaer.triggerOnZerstoertPlayerKreaturZerstoert, card.id);
		}

		for (int i = 0; i < getOponentForPlayer(p).boardCards.size(); i++) {
			addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).boardCards.get(i).id, effekteMangaer.triggerOnZerstoertOponentKreaturZerstoert, card.id);
		}

		addEffektToChain(p, card.id, effekteMangaer.triggerAfterDestroyed, -1);

		if (!ignoreResolve) {
			resolve();
		}
	}

	
	public void spielerPunkteAendern(Player p, int punkte, PunkteArt art, boolean send) {
		send(send, p.isPlayer, punkte, null, null, null, null, null, art.toString(), "spielerPunkteAendern");

		if (art == PunkteArt.Fluch) {
			p.fluchCounter += punkte;
		} else if (art == PunkteArt.Segen) {
			p.segenCounter += punkte;
		} else if (art == PunkteArt.Leben) {
			p.lifeCounter += punkte;
			
			if (p.lifeCounter <= 0) {
				p.lifeCounter = 0;
				switchState(gameFinishedState);
			}
			
		} else {
			throw new Error("Unbekannte Punkte Art " + art);
		}
	}

	public void kartenMischen(Player p, List<CardState> cards, boolean send) {
		String posName = "";
		if (cards == p.boardCards) {
			posName = "board";
		} else if (cards == p.stapel) {
			posName = "stapel";
		} else if (cards == p.handCards) {
			posName = "hand";
		}
		List<Integer> reihenfolge = new ArrayList<>();
		for (int i = 0; i < cards.size(); i++) {
			reihenfolge.add(i);
		}
		Collections.shuffle(reihenfolge);
		int[] arg = reihenfolge.stream().mapToInt(Integer::intValue).toArray();
		
		sortKarten(p, arg, posName, send);
	}

	public void sortKarten(Player p, int[] reihenfolge, String posName, boolean send) {
		send(send, p.isPlayer, null, null, null, null, null, reihenfolge, posName, "sortCards");
		
		List<CardState> sortedList = new ArrayList<>();

		if (posName.equals("board")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.boardCards.get(reihenfolge[i]));
			}	
			p.boardCards = sortedList;	
		} else if (posName.equals("stapel")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.stapel.get(reihenfolge[i]));
			}	
			p.stapel = sortedList;	
		} else if (posName.equals("hand")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.handCards.get(reihenfolge[i]));
			}	
			p.handCards = sortedList;	
		} else {
			throw new Error("sortiere Liste, unbekannte posName " + posName);
		}
	}

	public void karteDrehen(Player p, int id, boolean isHide, boolean send) {
		send(send, p.isPlayer, id, null, isHide, null, null, null, null, "setHide");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.isHide = isHide;

			if (isHide) {
				card.resetStatsToHide(p);
			} else {
				card.setDefaultStatus();
			}

			gp.playSE(1);	
		}
	}

	public void karteSchaden(Player p, int id, int schaden, boolean send) {
		send(send, p.isPlayer, id, schaden, null, null, null, null, null, "setSchadenOfBoardCard");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			if (card.life <= schaden) {
				karteVomBoardZerstoeren(p, id, false, false);
			} else {
				card.life = card.life - schaden;
				cd.showAnimKarteStatsAenderung(p, card, false);
			}
		}
	}

	public void karteHeilen(Player p, int id, int punkte, boolean send) {
		send(send, p.isPlayer, id, punkte, null, null, null, null, null, "setHeilenOfBoardCard");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.life = card.life + punkte;
			cd.showAnimKarteStatsAenderung(p, card, true);
		}
	}

	public void karteAngriffVerringern(Player p, int id, int punkte, boolean send) {
		send(send, p.isPlayer, id, punkte, null, null, null, null, null, "setAtkVerringernOfCardOnBoard");

		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			if (punkte > card.atk) {
				card.atk = 0;
			} else {
				card.atk = card.atk - punkte;
			}
			cd.showAnimKarteStatsAenderung(p, card, false);
		}
	}

	public void karteAngriffErhoehen(Player p, int id, int punkte, boolean send) {
		send(send, p.isPlayer, id, punkte, null, null, null, null, null, "setAtkErhoehenOfCardOnBoard");

		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.atk = card.atk + punkte;
			cd.showAnimKarteStatsAenderung(p, card, true);	
		}
	}

	public void setKarteStatus(Player p, int id, boolean isStatus, Status status, boolean send) {
		send(send, p.isPlayer, id, null, isStatus, null, null, null, status.toString(), "setKarteStatus");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			if (isStatus) {
				if (!card.statusSet.contains(status)) {
					card.statusSet.add(status);
				}
			} else {
				if (card.statusSet.contains(status)) {
					card.statusSet.remove(status);
				}
			}
		}
	}

	public void setArtOfCardOnBoard(Player p, int id, Art art, boolean send) {
		send(send, p.isPlayer, id, null, null, null, art, null, null, "setArtOfCardOnBoard");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.art = art;
		}
	}

	public void karteVonHandAufSpellGrave(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandAufSpellGrave");
		CardState card = getCardOfId(id);
		p.handCards.remove(card);
		p.spellGraveCards.add(card);
	}

	public void karteVomFriedhofInDieHandNehmen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToHand");
		CardState card = getCardOfId(id);
		p.graveCards.remove(card);
		p.handCards.add(card);
		card.isEffectActivate = false;
	}

	public void karteVomBoardInDieHandGeben(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToHand");
		CardState card = getCardOfId(id);
		p.boardCards.remove(card);
		p.handCards.add(card);
		card.resetStatsToLeaveBoard(p);
	}

	public void karteVomFriedhofAufrufen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToBoard");
		CardState card = getCardOfId(id);
		p.graveCards.remove(card);
		p.boardCards.add(card);

		card.setDefaultStatus();
		if (card.art == Art.Fabelwesen) {
			spielerPunkteAendern(p, 1, PunkteArt.Segen, false);
		} else if (card.art == Art.Nachtgestalt) {
			spielerPunkteAendern(p, 1, PunkteArt.Fluch, false);
		}
	}

	public void karteVonHandAufDenStapel(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "moveCardFromHandToStapel");

		CardState card = p.handCards.get(idx);
		p.handCards.remove(card);
		p.stapel.add(card);
		gp.playSE(1);	
	}

	public void karteKontrolleUebernehmen(Player p, int opId, boolean send) {
		send(send, p.isPlayer, opId, null, null, null, null, null, null, "moveCardFromOponentBoardToOwnBoard");

		Player op = getOponentForPlayer(p);
		CardState card = getCardOfId(opId);
		op.boardCards.remove(card);
		p.boardCards.add(card);
	}

	public void kartenTauschenHand(Player p, int idP, int idOp, boolean send) {
		send(send, p.isPlayer, idP, idOp, null, null, null, null, null, "switchHandCardsWithOponent");

		Player op = getOponentForPlayer(p);
		CardState cardP = getCardOfId(idP);
		CardState cardOp = getCardOfId(idOp);
		
		p.handCards.remove(cardP);
		op.handCards.remove(cardOp);
		
		p.handCards.add(cardOp);
		op.handCards.add(cardP);
	}

	public void endTurn() {
		if (isFirstTurn) {
			isFirstTurn = false;
		}

		creatureWasPlayedInTurn = false;
		hasAttackInTurn = false;
		updateAllBoardCardsForPlayer(player);
		inactiveMode = true;
		isOnTurn = false;
		cd.showMsg("Gegner ist am Zug");

		if (isOnline) {
			gp.connection.send(null, null, null, null, null, null, null, null, "playerEndTurn");
		}
	}

	public void startTurn() {
		creatureWasPlayedInTurn = false;
		hasAttackInTurn = false;
		updateAllBoardCardsForPlayer(oponent);
		kartenZiehen(player, 1, true);
		inactiveMode = false;
		isOnTurn = true;
		cd.showMsg("Du bist am Zug");
	}

	public void updateAllBoardCardsForPlayer(Player p) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			CardState card = p.boardCards.get(i);
			card.hasAttackOnTurn = false;
			card.isEffectActivateInTurn = false;
			
			if (card.statusSet.contains(Status.Gift)) {
				card.poisenCounter++;
				if (card.poisenCounter == 2) {
					karteVomBoardZerstoeren(p, card.id, false, false);
				}
			}	

			if (card.statusSet.contains(Status.Feuer)) {
				karteSchaden(p, card.id, 2, false);
			}
			
							
		}
	}

	//Get Methoden
	public Player getOponentForPlayer(Player p) {
		if (p == player) {
			return oponent;
		} else {
			return player;
		}
	}

	public int getIdxOfCard(List<CardState> cards, CardState card) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i) == card) {
				return i;
			}
		}
		return -1;
	}

	public CardState getCardOfId(int id) {
		Player[] players = {player, oponent};
		for (Player p : players) {
			for (CardState card : p.boardCards) {
				if (card.id == id) {
					return card;
				}
			}
			for (CardState card : p.handCards) {
				if (card.id == id) {
					return card;
				}
			}
			for (CardState card : p.graveCards) {
				if (card.id == id) {
					return card;
				}
			}
		}
		return null;
	}

	//Check Methoden
	public boolean isPlayCreatureFromHandAllowed(Player p) {
		return !creatureWasPlayedInTurn && p.boardCards.size() < limitBoardCards && !hasAttackInTurn;
	}

	private boolean isAngriffBlockiert(Player p, CardState card) {
		return 	(card.art == Art.Tier && (blockAngriffTierePlayer > 0) && p.isPlayer) || 
			   	(card.art == Art.Tier && (blockAngriffTiereOponent > 0) && !p.isPlayer) ||
				(card.art == Art.Mensch && (blockAngriffMenschenPlayer > 0) && p.isPlayer) || 
				(card.art == Art.Mensch && (blockAngriffMenschenOponent > 0) && !p.isPlayer);
	}

	public boolean checkIsAttackAlowed(Player p, int boardIdx) {
		CardState card = p.boardCards.get(boardIdx);

		boolean tmp = false;
		if (card.art == Art.Fabelwesen) {
			tmp = isArtOnBoardOfPlayer(p, Art.Mensch);
		} else if (card.art == Art.Nachtgestalt) {
			tmp = !isArtOnBoardOfPlayer(p, Art.Mensch);
		} else {
			tmp = true;
		}
		
		return !card.hasAttackOnTurn && tmp && !isFirstTurn && !isAngriffBlockiert(p, card) && !card.statusSet.contains(Status.Blitz);
	}

	private boolean isEffektBlockiert(CardState card) {
		return (card.art == Art.Nachtgestalt && (blockEffektNachtgestalten > 0));
	}

	public boolean isEffektManualActivatable(Player p, CardState card, int manualTrigger) {
		return !card.defaultCard.isSpell && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && !card.isHide && !inactiveMode;
	}

	public boolean isSpellActivatable(Player p, CardState card) {
		return ((card.art == Art.Fluch && card.defaultCard.kosten <= p.fluchCounter) || (card.art == Art.Segen && card.defaultCard.kosten <= p.segenCounter)) && card.isEffektPossible(p);
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(p) && card.triggerState == trigger && !isEffektBlockiert(card);
	}

	public boolean hasPlayerOpenCardsOnBoard(Player p) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			if (!p.boardCards.get(i).isHide) {
				return true;
			} 
		} return false;
	}
	
	public boolean isArtOnBoardOfPlayer(Player p, Art art) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			if (p.boardCards.get(i).art == art && !p.boardCards.get(i).isHide) {
				return true;
			}
		}
		return false;
	}

	public boolean isState(int state) {
		return currentState == state;
	}
	
	public void update() {
		cu.update();
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());     
		cd.draw(g2);
	}
	
	public void simulateOponentTurn() {
		kartenZiehen(oponent, 1, false);
	}
}
