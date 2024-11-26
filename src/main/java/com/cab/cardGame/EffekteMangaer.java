package com.cab.cardGame;

import com.cab.card.Card;
import com.cab.card.CardLoader;
import com.cab.effektCards.kreaturen.*;
import com.cab.effektCards.segen.*;
import com.cab.effektCards.fluch.*;

public class EffekteMangaer {
	CardGame cardGame;
	CardLoader cl;
	int ignoreState = -1;
	
	public int triggerManualFromBoard = 0; 
	public int triggerManualFromGrave = 1;
	public int triggerManualFromHand = 2; 

	public int triggerKreaturAufrufen = 10; //wenn Karte  offen auf das Feld gespielt wird
	public int triggerOnBoardOponentKreaturAufgerufen = 11; //wenn Karte auf dem Board und der Gegner eine Kreatur offen aufruft;
	public int triggerOnBoardPlayerKreaturAufgerufen = 12; //wenn Karte auf dem Board und Spieler eine Kreatur offen aufruft;

	public int triggerDirekterAngriff = 20;
	public int triggerOnHandDamageDirekterAngriff = 21; //wenn Karte in der Hand, und Gegner direkt angreift

	//Angriff Phase Eins Setup 
	public int triggerAngriffSetupAngreifer = 30;
	public int triggerAngriffSetupVerteidiger = 31;

	//Angriff Phase Zwei Setup
	public int triggerBeforeKarteAngreift = 40; //bevor karte eine andere Karte angreift
	public int triggerBeforeKarteWirdAngegriffen = 41; //bevor die offene Karte angegriffen wird

	//Angriff Phase Drei Setup
	public int triggerSchadenZugefuegtDurchAngriff = 50; //wenn die Karte angegriffen hat und beide noch auf dem board sind
	public int triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard = 51; //wenn die Karte angegriffen wurde und angreifer noch auf dem Board ist
	public int triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard = 52; //Effekt wird aktiviert, wenn die Karte durch kampf zerstört wird und Angreifer noch auf dem board ist
	public int triggerAfterDoAttack = 53; //Effekt wird aktivert, nachdem Karte angegriffen hat 
	public int triggerAfterDoAttackAngreiferNochAufBoard = 54; //Effekt wird aktivert, nachdem Karte angegriffen hat und sie sich noch auf dem Board befindet (keine selbszerstörung durch verdeckt Karten)
	public int triggerKarteWurdeDurchKampfZerstoert = 55;
	public int triggerKarteHatDurchAngriffKarteZerstoert = 56;
	public int triggerKarteWurdeAngegriffen = 57;


	public int triggerAfterDestroyed = 60; // Karte wurde vom Board auf den Friedhof geschickt
	public int triggerOnZerstoertPlayerKreaturZerstoert = 61;
	public int triggerOnZerstoertOponentKreaturZerstoert = 62;
	public int triggerOnZerstoertKreaturZerstoert = 63;

	public int triggerOnAddKreaturToGrave = 70;
	public int triggerOnStartRunde = 71;

	public int triggerOnWin = 100;
	
	public int triggerPermanent = 190;

	public int triggerTest = 200;

	public EffekteMangaer(CardGame cardGame) {
		this.cardGame = cardGame;
		this.cl = cardGame.gp.cardLoader;
	}
	
	public CardState getCardForId(int id) {
		Card card = cl.getCard(id);
		
		switch (id) {
			//KREATUREN
			case    0: return new Roboto(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case    1: return new Hexe(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case    2: return new Sensenmann(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case    3: return new Vampir(card, cardGame, cardGame.boardState, triggerAfterDoAttackAngreiferNochAufBoard, ignoreState);
			case    4: return new Werwolf(card, cardGame, cardGame.boardState, triggerAfterDoAttackAngreiferNochAufBoard, ignoreState);
			case    5: return new Harpyie(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case    6: return new Daemon(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case    7: return new Zombie(card, cardGame, cardGame.boardState, triggerManualFromGrave, ignoreState);
			case    8: return new Cerberus(card, cardGame, cardGame.boardState, triggerOnZerstoertKreaturZerstoert, ignoreState);
			case    9: return new Gefallener(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   10: return new Magier(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   12: return new Gorgone(card, cardGame, cardGame.boardState, triggerKarteWurdeAngegriffen, ignoreState);
			case   13: return new Kappa(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   14: return new Aswang(card, cardGame, cardGame.boardState, triggerAfterDoAttackAngreiferNochAufBoard, ignoreState);
			case   15: return new Gespenst(card, cardGame, cardGame.boardState, triggerBeforeKarteWirdAngegriffen, ignoreState);
			case   16: return new Ghul(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, ignoreState);
			case   17: return new Finsternis(card, cardGame, ignoreState, triggerPermanent, ignoreState);
			case   18: return new Hydra(card, cardGame, cardGame.boardState, triggerBeforeKarteWirdAngegriffen, ignoreState);
			case   19: return new VodooPriester(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   20: return new HoellenReiter(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   21: return new Wolf(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   22: return new Wolf(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   23: return new Schlange(card, cardGame, cardGame.boardState, triggerSchadenZugefuegtDurchAngriff, ignoreState);
			case   26: return new Biene(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case   27: return new Gottesanbeterin(card, cardGame, cardGame.boardState, triggerAfterDoAttackAngreiferNochAufBoard, ignoreState);
			case   28: return new Chamaeleon(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   30: return new Igel(card, cardGame, cardGame.boardState, triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, ignoreState);
			case   31: return new Vogel(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case   32: return new Kroete(card, cardGame, cardGame.graveOponentState, triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, ignoreState);
			case   33: return new Schmetterling(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   34: return new Gorilla(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, ignoreState);
			case   37: return new Pfau(card, cardGame, ignoreState, triggerPermanent, ignoreState); 
			case   39: return new Hirsch(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState); 
			case   40: return new Qualle(card, cardGame, cardGame.boardState, triggerSchadenZugefuegtDurchAngriff, ignoreState); 
			case   42: return new Schnecke(card, cardGame, cardGame.boardState, triggerOnBoardOponentKreaturAufgerufen, ignoreState);
			case   43: return new Fisch(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   45: return new Oktopus(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case   49: return new Loewe(card, cardGame, cardGame.boardState, triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, ignoreState);
			case   47: return new Verfluchter(card, cardGame, cardGame.boardState, triggerAfterDestroyed, ignoreState);
			case   50: return new Banshee(card, cardGame, cardGame.boardState, triggerManualFromGrave, ignoreState);
			case   51: return new Wendigo(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case   52: return new Todesfee(card, cardGame, cardGame.boardState, triggerOnZerstoertKreaturZerstoert, ignoreState);
			case   53: return new Mimikrie(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   55: return new Taube(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case   56: return new Ratte(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionCardListState);
			case   57: return new Lamia(card, cardGame, cardGame.boardState, triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, ignoreState);
			case   59: return new Nix(card, cardGame, ignoreState, triggerPermanent, ignoreState);
			case   68: return new Minotaurus(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, ignoreState);
			case   60: return new Drache(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   61: return new Einhorn(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   62: return new Fee(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   63: return new Djinn(card, cardGame, cardGame.handCardState, triggerKreaturAufrufen, cardGame.effektSelectOwnGraveState);
			case   64: return new Meerjungfrau(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   65: return new Phoenix(card, cardGame, cardGame.handCardState, triggerAfterDestroyed, ignoreState);
			case   66: return new Kobold(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case   67: return new Zyklop(card, cardGame, cardGame.boardState, triggerAngriffSetupAngreifer, ignoreState);
			case   70: return new Zentaur(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   72: return new Irrlicht(card, cardGame, cardGame.graveOponentState, triggerAfterDestroyed, cardGame.effektSelectOponentBoardState);
			case   73: return new Zwerg(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   74: return new Elf(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   76: return new Feuerteufel(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			
			case   78: return new Sphynx(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   79: return new Armor(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   80: return new Pegasus(card, cardGame, cardGame.boardState, triggerAfterDoAttack, ignoreState);
			case   81: return new Kraken(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case   82: return new Klabautermann(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case   83: return new Sylphe(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   84: return new Waldgeist(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   85: return new Golem(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   86: return new Tengu(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   87: return new Kirin(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   88: return new Kitzune(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case   90: return new Arzt(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOwnBoardState);
			case   91: return new Jaeger(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   92: return new Pirat(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   93: return new Ritter(card, cardGame, cardGame.boardState, triggerBeforeKarteAngreift, ignoreState);
			case   95: return new Astrologe(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   96: return new Waechter(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOwnBoardState);
			case   97: return new Daemonenjaeger(card, cardGame, ignoreState, triggerPermanent, ignoreState);
			case   98: return new Buergermeister(card, cardGame, cardGame.boardState, triggerAngriffSetupVerteidiger, cardGame.effektSelectOwnBoardState);
			case   99: return new Bauer(card, cardGame, cardGame.handCardState, triggerOnStartRunde, ignoreState);
			case  100: return new Koenig(card, cardGame, cardGame.boardState, triggerPermanent, ignoreState);
			case  101: return new Haenker(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case  102: return new Hofnarr(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionState);
			case  103: return new Hahn(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case  104: return new Bandit(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case  105: return new Vagabund(card, cardGame, cardGame.handCardState, triggerManualFromBoard, ignoreState);
			case  106: return new Glaeubiger(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case  107: return new Herrscherin(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case  108: return new Schmied(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionCardListState);
			case  109: return new Witwe(card, cardGame, cardGame.boardState, triggerOnAddKreaturToGrave, ignoreState);
			case  110: return new Scharmanin(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, cardGame.selectOptionCardListState);
			case  112: return new Wissenschaftler(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionCardListState);
			case  113: return new Schwein(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case  114: return new Donteur(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case  115: return new Gefangener(card, cardGame, cardGame.boardState, triggerAfterDoAttack, ignoreState);
			case  116: return new Bettler(card, cardGame, cardGame.boardState, triggerOnWin, ignoreState);
			case  117: return new Fischer(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case  118: return new Katze(card, cardGame, cardGame.graveState, triggerOnHandDamageDirekterAngriff, ignoreState);
			case  119: return new Prinzessin(card, cardGame, cardGame.handCardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  120: return new Philiosoph(card, cardGame, cardGame.handCardState, triggerOnStartRunde, cardGame.selectOptionCardListState);
			case  121: return new Mumie(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case  122: return new Koyote(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, cardGame.selectOptionCardListState);
			case  123: return new Rabe(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case  124: return new Verdorbene(card, cardGame, cardGame.graveState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case  125: return new Fledermaus(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case  127: return new Wahrsagerin(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionState);
			case  128: return new Papagei(card, cardGame, cardGame.boardState, triggerKarteWurdeDurchKampfZerstoert, ignoreState);
			case  129: return new Verstossener(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case  130: return new HeiligerRitter(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, ignoreState);
			case  131: return new Pinguin(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.selectOptionCardListState);
			case  132: return new Totenbeschwoerer(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.selectOptionCardListState);
			case  133: return new Spirituelle(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.selectOptionCardListState);
			case  134: return new Himmliche(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);

			//SEGEN
			case  300: return new Schwert(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  301: return new Beschwoerung(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case  302: return new Amulett(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  303: return new Wandel(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  304: return new Fluegel(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  305: return new Schild(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  306: return new Sturmangriff(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  307: return new Gnade(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnGraveState);
			case  308: return new Schwarm(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  309: return new Gnade(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnGraveState);
			case  310: return new Quelle(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case  311: return new SchildUndSchwert(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  312: return new Kessel(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case  313: return new Fleisch(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case  314: return new Hetze(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);

			case  316: return new Aufstieg(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  317: return new RufDerDunkelheit(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  318: return new HimmlicherRuf(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  319: return new Aufrufung(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  320: return new SegenDerSterblichen(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  321: return new SegenDerBestien(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  322: return new SegenDerFabel(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  323: return new SegenDerDunklen(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  324: return new Verstaerkung(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  325: return new Vollmond(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  326: return new Regenbogen(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);
			case  327: return new Sonnenfinsternis(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.selectOptionCardListState);

			//FLUCH
			case  500: return new Flasche(card, cardGame, cardGame.graveOponentState, triggerManualFromHand, cardGame.effektSelectOponentBoardState);
			case  501: return new Fluch(card, cardGame, cardGame.graveOponentState, triggerManualFromHand, cardGame.effektSelectOponentBoardState);
			case  502: return new Falle(card, cardGame, cardGame.graveOponentState, triggerManualFromHand, cardGame.effektSelectOponentBoardState); 
			case  503: return new Sturm(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState); 
			case  504: return new Ueberstrahlung(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState); 
			case  505: return new Hypnose(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOponentBoardState); 
			case  506: return new Gewitter(card, cardGame, cardGame.handCardState, triggerManualFromHand, cardGame.effektSelectOponentBoardState);
			case  507: return new Feuer(card, cardGame, cardGame.handCardState, triggerManualFromHand, cardGame.effektSelectOponentBoardState); 
			case  508: return new Brand(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState); 
			case  509: return new Opfergabe(card, cardGame, cardGame.handCardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState); 
			case  510: return new VerlorenesLand(card, cardGame, cardGame.handCardState, triggerManualFromHand, cardGame.effektSelectOponentBoardState); 
			case  511: return new Katastrophe(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case  512: return new Hetzjagd(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState); 
			case  513: return new Vogelscheuche(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState); 

			default:   return new CardState(card, cardGame);
		}
	}
}
