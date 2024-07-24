package com.cab.cardGame;

import com.cab.card.Card;
import com.cab.card.CardLoader;
import com.cab.effektCards.*;;


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
	public int triggerAfterDoAttack = 53; //Effekt wird aktivert, nachdem Karte angegriffen hat und sie sich noch auf dem Board befindet (keine selbszerstörung durch verdeckt Karten)
	public int triggerKarteWurdeDurchKampfZerstoert = 54;
	public int triggerKarteHatDurchAngriffKarteZerstoert = 55;

	public int triggerAfterDestroyed = 60; // Karte wurde vom Board auf den Friedhof geschickt
	public int triggerOnZerstoertPlayerKreaturZerstoert = 61;
	public int triggerOnZerstoertOponentKreaturZerstoert = 62;

	public int triggerLebenGeheilt = 70;
	public int triggerAngriffGesenkt = 80;
	public int triggerAngriffErhoeht = 90;
	public int triggerBurnStatusErhalten = 100;
	public int triggerPoisenStatusErhalten = 110;
	public int triggerKarteVomFriedhofInDieHand = 120;
	public int triggerKarteVomBoardInDieHand = 130;
	public int triggerKarteVomFriedhofAufrufen = 140;

	public int triggerTest = 200;



	

	public EffekteMangaer(CardGame cardGame) {
		this.cardGame = cardGame;
		this.cl = cardGame.gp.cardLoader;
	}
	
	public CardState getCardForId(int id) {
		//Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState
		Card card = cl.getCard(id);
		
		switch (id) {
			case    0: return new Roboto(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case    1: return new Arzt(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOwnBoardState);
			case    2: return new Wolf(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case    4: return new Drache(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case    5: return new Drache(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case    6: return new Einhorn(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case    7: return new Fee(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case 	8: return new Flaschengeist(card, cardGame, cardGame.handCardState, triggerKreaturAufrufen, cardGame.effektSelectOwnGraveState);
			case 	9: return new Gespenst(card, cardGame, cardGame.boardState, triggerBeforeKarteWirdAngegriffen, ignoreState);	
			case   10: return new Hexe(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   11: return new Jaeger(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   12: return new Meerjungfrau(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   13: return new Phoenix(card, cardGame, cardGame.handCardState, triggerAfterDestroyed, ignoreState);
			case   14: return new Pirat(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   15: return new Ritter(card, cardGame, cardGame.boardState, triggerBeforeKarteAngreift, ignoreState);
			case   16: return new Schlange(card, cardGame, cardGame.boardState, triggerSchadenZugefuegtDurchAngriff, ignoreState);
			case   17: return new Sensenmann(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   19: return new Vampir(card, cardGame, cardGame.boardState, triggerAfterDoAttack, ignoreState);
			case   21: return new Kobold(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);
			case   23: return new Zyklop(card, cardGame, cardGame.boardState, triggerAngriffSetupAngreifer, ignoreState);
			case   26: return new Astrologe(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   29: return new Gottesanbeterin(card, cardGame, cardGame.boardState, triggerAfterDoAttack, ignoreState);
			case   30: return new Daemon(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   31: return new Zombie(card, cardGame, cardGame.boardState, triggerManualFromGrave, ignoreState);
			case   33: return new Chamaeleon(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   36: return new Igel(card, cardGame, cardGame.boardState, triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, ignoreState);
			case   37: return new Fisch(card, cardGame, cardGame.boardState, triggerManualFromBoard, ignoreState);
			case   38: return new Cerberus(card, cardGame, cardGame.boardState, triggerOnZerstoertOponentKreaturZerstoert, ignoreState);
			case   39: return new Himmlicher(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   40: return new Waechter(card, cardGame, cardGame.boardState, triggerManualFromBoard, cardGame.effektSelectOwnBoardState);
			case   41: return new Magier(card, cardGame, cardGame.handCardState, triggerManualFromBoard, cardGame.effektSelectOponentBoardState);
			case   44: return new Vogel(card, cardGame, cardGame.boardState, triggerManualFromHand, ignoreState);
			case   45: return new Daemonenjaeger(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   46: return new Buergermeister(card, cardGame, cardGame.boardState, triggerAngriffSetupVerteidiger, cardGame.effektSelectOwnBoardState);
			case   47: return new Loewe(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   49: return new Kroete(card, cardGame, cardGame.graveOponentState, triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, ignoreState);
			case   52: return new Irrlicht(card, cardGame, cardGame.graveOponentState, triggerAfterDestroyed, cardGame.effektSelectOponentBoardState);
			case   53: return new Gorilla(card, cardGame, cardGame.boardState, triggerKarteHatDurchAngriffKarteZerstoert, ignoreState);
			case   61: return new Koenig(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   62: return new Haenker(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, cardGame.effektSelectOponentBoardState);
			case   67: return new Glaeubiger(card, cardGame, cardGame.boardState, triggerKreaturAufrufen, ignoreState);
			case   68: return new Pfau(card, cardGame, ignoreState, ignoreState, ignoreState); //Kein Effekt der getriggert werden muss

			case  200: return new S_Beschwoerung(card, cardGame, cardGame.handCardState, triggerManualFromHand, ignoreState);	
			case  201: return new S_Schwert(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  202: return new S_Lebensenergie(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  203: return new S_Wandler(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  206: return new S_Schild(card, cardGame, cardGame.boardState, triggerManualFromHand, cardGame.effektSelectOwnBoardState);
			case  401: return new F_Flasche(card, cardGame, cardGame.graveOponentState, triggerManualFromHand, cardGame.effektSelectOponentBoardState);

			default:   return new CardState(card, cardGame);
		}
	}
}
