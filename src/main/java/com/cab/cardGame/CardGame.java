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
	final int selectOptionState = 19;
	final int gameFinishedState = 20;

	public int currentState;
	
	boolean continueToAttackPhaseTwo;
	boolean continueToAttackPhaseThree;

	public int savedIdPlayerAttack;
	public int savedIdOpAttack;

	public List<String> optionsToSelect;

	//Duell Logik
	boolean isOnTurn;
	boolean isFirstTurn;
	boolean inactiveMode; 
	public int numberOfCreatureCanPlayInTurn;
	
	//BoardStates
	public boolean blockEffektAll;
	public int blockEffektMenschenPlayer;
	public int blockEffektMenschenOponent;
	public int blockEffektTierePlayer;
	public int blockEffektTiereOponent;
	public int blockEffektFabelwesenPlayer;
	public int blockEffektFabelwesenOponent;
	public int blockEffektNachtgestaltenPlayer;
	public int blockEffektNachtgestaltenOponent;

	public int blockAngriffTiereOponent;
	public int blockAngriffTierePlayer;

	public int blockOneTurnAufrufMenschen;
	public int blockOneTurnAufrufTiere;
	public int blockOneTurnAufrufFabelwesen;
	public int blockOneTurnAufrufNachtgestalten;
	public int blockOneTurnAufrufUnbekannt;


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
		numberOfCreatureCanPlayInTurn = 1;

		blockEffektAll = false;
		blockEffektMenschenPlayer = 0;
		blockEffektMenschenOponent = 0;
		blockEffektTierePlayer = 0;
		blockEffektTiereOponent = 0;
		blockEffektFabelwesenPlayer = 0;
		blockEffektFabelwesenOponent = 0;
		blockEffektNachtgestaltenPlayer = 0;
		blockEffektNachtgestaltenOponent = 0;


		blockAngriffTiereOponent = 0;
		blockAngriffTierePlayer = 0;

		blockOneTurnAufrufMenschen = 0;
		blockOneTurnAufrufTiere = 0;
		blockOneTurnAufrufFabelwesen = 0;
		blockOneTurnAufrufNachtgestalten = 0;
		blockOneTurnAufrufUnbekannt = 0;

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

		//kartenMischen(player, player.stapel, isOnline);
		kartenZiehen(player, 5, true);

		if (!isOnline) {
			kartenMischen(oponent, oponent.stapel, false);
			kartenZiehen(oponent, 5, false);
		}

		gp.stopMuic();
		gp.playMusic(5);
	}

	public void send(Boolean send, Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (send && isOnline) {
			gp.connection.send(isPlayer, argIntOne, argIntTwo, argBoolean, aBooleanTwo, art, array, argString, msg);
		}
	}

	public void switchState(int state) {
		selectedIdx = 0;
		currentState = state;
	}

	//Board Status

	public void setBlockEffekte(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekte");
		blockEffektAll = isBlock;
	}

	public void setBlockEffekteMenschenOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteMenschenPlayer");
		blockEffektMenschenOponent = isBlock ? 1 : -1;
	}

	public void setBlockEffekteMenschenPlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteMenschenOponent");
		blockEffektMenschenPlayer = isBlock ? 1 : -1;
	}

	public void setBlockEffekteTiereOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteTierePlayer");
		blockEffektTiereOponent = isBlock ? 1 : -1;
	}

	public void setBlockEffekteTierePlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteTiereOponent");
		blockEffektTierePlayer = isBlock ? 1 : -1;
	}

	public void setBlockEffekteFabelwesenOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteFabelwesenPlayer");
		blockEffektFabelwesenOponent = isBlock ? 1 : -1;
	}

	public void setBlockEffekteFabelwesenPlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteFabelwesenOponent");
		blockEffektFabelwesenPlayer = isBlock ? 1 : -1;
	}

	public void setBlockEffekteNachtgestaltenOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteNachtgestaltenPlayer");
		blockEffektNachtgestaltenOponent = isBlock ? 1 : -1;
	}

	public void setBlockEffekteNachtgestaltenPlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockEffekteNachtgestaltenOponent");
		blockEffektNachtgestaltenPlayer = isBlock ? 1 : -1;
	}

	public void setBlockAngriffTiereOponent(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffTierePlayer");
		blockAngriffTiereOponent = isBlock ? 1 : -1;
	}

	public void setBlockAngriffTierePlayer(boolean isBlock, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  null, "setBlockAngriffTiereOponent");
		blockAngriffTierePlayer = isBlock ? 1 : -1;
	}


	public void setBlockOneTurnAufrufArt(boolean isBlock, Art art, boolean send) {
		send(send, null, null, null, isBlock, null,  null,  null,  art.toString(), "setBlockOneTurnAufrufArt");
		int counter = isBlock ? 1 : -1;
		switch (art) {
			case Mensch: 
				blockOneTurnAufrufMenschen += counter;
				break;
			case Tier: 
				blockOneTurnAufrufTiere += counter;
				break;
			case Fabelwesen: 
				blockOneTurnAufrufFabelwesen += counter;
				break;
			case Nachtgestalt: 
				blockOneTurnAufrufNachtgestalten += counter;
				break;
			default:
				break;
		}
	}

	//Effekt

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
			activeEffektCard.setUpOptionsToSelect();
			switchState(effektCard.selectState);
		}
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
	
	private void kreaturHatDasBoardOffenBetreten(Player p, CardState card) {
		p.boardCards.add(card);
		card.setDefaultStatus();
		setBlock(p, card);

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

		resolve();
	}

	private void karteHatBoardVerlassen(Player p, CardState card) {
		p.boardCards.remove(card);
		card.resetStatsToLeaveBoard(p);
		removeBlock(p, card);
	}

	// Karte in die Hand

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

	public void karteVomFriedhofInDieHandNehmen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToHand");
		CardState card = getCardOfId(id);
		p.graveCards.remove(card);
		p.handCards.add(card);
		gp.playSE(1);	
	}

	public void karteVomBoardInDieHandGeben(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToHand");
		CardState card = getCardOfId(id);
		karteHatBoardVerlassen(p, card);
		p.handCards.add(card);
		gp.playSE(1);	
	}

	// Karte auf das Board
	private void removeBlock(Player p, CardState card) {
		if (p.isPlayer && card.isBlockActiv) {
			card.removeBlock();
			
			for (CardState c : p.boardCards) {
				if (!isEffektBlockiert(p, c) && !c.isBlockActiv && c.triggerState == effekteMangaer.triggerPermanent) {
					c.setBlock();
				}
			}

			for (CardState c : getOponentForPlayer(p).boardCards) {
				if (!isEffektBlockiert(getOponentForPlayer(p), c) && !c.isBlockActiv && c.triggerState == effekteMangaer.triggerPermanent) {
					c.setBlock();
				}
			}
		}
	}

	private void setBlock(Player p, CardState card) {
		if (p.isPlayer && !isEffektBlockiert(p, card) && card.triggerState == effekteMangaer.triggerPermanent) {
			card.setBlock();
			
			for (CardState c : p.boardCards) {
				if (isEffektBlockiert(p, c) && c.isBlockActiv) {
					c.removeBlock();
				}
			}

			for (CardState c : getOponentForPlayer(p).boardCards) {
				if (isEffektBlockiert(getOponentForPlayer(p), c) && c.isBlockActiv) {
					c.removeBlock();
				}
			}
		}
	}

	public void kreaturAufrufen(Player p, int id, boolean hide, boolean send) {
		send(send, p.isPlayer, id, null, null, hide, null, null, null, "moveCardFromHandToBoard");
		CardState card = getCardOfId(id);
		card.isHide = hide;
		--numberOfCreatureCanPlayInTurn;
		p.handCards.remove(card);
		if (!hide) {
			kreaturHatDasBoardOffenBetreten(p, card);
		} else {
			p.boardCards.add(card);
		}
		gp.playSE(1);	
	}

	public void kreaturAufrufenVomStapel(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromStapelToBoard");
		CardState card = getCardOfId(id);
		p.stapel.remove(card);
		kreaturHatDasBoardOffenBetreten(p, card);
		gp.playSE(1);	
	}

	public void karteVomFriedhofAufrufen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToBoard");
		CardState card = getCardOfId(id);
		p.graveCards.remove(card);
		kreaturHatDasBoardOffenBetreten(p, card);
		gp.playSE(1);	
	}

	// Karte in den Friedhof

	public void karteVomBoardZerstoeren(Player p, int id, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToGrave");
		if (p.isPlayer) {
			switchState(graveState);
		} else {
			switchState(graveOponentState);
		}
		CardState card = getCardOfId(id);
		p.graveCards.add(card);
		karteHatBoardVerlassen(p, card);

		for (int i = 0; i < p.boardCards.size(); i++) {
			addEffektToChain(p, p.boardCards.get(i).id, effekteMangaer.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffektToChain(p, p.boardCards.get(i).id, effekteMangaer.triggerOnZerstoertPlayerKreaturZerstoert, card.id);
		}

		for (int i = 0; i < getOponentForPlayer(p).boardCards.size(); i++) {
			addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).boardCards.get(i).id, effekteMangaer.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffektToChain(getOponentForPlayer(p), getOponentForPlayer(p).boardCards.get(i).id, effekteMangaer.triggerOnZerstoertOponentKreaturZerstoert, card.id);
		}

		addEffektToChain(p, card.id, effekteMangaer.triggerAfterDestroyed, -1);

		if (!ignoreResolve) {
			resolve();
		}
	}

	// Karte auf den Stapel

	public void karteVonHandAufDenStapel(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "moveCardFromHandToStapel");
		CardState card = p.handCards.get(idx);
		p.handCards.remove(card);
		p.stapel.add(card);
		gp.playSE(1);	
	}

	// Besondere Bewegungen

	public void karteKontrolleUebernehmen(Player p, int opId, boolean send) {
		send(send, p.isPlayer, opId, null, null, null, null, null, null, "moveCardFromOponentBoardToOwnBoard");
		Player op = getOponentForPlayer(p);
		CardState card = getCardOfId(opId);
		removeBlock(op, card);
		op.boardCards.remove(card);
		p.boardCards.add(card);
		setBlock(p, card);
		gp.playSE(1);	
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
		gp.playSE(1);	
	}
	
	// Angriff
	
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

	public void attackPhaseThree(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, "attackPhaseThree");
		continueToAttackPhaseThree = false;
		
		Player op = getOponentForPlayer(p);

		CardState verteidiger = getCardOfId(idOponent);
		CardState angreifer = getCardOfId(idPlayer);

		addEffektToChain(op, verteidiger.id, effekteMangaer.triggerKarteWurdeAngegriffen, angreifer.id);

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
			if (verteidiger.statusSet.contains(Status.Schild)) {
				angreifer.hasAttackOnTurn = true;
				verteidiger.statusSet.remove(Status.Schild);
				cd.showAttackOnSchild(angreifer, verteidiger);
				switchState(boardState);
			} else if (verteidiger.life > angreifer.atk) {
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

		switchState(boardState);
		resolve();
	}

	// Spieler Punkte
	
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

	// Spieler Karten Mischen 

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

	// Karten Operationen

	public void karteDrehen(Player p, int id, boolean isHide, boolean send) {
		send(send, p.isPlayer, id, null, isHide, null, null, null, null, "setHide");
		
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.isHide = isHide;

			if (isHide) {
				card.resetStatsToHide(p);
				removeBlock(p, card);
			} else {
				card.setDefaultStatus();
				setBlock(p, card);
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

	public void setKarteHasAttackedOnTurn(Player p, int id, boolean hasAttackOnTurn, boolean send) {
		send(send, p.isPlayer, id, null, hasAttackOnTurn, null, null, null, null, "setKarteHasAttackedOnTurn");
		CardState card = getCardOfId(id);

		if (p.boardCards.contains(card)) {
			card.hasAttackOnTurn = hasAttackOnTurn;
		}
	}

	// Spell 

	public void karteVonHandAufSpellGrave(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandAufSpellGrave");
		CardState card = getCardOfId(id);
		p.handCards.remove(card);
		p.spellGraveCards.add(card);
		gp.playSE(1);	
	}

	// Turn

	public void endTurn() {
		if (isFirstTurn) {
			isFirstTurn = false;
		}

		numberOfCreatureCanPlayInTurn = 1;
		updateAllBoardCardsForPlayer(player);
		inactiveMode = true;
		isOnTurn = false;
		cd.showMsg("Gegner ist am Zug");

		if (isOnline) {
			gp.connection.send(null, null, null, null, null, null, null, null, "playerEndTurn");
		}
	}

	public void startTurn() {
		numberOfCreatureCanPlayInTurn = 1;
		updateAllBoardCardsForPlayer(oponent);
		kartenZiehen(player, 1, true);
		inactiveMode = false;
		isOnTurn = true;
		cd.showMsg("Du bist am Zug");
	}

	public void updateAllBoardCardsForPlayer(Player p) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			CardState card = p.boardCards.get(i);
			setKarteHasAttackedOnTurn(p, card.id, false, true);
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

	public boolean containsCardId(List<CardState> cards, int id) {
		for (CardState card : cards) {
			if (card.id == id) {
				return true;
			}
		} 
		return false;
	}

	public boolean containsSpecificCardId(List<CardState> cards, int id) {
		for (CardState card : cards) {
			if (card.defaultCard.id == id) {
				return true;
			}
		} 
		return false;
	}

	public boolean isPlayCreatureFromHandAllowed(Player p) {
		return numberOfCreatureCanPlayInTurn > 0 && p.boardCards.size() < limitBoardCards;
	}

	private boolean isAngriffBlockiert(Player p, CardState card) {
		return 	(card.art == Art.Tier && (blockAngriffTierePlayer > 0) && p.isPlayer) || (card.art == Art.Tier && (blockAngriffTiereOponent > 0) && !p.isPlayer);
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
		
		return !card.hasAttackOnTurn && tmp && !isFirstTurn && !isAngriffBlockiert(p, card) && !card.statusSet.contains(Status.Blitz) && isOnTurn;
	}

	private boolean isEffektBlockiert(Player p, CardState card) {
		return (
 			p.isPlayer && card.art == Art.Mensch && (blockEffektMenschenPlayer > 0) || 
			!p.isPlayer && card.art == Art.Mensch && (blockEffektMenschenOponent > 0) || 
			p.isPlayer && card.art == Art.Tier && (blockEffektTierePlayer > 0) || 
			!p.isPlayer && card.art == Art.Tier && (blockEffektTiereOponent > 0) || 
			p.isPlayer && card.art == Art.Fabelwesen && (blockEffektFabelwesenPlayer > 0) || 
			!p.isPlayer && card.art == Art.Fabelwesen && (blockEffektFabelwesenOponent > 0) || 
			p.isPlayer && card.art == Art.Nachtgestalt && (blockEffektNachtgestaltenPlayer > 0) || 
			!p.isPlayer && card.art == Art.Nachtgestalt && (blockEffektNachtgestaltenOponent > 0) || 
			blockEffektAll
		);
	}

	public boolean isEffektManualActivatable(Player p, CardState card, int manualTrigger) {
		return !card.defaultCard.isSpell && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && !card.isHide && isOnTurn;
	}

	public boolean isSpellActivatable(Player p, CardState card) {
		return ((card.art == Art.Fluch && card.defaultCard.kosten <= p.fluchCounter) || (card.art == Art.Segen && card.defaultCard.kosten <= p.segenCounter)) && card.isEffektPossible(p) && isOnTurn;
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(p) && card.triggerState == trigger && !isEffektBlockiert(p, card);
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

	public boolean isOffeneKarteOnBoardOfPlayer(Player p) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			if (!p.boardCards.get(i).isHide) {
				return true;
			}
		}
		return false;
	}

	public boolean isState(int state) {
		return currentState == state;
	}
	



	// Hilfsmethoden
	
	public void specificKreaturAusStapelOderHandAufrufen(Player p, int specificId) {
		CardState specificCard = null;

		for (CardState card : p.handCards) {
			if (card.defaultCard.id == specificId) {
				specificCard = card;
				break;
			}
		} 

		if (specificCard != null) {
			kreaturAufrufen(p, specificCard.id, false, true);
			return;
		} 

		for (CardState card : p.stapel) {
			if (card.defaultCard.id == specificId) {
				specificCard = card;
				break;
			}
		} 

		if (specificCard != null) {
			kreaturAufrufenVomStapel(p, specificCard.id, true);
			return;
		} 
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
