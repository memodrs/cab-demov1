package com.cab.cardGame;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.effektCards.fluch.*;
import com.cab.cardGame.effektCards.kreaturen.*;
import com.cab.cardGame.effektCards.segen.*;
import com.cab.cardGame.model.CardState;

public class EffektManager {
	CardGame cardGame;
	public EffektManager(CardGame cardGame) {
		this.cardGame = cardGame;
	}
	
	public CardState getCardForId(int id) {
		Card card = cardGame.gp.cardLoader.getCard(id);
		
		switch (id) {
			//KREATUREN
			case Ids.ROBOTO:           return new Roboto(card);
			case Ids.HEXE:             return new Hexe(card);
			case Ids.SENSENMANN:       return new Sensenmann(card);
			case Ids.VAMPIR:           return new Vampir(card);
			case Ids.WERWOLF:          return new Werwolf(card);
			case Ids.HARPYIE:          return new Harpyie(card);
			case Ids.DAEMON:           return new Daemon(card);
			case Ids.ZOMBIE:           return new Zombie(card);
			case Ids.CERBERUS:         return new Cerberus(card);
			case Ids.GEFALLENER:       return new Gefallener(card);
			case Ids.MAGIER:           return new Magier(card);
			case Ids.GORGONE:          return new Gorgone(card);
			case Ids.KAPPA:            return new Kappa(card);
			case Ids.ASWANG:           return new Aswang(card);
			case Ids.GESPENST:         return new Gespenst(card);
			case Ids.GHUL:             return new Ghul(card);
			case Ids.FINSTERNIS:       return new Finsternis(card);
			case Ids.HYDRA:            return new Hydra(card);
			case Ids.VODOOPRIESTER:    return new VodooPriester(card);
			case Ids.HOELLENREITER:    return new HoellenReiter(card);
			case Ids.WOLF_1:           return new Wolf(card);
			case Ids.WOLF_2:           return new Wolf(card);
			case Ids.SCHLANGE:         return new Schlange(card);
			case Ids.BIENE:            return new Biene(card);
			case Ids.GOTTESANBETERIN:  return new Gottesanbeterin(card);
			case Ids.CHAMAELEON:       return new Chamaeleon(card);
			case Ids.IGEL:             return new Igel(card);
			case Ids.VOGEL:            return new Vogel(card);
			case Ids.KROETE:           return new Kroete(card);
			case Ids.SCHMETTERLING:    return new Schmetterling(card);
			case Ids.GORILLA:          return new Gorilla(card);
			case Ids.PFAU:             return new Pfau(card);
			case Ids.HIRSCH:           return new Hirsch(card);
			case Ids.QUALLE:           return new Qualle(card);
			case Ids.SCHNECKE:         return new Schnecke(card);
			case Ids.FISCH:            return new Fisch(card);
			case Ids.OKTOPUS:          return new Oktopus(card);
			case Ids.LOEWE:            return new Loewe(card);
			case Ids.VERFLUCHTER:      return new Verfluchter(card);
			case Ids.SUMPFGEIST:  	   return new Sumpfgeist(card);
			case Ids.BANSHEE:          return new Banshee(card);
			case Ids.WENDIGO:          return new Wendigo(card);
			case Ids.TODESFEE:         return new Todesfee(card);
			case Ids.MIMIKRIE:         return new Mimikrie(card);
			case Ids.TAUBE:            return new Taube(card);
			case Ids.RATTE:            return new Ratte(card);
			case Ids.LAMIA:            return new Lamia(card);
			case Ids.NIX:              return new Nix(card);
			case Ids.MINOTAURUS:       return new Minotaurus(card);
			case Ids.DRACHE:           return new Drache(card);
			case Ids.EINHORN:          return new Einhorn(card);
			case Ids.FEE:              return new Fee(card);
			case Ids.DJINN:            return new Djinn(card);
			case Ids.MEERJUNGFRAU:     return new Meerjungfrau(card);
			case Ids.PHOENIX:          return new Phoenix(card);
			case Ids.KOBOLD:           return new Kobold(card);
			case Ids.ZYKLOP:           return new Zyklop(card);
			case Ids.ZENTAUR:          return new Zentaur(card);
			case Ids.IRRLICHT:         return new Irrlicht(card);
			case Ids.ZWERG:            return new Zwerg(card);
			case Ids.ELF:              return new Elf(card);
			case Ids.FEUERTEUFEL:      return new Feuerteufel(card);
			case Ids.YETI:             return new Yeti(card);
			case Ids.SPHYNX:           return new Sphynx(card);
			case Ids.ARMOR:            return new Armor(card);
			case Ids.PEGASUS:          return new Pegasus(card);
			case Ids.KRAKEN:           return new Kraken(card);
			case Ids.KLABAUTERMANN:    return new Klabautermann(card);
			case Ids.SYLPHE:           return new Sylphe(card);
			case Ids.WALDGEIST:        return new Waldgeist(card);
			case Ids.GOLEM:            return new Golem(card);
			case Ids.TENGU:            return new Tengu(card);
			case Ids.KIRIN:            return new Kirin(card);
			case Ids.KITZUNE:          return new Kitzune(card);
			case Ids.HUMA:             return new Huma(card);
			case Ids.ARZT:             return new Arzt(card);
			case Ids.JAEGER:           return new Jaeger(card);
			case Ids.PIRAT:            return new Pirat(card);
			case Ids.RITTER:           return new Ritter(card);
			case Ids.ASTROLOGE:        return new Astrologe(card);
			case Ids.WAECHTER:         return new Waechter(card);
			case Ids.DAEMONENJAEGER:   return new Daemonenjaeger(card);
			case Ids.BUERGERMEISTER:   return new Buergermeister(card);
			case Ids.BAUER:            return new Bauer(card);
			case Ids.KOENIG:           return new Koenig(card);
			case Ids.HAENKER:          return new Haenker(card);
			case Ids.HOFNARR:          return new Hofnarr(card);
			case Ids.HAHN:             return new Hahn(card);
			case Ids.BANDIT:           return new Bandit(card);
			case Ids.VAGABUND:         return new Vagabund(card);
			case Ids.GLAEUBIGER:       return new Glaeubiger(card);
			case Ids.HERRSCHERIN:      return new Herrscherin(card);
			case Ids.SCHMIED:          return new Schmied(card);
			case Ids.WITWE:            return new Witwe(card);
			case Ids.SCHARMANIN:       return new Scharmanin(card);
			case Ids.WISSENSCHAFTLER:  return new Wissenschaftler(card);
			case Ids.SCHWEIN:          return new Schwein(card);
			case Ids.DONTEUR:          return new Donteur(card);
			case Ids.GEFANGENER:       return new Gefangener(card);
			case Ids.FISCHER:          return new Fischer(card);
			case Ids.KATZE:            return new Katze(card);
			case Ids.PRINZESSIN:       return new Prinzessin(card);
			case Ids.PHILIOSOPH:       return new Philiosoph(card);
			case Ids.MUMIE:            return new Mumie(card);
			case Ids.KOYOTE:           return new Koyote(card);
			case Ids.RABE:             return new Rabe(card);
			case Ids.VERDORBENE:       return new Verdorbene(card);
			case Ids.FLEDERMAUS:       return new Fledermaus(card);
			case Ids.WAHRSAGERIN:      return new Wahrsagerin(card);
			case Ids.PAPAGEI:          return new Papagei(card);
			case Ids.VERSTOSSENER:     return new Verstossener(card);
			case Ids.HEILIGERRITTER:   return new HeiligerRitter(card);
			case Ids.PINGUIN:          return new Pinguin(card);
			case Ids.TOTENBESCHWOERER: return new Totenbeschwoerer(card);
			case Ids.SPIRITUELLE:      return new Spirituelle(card);
			case Ids.HIMMLICHE:        return new Himmliche(card);
			case Ids.PUPPE:            return new Puppe(card);

			//SEGEN
			case Ids.SCHWERT:          return new Schwert(card);
			case Ids.BESCHWOERUNG:     return new Beschwoerung(card);
			case Ids.AMULETT:          return new Amulett(card);
			case Ids.WANDEL:           return new Wandel(card);
			case Ids.FLUEGEL:          return new Fluegel(card);
			case Ids.SCHILD:           return new Schild(card);
			case Ids.STURMANGRIFF:     return new Sturmangriff(card);
			case Ids.YINGUNDYANG:      return new YingUndYang(card);
			case Ids.SCHWARM:          return new Schwarm(card);
			case Ids.GNADE:            return new Gnade(card);
			case Ids.QUELLE:           return new Quelle(card);
			case Ids.SCHILDUNDSCHWERT: return new SchildUndSchwert(card);
			case Ids.KESSEL:           return new Kessel(card);
			case Ids.FLEISCH:          return new Fleisch(card);
			case Ids.HETZE:            return new Hetze(card);
			case Ids.AUFSTIEG:         return new Aufstieg(card);
			case Ids.RUFDERDUNKELHEIT: return new RufDerDunkelheit(card);
			case Ids.HIMMLICHERRUF:    return new HimmlicherRuf(card);
			case Ids.AUFRUFUNG:        return new Aufrufung(card);
			case Ids.HEN:              return new SegenDerSterblichen(card);
			case Ids.SEGENDERBESTIEN:  return new SegenDerBestien(card);
			case Ids.SEGENDERFABEL:    return new SegenDerFabel(card);
			case Ids.SEGENDERDUNKLEN:  return new SegenDerDunklen(card);
			case Ids.VERSTAERKUNG:     return new Verstaerkung(card);
			case Ids.VOLLMOND:         return new Vollmond(card);
			case Ids.REGENBOGEN:       return new Regenbogen(card);
			case Ids.SONNENFINSTERNIS: return new Sonnenfinsternis(card);

			//FLUCH
			case Ids.FLASCHE:        return new Flasche(card);
			case Ids.FLUCH:          return new Fluch(card);
			case Ids.FALLE:          return new Falle(card);
			case Ids.STURM:          return new Sturm(card);
			case Ids.UEBERSTRAHLUNG: return new Ueberstrahlung(card);
			case Ids.HYPNOSE:        return new Hypnose(card);
			case Ids.GEWITTER:       return new Gewitter(card);
			case Ids.FEUER:          return new Feuer(card);
			case Ids.BRAND:          return new Brand(card);
			case Ids.OPFERGABE:      return new Opfergabe(card);
			case Ids.VERLORENESLAND: return new VerlorenesLand(card);
			case Ids.KATASTROPHE:    return new Katastrophe(card);
			case Ids.HETZJAGD:       return new Hetzjagd(card);
			case Ids.VOGELSCHEUCHE:  return new Vogelscheuche(card);
			case Ids.ALCHEMIE:       return new Alchemie(card);

			default: return new CardState(card);
		}
	}
}
