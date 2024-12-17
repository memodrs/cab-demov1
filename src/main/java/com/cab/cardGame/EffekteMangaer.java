package com.cab.cardGame;

import com.cab.card.Card;
import com.cab.effektCards.kreaturen.*;
import com.cab.effektCards.segen.*;
import com.cab.effektCards.fluch.*;

public class EffekteMangaer {
	CardGame cardGame;
	
	public int triggerManualFromBoard = 0;
	public int triggerManualFromGrave = 1;
	public int triggerManualFromHand  = 2;

	public int triggerKreaturAufrufen                 = 10;  //wenn Karte  offen auf das Feld gespielt wird
	public int triggerOnBoardOponentKreaturAufgerufen = 11;  //wenn Karte auf dem Board und der Gegner eine Kreatur offen aufruft;
	public int triggerOnBoardPlayerKreaturAufgerufen  = 12;  //wenn Karte auf dem Board und Spieler eine Kreatur offen aufruft;

	public int triggerDirekterAngriff                   = 20;
	public int triggerBeforeDirekterAngriff             = 21;
	public int triggerOnHandBeforeDamageDirekterAngriff = 22;  //Wenn Karte in der Hand und Gegner direkter angriff, wird davor aktiviert
	public int triggerOnHandDamageDirekterAngriff       = 23;  //wenn Karte in der Hand, und Gegner direkt angreift, wird danach aktiviert

	  //Angriff Phase Eins Setup 
	public int triggerAngriffSetupAngreifer   = 30;
	public int triggerAngriffSetupVerteidiger = 31;

	                                                    //Angriff Phase Zwei Setup
	public int triggerBeforeKarteAngreift = 40;         //bevor karte eine andere Karte angreift
	public int triggerBeforeKarteWirdAngegriffen = 41;  //bevor die offene Karte angegriffen wird

	                                                                                     //Angriff Phase Drei Setup
	public int triggerSchadenZugefuegtDurchAngriff = 50;                                 //wenn die Karte angegriffen hat und beide noch auf dem board sind
	public int triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard = 51;          //wenn die Karte angegriffen wurde und angreifer noch auf dem Board ist
	public int triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard = 52;  //Effekt wird aktiviert, wenn die Karte durch kampf zerstört wird und Angreifer noch auf dem board ist
	public int triggerAfterDoAttack = 53;                                                //Effekt wird aktivert, nachdem Karte angegriffen hat 
	public int triggerAfterDoAttackAngreiferNochAufBoard = 54;                           //Effekt wird aktivert, nachdem Karte angegriffen hat und sie sich noch auf dem Board befindet (keine selbszerstörung durch verdeckt Karten)
	public int triggerKarteWurdeDurchKampfZerstoert      = 55;
	public int triggerKarteHatDurchAngriffKarteZerstoert = 56;
	public int triggerKarteWurdeAngegriffen              = 57;


	public int triggerAfterDestroyed                     = 60;  // Karte wurde vom Board auf den Friedhof geschickt
	public int triggerOnZerstoertPlayerKreaturZerstoert  = 61;
	public int triggerOnZerstoertOponentKreaturZerstoert = 62;
	public int triggerOnZerstoertKreaturZerstoert        = 63;

	public int triggerOnAddKreaturToGrave = 70;
	public int triggerOnStartRunde        = 71;

	public int triggerOnWin = 100;
	
	public int triggerPermanent = 190;

	public int triggerTest = 200;

	public EffekteMangaer(CardGame cardGame) {
		this.cardGame = cardGame;
	}
	
	public CardState getCardForId(int id) {
		Card card = cardGame.gp.cardLoader.getCard(id);
		
		switch (id) {
			  //KREATUREN
			case 0  : return new Roboto(          card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 1  : return new Hexe(            card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 2  : return new Sensenmann(      card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 3  : return new Vampir(          card, cardGame, State.boardState,        triggerAfterDoAttack,                                               State.ignoreState                  );
			case 4  : return new Werwolf(         card, cardGame, State.boardState,        triggerAfterDoAttack,                                               State.ignoreState                  );
			case 5  : return new Harpyie(         card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 6  : return new Daemon(          card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 7  : return new Zombie(          card, cardGame, State.boardState,        triggerManualFromGrave,                                             State.ignoreState                  );
			case 8  : return new Cerberus(        card, cardGame, State.boardState,        triggerOnZerstoertKreaturZerstoert,                                 State.ignoreState                  );
			case 9  : return new Gefallener(      card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 10 : return new Magier(          card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOponentBoardState);
			case 12 : return new Gorgone(         card, cardGame, State.boardState,        triggerKarteWurdeAngegriffen,                                       State.ignoreState                  );
			case 13 : return new Kappa(           card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 14 : return new Aswang(          card, cardGame, State.boardState,        triggerAfterDoAttack,                                               State.ignoreState                  );
			case 15 : return new Gespenst(        card, cardGame, State.boardState,        triggerBeforeKarteWirdAngegriffen,                                  State.ignoreState                  );
			case 16 : return new Ghul(            card, cardGame, State.boardState,        triggerKarteHatDurchAngriffKarteZerstoert,                          State.ignoreState                  );
			case 17 : return new Finsternis(      card, cardGame, State.ignoreState,       triggerPermanent,                                                   State.ignoreState                  );
			case 18 : return new Hydra(           card, cardGame, State.boardState,        triggerBeforeKarteWirdAngegriffen,                                  State.ignoreState                  );
			case 19 : return new VodooPriester(   card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOponentBoardState);
			case 20 : return new HoellenReiter(   card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 21 : return new Wolf(            card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 22 : return new Wolf(            card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 23 : return new Schlange(        card, cardGame, State.boardState,        triggerSchadenZugefuegtDurchAngriff,                                State.ignoreState                  );
			case 26 : return new Biene(           card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 27 : return new Gottesanbeterin( card, cardGame, State.boardState,        triggerAfterDoAttackAngreiferNochAufBoard,                          State.ignoreState                  );
			case 28 : return new Chamaeleon(      card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 30 : return new Igel(            card, cardGame, State.boardState,        triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard,         State.ignoreState                  );
			case 31 : return new Vogel(           card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 32 : return new Kroete(          card, cardGame, State.graveOponentState, triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, State.ignoreState                  );
			case 33 : return new Schmetterling(   card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 34 : return new Gorilla(         card, cardGame, State.boardState,        triggerKarteHatDurchAngriffKarteZerstoert,                          State.ignoreState                  );
			case 37 : return new Pfau(            card, cardGame, State.ignoreState,       triggerPermanent,                                                   State.ignoreState                  );
			case 39 : return new Hirsch(          card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 40 : return new Qualle(          card, cardGame, State.boardState,        triggerSchadenZugefuegtDurchAngriff,                                State.ignoreState                  );
			case 42 : return new Schnecke(        card, cardGame, State.boardState,        triggerOnBoardOponentKreaturAufgerufen,                             State.ignoreState                  );
			case 43 : return new Fisch(           card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 45 : return new Oktopus(         card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 49 : return new Loewe(           card, cardGame, State.boardState,        triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard,         State.ignoreState                  );
			case 47 : return new Verfluchter(     card, cardGame, State.boardState,        triggerAfterDestroyed,                                              State.ignoreState                  );
			case 50 : return new Banshee(         card, cardGame, State.boardState,        triggerManualFromGrave,                                             State.ignoreState                  );
			case 51 : return new Wendigo(         card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 52 : return new Todesfee(        card, cardGame, State.boardState,        triggerOnZerstoertKreaturZerstoert,                                 State.ignoreState                  );
			case 53 : return new Mimikrie(        card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 55 : return new Taube(           card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 56 : return new Ratte(           card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionCardListState    );
			case 57 : return new Lamia(           card, cardGame, State.boardState,        triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard,         State.ignoreState                  );
			case 59 : return new Nix(             card, cardGame, State.ignoreState,       triggerPermanent,                                                   State.ignoreState                  );
			case 68 : return new Minotaurus(      card, cardGame, State.boardState,        triggerKarteHatDurchAngriffKarteZerstoert,                          State.ignoreState                  );
			case 60 : return new Drache(          card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOponentBoardState);
			case 61 : return new Einhorn(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 62 : return new Fee(             card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 63 : return new Djinn(           card, cardGame, State.handCardState,     triggerKreaturAufrufen,                                             State.effektSelectOwnGraveState    );
			case 64 : return new Meerjungfrau(    card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 65 : return new Phoenix(         card, cardGame, State.handCardState,     triggerAfterDestroyed,                                              State.ignoreState                  );
			case 66 : return new Kobold(          card, cardGame, State.handCardState,     triggerManualFromHand,                                              State.ignoreState                  );
			case 67 : return new Zyklop(          card, cardGame, State.boardState,        triggerAngriffSetupAngreifer,                                       State.ignoreState                  );
			case 70 : return new Zentaur(         card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 72 : return new Irrlicht(        card, cardGame, State.graveOponentState, triggerAfterDestroyed,                                              State.effektSelectOponentBoardState);
			case 73 : return new Zwerg(           card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 74 : return new Elf(             card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 76 : return new Feuerteufel(     card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 77 : return new Yeti(            card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 78 : return new Sphynx(          card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 79 : return new Armor(           card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOponentBoardState);
			case 80 : return new Pegasus(         card, cardGame, State.boardState,        triggerAfterDoAttack,                                               State.ignoreState                  );
			case 81 : return new Kraken(          card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 82 : return new Klabautermann(   card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 83 : return new Sylphe(          card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 84 : return new Waldgeist(       card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 85 : return new Golem(           card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 86 : return new Tengu(           card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 87 : return new Kirin(           card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOponentBoardState);
			case 88 : return new Kitzune(         card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 89 : return new Huma(            card, cardGame, State.boardState,        triggerManualFromHand,                                              State.ignoreState                  );
			case 90 : return new Arzt(            card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOwnBoardState    );
			case 91 : return new Jaeger(          card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 92 : return new Pirat(           card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 93 : return new Ritter(          card, cardGame, State.boardState,        triggerBeforeKarteAngreift,                                         State.ignoreState                  );
			case 95 : return new Astrologe(       card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.ignoreState                  );
			case 96 : return new Waechter(        card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.effektSelectOwnBoardState    );
			case 97 : return new Daemonenjaeger(  card, cardGame, State.ignoreState,       triggerPermanent,                                                   State.ignoreState                  );
			case 98 : return new Buergermeister(  card, cardGame, State.boardState,        triggerAngriffSetupVerteidiger,                                     State.effektSelectOwnBoardState    );
			case 99 : return new Bauer(           card, cardGame, State.handCardState,     triggerOnStartRunde,                                                State.ignoreState                  );
			case 100: return new Koenig(          card, cardGame, State.boardState,        triggerPermanent,                                                   State.ignoreState                  );
			case 101: return new Haenker(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 102: return new Hofnarr(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionState            );
			case 103: return new Hahn(            card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 104: return new Bandit(          card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 105: return new Vagabund(        card, cardGame, State.handCardState,     triggerManualFromBoard,                                             State.ignoreState                  );
			case 106: return new Glaeubiger(      card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 107: return new Herrscherin(     card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 108: return new Schmied(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionCardListState    );
			case 109: return new Witwe(           card, cardGame, State.boardState,        triggerOnAddKreaturToGrave,                                         State.ignoreState                  );
			case 110: return new Scharmanin(      card, cardGame, State.boardState,        triggerKarteWurdeDurchKampfZerstoert,                               State.selectOptionCardListState    );
			case 112: return new Wissenschaftler( card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionCardListState    );
			case 113: return new Schwein(         card, cardGame, State.handCardState,     triggerManualFromHand,                                              State.ignoreState                  );
			case 114: return new Donteur(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 115: return new Gefangener(      card, cardGame, State.boardState,        triggerAfterDoAttack,                                               State.ignoreState                  );
			case 116: return new Bettler(         card, cardGame, State.boardState,        triggerOnWin,                                                       State.ignoreState                  );
			case 117: return new Fischer(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.effektSelectOponentBoardState);
			case 118: return new Katze(           card, cardGame, State.graveState,        triggerOnHandBeforeDamageDirekterAngriff,                           State.ignoreState                  );
			case 119: return new Prinzessin(      card, cardGame, State.handCardState,     triggerManualFromHand,                                              State.selectOptionCardListState    );
			case 120: return new Philiosoph(      card, cardGame, State.handCardState,     triggerOnStartRunde,                                                State.selectOptionCardListState    );
			case 121: return new Mumie(           card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 122: return new Koyote(          card, cardGame, State.boardState,        triggerKarteHatDurchAngriffKarteZerstoert,                          State.selectOptionCardListState    );
			case 123: return new Rabe(            card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 124: return new Verdorbene(      card, cardGame, State.graveState,        triggerKarteWurdeDurchKampfZerstoert,                               State.ignoreState                  );
			case 125: return new Fledermaus(      card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 127: return new Wahrsagerin(     card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionState            );
			case 128: return new Papagei(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 129: return new Verstossener(    card, cardGame, State.handCardState,     triggerManualFromHand,                                              State.ignoreState                  );
			case 130: return new HeiligerRitter(  card, cardGame, State.boardState,        triggerKarteHatDurchAngriffKarteZerstoert,                          State.ignoreState                  );
			case 131: return new Pinguin(         card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.selectOptionCardListState    );
			case 132: return new Totenbeschwoerer(card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.selectOptionCardListState    );
			case 133: return new Spirituelle(     card, cardGame, State.boardState,        triggerManualFromBoard,                                             State.selectOptionCardListState    );
			case 134: return new Himmliche(       card, cardGame, State.boardState,        triggerKreaturAufrufen,                                             State.ignoreState                  );
			case 135: return new Puppe(           card, cardGame, State.graveState,        triggerOnHandDamageDirekterAngriff,                                 State.ignoreState                  );

			  //SEGEN
			case 300: return new Schwert(            card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 301: return new Beschwoerung(       card, cardGame, State.boardState,	  triggerManualFromHand, State.ignoreState);
			case 302: return new Amulett(            card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 303: return new Wandel(             card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 304: return new Fluegel(            card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 305: return new Schild(             card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 306: return new Sturmangriff(       card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 307: return new Gnade(              card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnGraveState);
			case 308: return new Schwarm(            card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 309: return new Gnade(              card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnGraveState);
			case 310: return new Quelle(             card, cardGame, State.boardState,	  triggerManualFromHand, State.ignoreState);
			case 311: return new SchildUndSchwert(   card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 312: return new Kessel(             card, cardGame, State.handCardState, triggerManualFromHand, State.ignoreState);
			case 313: return new Fleisch(            card, cardGame, State.boardState, 	  triggerManualFromHand, State.ignoreState);
			case 314: return new Hetze(              card, cardGame, State.boardState,	  triggerManualFromHand, State.ignoreState);
			
			case 316: return new Aufstieg(           card, cardGame, State.boardState,	  triggerManualFromHand, State.effektSelectOwnBoardState);
			case 317: return new RufDerDunkelheit(   card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 318: return new HimmlicherRuf(      card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 319: return new Aufrufung(          card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 320: return new SegenDerSterblichen(card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 321: return new SegenDerBestien(    card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 322: return new SegenDerFabel(      card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 323: return new SegenDerDunklen(    card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 324: return new Verstaerkung(       card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 325: return new Vollmond(           card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 326: return new Regenbogen(         card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);
			case 327: return new Sonnenfinsternis(   card, cardGame, State.boardState,	  triggerManualFromHand, State.selectOptionCardListState);

			  //FLUCH
			case 500: return new Flasche(       card, cardGame, State.graveOponentState, triggerManualFromHand, State.effektSelectOponentBoardState);
			case 501: return new Fluch(         card, cardGame, State.graveOponentState, triggerManualFromHand, State.effektSelectOponentBoardState);
			case 502: return new Falle(         card, cardGame, State.graveOponentState, triggerManualFromHand, State.effektSelectOponentBoardState);
			case 503: return new Sturm(         card, cardGame, State.handCardState, 	 triggerManualFromHand, State.ignoreState);
			case 504: return new Ueberstrahlung(card, cardGame, State.handCardState,     triggerManualFromHand, State.ignoreState);
			case 505: return new Hypnose(       card, cardGame, State.boardState,	 	 triggerManualFromHand, State.effektSelectOponentBoardState);
			case 506: return new Gewitter(      card, cardGame, State.handCardState, 	 triggerManualFromHand, State.effektSelectOponentBoardState);
			case 507: return new Feuer(         card, cardGame, State.handCardState, 	 triggerManualFromHand, State.effektSelectOponentBoardState);
			case 508: return new Brand(         card, cardGame, State.handCardState, 	 triggerManualFromHand, State.ignoreState);
			case 509: return new Opfergabe(     card, cardGame, State.handCardState, 	 triggerManualFromHand, State.effektSelectOwnBoardState);
			case 510: return new VerlorenesLand(card, cardGame, State.handCardState, 	 triggerManualFromHand, State.effektSelectOponentBoardState);
			case 511: return new Katastrophe(   card, cardGame, State.handCardState, 	 triggerManualFromHand, State.ignoreState);
			case 512: return new Hetzjagd(      card, cardGame, State.handCardState, 	 triggerManualFromHand, State.ignoreState);
			case 513: return new Vogelscheuche( card, cardGame, State.handCardState, 	 triggerManualFromHand, State.ignoreState);
			case 514: return new Alchemie(      card, cardGame, State.boardState, 		 triggerManualFromHand, State.selectOptionState);

			default: return new CardState(card, cardGame);
		}
	}
}
