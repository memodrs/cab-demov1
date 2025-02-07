package com.cab.draw;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.Tools;
import com.cab.card.*;
import com.cab.configs.Sprache;;

public class ImageLoader {
    public AnimImage loadingScreen;
    public BufferedImage genersichBG;
    public BufferedImage loadingScreenBg;
    public BufferedImage cardBackgroundImage;
    public BufferedImage shopBackgroundImage;

    public BufferedImage instractionKeyboardGeneric;
    public BufferedImage instractionKeyboardGBtn;

    public BufferedImage flagGermany;
    public BufferedImage flagGermanyHover;
    public BufferedImage flagEngland;
    public BufferedImage flagEnglandHover;

    public AnimImage selectedCardHover;
    public AnimImage instractionKeyboardG;

    public BufferedImage boosterHover;
    public BufferedImage boosterMensch;
    public BufferedImage boosterTier;
    public BufferedImage boosterFabelwesen;
    public BufferedImage boosterNachtgestelt;
    public BufferedImage boosterSegen;
    public BufferedImage boosterFluch;

    public AnimImage openBooster;

    public BufferedImage book;

    public BufferedImage iconArrowMarker;
    public BufferedImage iconCheck;

    public BufferedImage iconSegenKosten;
    public BufferedImage iconFluchKosten;

    public BufferedImage iconArtUnbekannt;
    public BufferedImage iconArtMensch;
	public BufferedImage iconArtTier;
	public BufferedImage iconArtFabelwesen;
	public BufferedImage iconArtNachtgestalt;
	public BufferedImage iconArtSegen;
	public BufferedImage iconArtFluch;
    public BufferedImage iconArtUnbekanntHover;
	public BufferedImage iconArtMenschHover;
	public BufferedImage iconArtTierHover;
	public BufferedImage iconArtFabelwesenHover;
	public BufferedImage iconArtNachtgestaltHover;
	public BufferedImage iconArtSegenHover;
	public BufferedImage iconArtFluchHover;
    public BufferedImage iconAttackAvailable;

    public BufferedImage iconBlockAtk;
    public BufferedImage iconArtMenschBlock;
	public BufferedImage iconArtTierBlock;
	public BufferedImage iconArtFabelwesenBlock;
	public BufferedImage iconArtNachtgestaltBlock;
	public BufferedImage iconArtSegenBlock;
	public BufferedImage iconArtFluchBlock;

    public BufferedImage iconAllEffekteBlock;
    public BufferedImage iconMenschEffektBlock;
    public BufferedImage iconTierEffektBlock;
    public BufferedImage iconFabelwesenEffektBlock;
    public BufferedImage iconNachtgesteltEffektBlock;

    public BufferedImage iconMenschenAngriffBlock;
    public BufferedImage iconTierAngriffBlock;
    public BufferedImage iconFabelwesenAngriffBlock;
    public BufferedImage iconNachtgestaltenAngriffBlock;
    
    public BufferedImage paper01, paper02, paper03, paper04, paper05, paper06, paper07, paper08, paper09, paper10, paper11;
    public BufferedImage iconHeart, iconAtk;

    public BufferedImage paperStats;
    public BufferedImage paperStatus;

    public BufferedImage status;
    public BufferedImage statusFeuer;
    public BufferedImage statusGift;
    public BufferedImage statusSchild;
    public BufferedImage statusFluegel;
    public BufferedImage statusBlitz;

    public BufferedImage statusFeuerReverse;
    public BufferedImage statusGiftReverse;
    public BufferedImage statusSchildReverse;
    public BufferedImage statusFluegelReverse;
    public BufferedImage statusBlitzReverse;

    public BufferedImage iconEffektAvailable;
	public BufferedImage navigationArrowLeft;
	public BufferedImage navigationArrowLeftDisabled;
	public BufferedImage navigationArrowRight;
	public BufferedImage navigationArrowRightDisabled;

    public BufferedImage iconAufgeben;
    public BufferedImage iconAufgebenHover;

    public BufferedImage iconArrowLeft;
    public BufferedImage iconArrowRight;
    public BufferedImage iconHand;
    public BufferedImage iconGrave;
    public BufferedImage iconPage;
    public BufferedImage iconPageSelected;

    public BufferedImage iconSave;
    public BufferedImage iconLoad;

    public BufferedImage cardGameBG;
    public BufferedImage cardGameBoard;
    public BufferedImage cardsGameOponentBoardPlace;
    public BufferedImage cardsGamePlayerBoardPlace;

    public AnimImage animHauptmenuBG;
    public AnimImage animCardEditorBG;
    public AnimImage animDestroy;
    public AnimImage animDestroy2;
    public AnimImage animSchaden;
    public AnimImage animAufruf;
    public AnimImage animHolo;
    
    public List<AnimImage> animHealPlayerList = new ArrayList<>();
    public List<AnimImage> animHealOponentList = new ArrayList<>();

    public AnimImage holoEffektImg;
    public AnimImage cardIsPlayable;
    public AnimImage cardIsEffektIsPossible;
    public AnimImage cardSelectGreen;
    public AnimImage cardSelectRed;
    public AnimImage cardTargeted;
    
    public AnimImage blinkRed;

    public AnimImage singleplayerShopBG;
    public AnimImage singleplayerCoinBG;
    public BufferedImage iconCoind;

    public BufferedImage transparent;
    

    public ImageLoader() {
        loadingScreen = new AnimImage("/bgs/loading/", 10, false, 20);
        loadingScreenBg = resourceAsStream("/bgs/loading/loadingScreenBg.png");
    }

    public void init() {
        transparent = resourceAsStream("/cardGameImgs/transparent.png");
        holoEffektImg = new AnimImage("/anim/holo/", 14, false, 6);
		
		cardIsPlayable = new AnimImage("/anim/cardIsPlayable/", 12, false, 8);
		cardIsEffektIsPossible = new AnimImage("/anim/cardIsEffektIsPossible/", 15, false, 6);
		cardSelectGreen = new AnimImage("/anim/selectOwnCardGreen/", 15, false, 6);
		cardSelectRed = new AnimImage("/anim/selectOponentCardRed/", 15, false, 6);
        cardTargeted = new AnimImage("/anim/targeted/", 2, false, 10);

        blinkRed = new AnimImage("/anim/blinkRed/", 2, false, 8);

        genersichBG = resourceAsStream("/bgs/genersichBG.png");
        shopBackgroundImage = resourceAsStream("/bgs/shop/bgShop.png");
        cardBackgroundImage = resourceAsStream("/cardGameImgs/cardBg.png");
        
        instractionKeyboardGeneric = resourceAsStream("/instractions/keyboard/generisch.png");
        instractionKeyboardGBtn = resourceAsStream("/instractions/keyboard/g.png");

        flagGermany = resourceAsStream("/icons/germany.png");
        flagGermanyHover = resourceAsStream("/icons/germanyHover.png");
        flagEngland = resourceAsStream("/icons/england.png");
        flagEnglandHover = resourceAsStream("/icons/englandHover.png");

        selectedCardHover = new AnimImage("/anim/selectedCard/", 6, true, 10);
        instractionKeyboardG = new AnimImage("/instractions/keyboard/g/", 8, false, 12);

        iconArrowMarker = resourceAsStream("/icons/arrowRight.png");
        iconCheck = resourceAsStream("/icons/check.png");

        iconAufgeben = resourceAsStream("/icons/iconAufgeben.png");
        iconAufgebenHover = resourceAsStream("/icons/iconAufgebenHover.png");

        iconArrowLeft = resourceAsStream("/icons/iconArrowLeft.png");
        iconArrowRight = resourceAsStream("/icons/iconArrowRight.png");
        iconHand = resourceAsStream("/icons/iconHand.png");
        iconGrave = resourceAsStream("/icons/iconGrave.png");
        iconPage = resourceAsStream("/icons/iconPage.png");
        iconPageSelected = resourceAsStream("/icons/iconPageSelected.png");
        
        iconSave = resourceAsStream("/icons/iconSave.png");
        iconLoad = resourceAsStream("/icons/iconLoad.png");

        iconAllEffekteBlock = resourceAsStream("/icons/iconBlockAllEffekte.png");
        iconMenschEffektBlock = resourceAsStream("/icons/blockMenschenEffekt.png");
        iconTierEffektBlock = resourceAsStream("/icons/blockTiereEffekt.png");
        iconFabelwesenEffektBlock = resourceAsStream("/icons/blockFabelwesenEffekt.png");
        iconNachtgesteltEffektBlock = resourceAsStream("/icons/blockNachtgestaltEffekt.png");

        iconEffektAvailable = resourceAsStream("/icons/effektAvailable.png");
        navigationArrowLeft = resourceAsStream("/icons/arrowLeft.png");
        navigationArrowLeftDisabled = resourceAsStream("/icons/arrowLeftDisabled.png");
        navigationArrowRight = resourceAsStream("/icons/arrowRight.png");
        navigationArrowRightDisabled = resourceAsStream("/icons/arrowRightDisabled.png");

        iconMenschenAngriffBlock = resourceAsStream("/icons/blockAtkMenschen.png");
        iconTierAngriffBlock = resourceAsStream("/icons/blockAtkTiere.png");
        iconFabelwesenAngriffBlock = resourceAsStream("/icons/blockAtkFabelwesen.png");
        iconNachtgestaltenAngriffBlock = resourceAsStream("/icons/blockAtkNachtgestalten.png");

        iconSegenKosten = resourceAsStream("/icons/iconSegenKosten.png");
        iconFluchKosten = resourceAsStream("/icons/iconFluchKosten.png");

        iconArtUnbekannt =         resourceAsStream("/icons/artUnbekannt.png");
        iconArtMensch =            resourceAsStream("/icons/artMensch.png");
        iconArtTier =              resourceAsStream("/icons/artTier.png");
        iconArtFabelwesen =        resourceAsStream("/icons/artFabelwesen.png");
        iconArtNachtgestalt =      resourceAsStream("/icons/artNachtgestalt.png");
        iconArtSegen =             resourceAsStream("/icons/artSegen.png");
        iconArtFluch =             resourceAsStream("/icons/artFluch.png");
        iconArtUnbekanntHover =    resourceAsStream("/icons/artUnbekanntHover.png");
        iconArtMenschHover =       resourceAsStream("/icons/artMenschHover.png");
        iconArtTierHover =         resourceAsStream("/icons/artTierHover.png");
        iconArtFabelwesenHover =   resourceAsStream("/icons/artFabelwesenHover.png");
        iconArtNachtgestaltHover = resourceAsStream("/icons/artNachtgestaltHover.png");
        iconArtSegenHover	 =     resourceAsStream("/icons/artSegenHover.png");
        iconArtFluchHover =        resourceAsStream("/icons/artFluchHover.png");

        iconBlockAtk =              resourceAsStream("/icons/blockAtk.png");
        iconArtMenschBlock =       resourceAsStream("/icons/blockMenschen.png");
        iconArtTierBlock =         resourceAsStream("/icons/blockTiere.png");
        iconArtFabelwesenBlock =   resourceAsStream("/icons/blockFabelwesen.png");
        iconArtNachtgestaltBlock = resourceAsStream("/icons/blockNachtgestalten.png");
        iconArtSegenBlock =        resourceAsStream("/icons/blockSegen.png");
        iconArtFluchBlock =        resourceAsStream("/icons/blockFluch.png");   

        
        paper01 = resourceAsStream("/paper/paper01.png");
        paper02 = resourceAsStream("/paper/paper02.png");
        paper03 = resourceAsStream("/paper/paper03.png");
        paper04 = resourceAsStream("/paper/paper04.png");
        paper05 = resourceAsStream("/paper/paper05.png");
        paper06 = resourceAsStream("/paper/paper06.png");
        paper07 = resourceAsStream("/paper/paper07.png");
        paper08 = resourceAsStream("/paper/paper08.png");
        paper09 = resourceAsStream("/paper/paper09.png");
        paper10 = resourceAsStream("/paper/paper10.png");
        paper11 = resourceAsStream("/paper/paper11.png");

        paperStats = resourceAsStream("/paper/paperStats01.png");

        iconHeart = resourceAsStream("/icons/life.png");
        iconAtk =   resourceAsStream("/icons/atk.png");

        status = resourceAsStream("/icons/status.png");
        statusFeuer =   resourceAsStream("/icons/statusFeuer.png");
        statusGift =   resourceAsStream("/icons/statusGift.png");
        statusSchild =   resourceAsStream("/icons/statusSchild.png");
        statusFluegel =   resourceAsStream("/icons/statusFluegel.png");
        statusBlitz =   resourceAsStream("/icons/statusBlitz.png");

        statusFeuerReverse = Tools.rotateImage180(statusFeuer);
        statusGiftReverse = Tools.rotateImage180(statusGift );
        statusSchildReverse = Tools.rotateImage180(statusSchild);
        statusFluegelReverse = Tools.rotateImage180(statusFluegel);
        statusBlitzReverse = Tools.rotateImage180(statusBlitz);

        iconAttackAvailable = resourceAsStream("/icons/attackAvailable.png");

        cardGameBG = resourceAsStream("/cardGameImgs/bg.png");
        cardGameBoard = resourceAsStream("/cardGameImgs/board.png");
        cardsGameOponentBoardPlace = resourceAsStream("/cardGameImgs/oponentCardPlace.png");
        cardsGamePlayerBoardPlace = resourceAsStream("/cardGameImgs/playerCardPlace.png");

        openBooster = new AnimImage("/anim/openBooster/", 16, false, 4);

        boosterHover = resourceAsStream("/shop/boosterHover.png");
        boosterMensch = resourceAsStream("/shop/boosterMenschen.png");
        boosterTier = resourceAsStream("/shop/boosterTiere.png");
        boosterFabelwesen = resourceAsStream("/shop/boosterFabelwesen.png");
        boosterNachtgestelt = resourceAsStream("/shop/boosterNachtgestalten.png");
        boosterSegen = resourceAsStream("/shop/boosterSegen.png");
        boosterFluch = resourceAsStream("/shop/boosterFluch.png");

        book = resourceAsStream("/lexikon/book.png");

        animCardEditorBG = new AnimImage("/bgs/cardEditor/", 9, true, 25);
        animHauptmenuBG = new AnimImage("/bgs/menu/", 5, true, 12);
        animDestroy = new AnimImage("/anim/destroy/", 10, false, 6);
        animDestroy2 = new AnimImage("/anim/destroy/", 10, false, 6);

        for (int i = 0; i < 4; i++) {
            animHealPlayerList.add(new AnimImage("/anim/heal/", 17, false, 6));
            animHealOponentList.add(new AnimImage("/anim/heal/", 17, false, 6));
        }

        animSchaden = new AnimImage("/anim/schaden/", 9, false, 6);
        animAufruf = new AnimImage("/anim/aufruf/", 11, false, 6);
        animHolo = new AnimImage("/anim/holo/", 14, false, 6);

        singleplayerShopBG = new AnimImage("/singleplayer/shop/bg/", 7, true, 6);
        singleplayerCoinBG = new AnimImage("/singleplayer/coin/bg/", 8, true, 6);
        iconCoind = resourceAsStream("/icons/coin.png");
        
    }


    private BufferedImage resourceAsStream(String name) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(name));
        } catch (IOException e) {
            System.err.println(name + " resource nicht gefunden");
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println(name + " resource nicht gefunden");
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getStatusImage(Status status, boolean isReverse) {
        switch (status) {
            case Feuer:     return isReverse? statusFeuerReverse   :   statusFeuer;
            case Gift:      return isReverse? statusGiftReverse    :    statusGift;
            case Schild:    return isReverse? statusSchildReverse  :  statusSchild;
            case Fluegel:   return isReverse? statusFluegelReverse : statusFluegel;
            case Blitz:     return isReverse? statusBlitzReverse   :   statusBlitz;
            default:        return null;
        }
    }

    public BufferedImage getArtIconForArt(Art art, boolean selectHover) {
		switch (art) {
			case Unbekannt: return selectHover? iconArtUnbekanntHover : iconArtUnbekannt;
			case Mensch: return selectHover? iconArtMenschHover : iconArtMensch;
			case Tier: return selectHover? iconArtTierHover : iconArtTier;
			case Fabelwesen: return selectHover? iconArtFabelwesenHover : iconArtFabelwesen;
			case Nachtgestalt: return selectHover? iconArtNachtgestaltHover : iconArtNachtgestalt;
			case Segen: return selectHover? iconArtSegenHover : iconArtSegen;
			case Fluch: return selectHover? iconArtFluchHover : iconArtFluch;
			default: throw new Error("Für die Art konnte kein Icon gefunden werden: " + art);
		}
	}

    public BufferedImage getKostenIcon(Art art) {
        switch (art) {
            case Segen: return iconSegenKosten;
            case Fluch: return iconFluchKosten;
			default: throw new Error("Für die Art konnte kein Kosten Icon gefunden werden: " + art);
        }
    }

    public BufferedImage getBoosterForArt(Art art) {
        switch (art) {
			case Mensch:       return boosterMensch;
			case Tier:         return boosterTier;
			case Fabelwesen:   return boosterFabelwesen;
			case Nachtgestalt: return boosterNachtgestelt;
			case Segen:        return boosterSegen;
			case Fluch:        return boosterFluch;
			default: throw new Error("Für die Art konnte kein booster pack bild gefunden werden: " + art);
		}  
    }

    public BufferedImage getFlagForLand(Sprache sprache) {
        switch (sprache) {
            case Deutsch:  return flagGermany;
            case Englisch:  return flagEngland;
            default: throw new Error("Unbekanntes Land kein Icon gefunden getFlagForLand " + sprache);
        }
    }
}
