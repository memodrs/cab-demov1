package com.cab.draw;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cab.Tools;
import com.cab.card.*;;

public class ImageLoader {
    public AnimImage loadingScreen;
    public BufferedImage genersichBG;
    public BufferedImage loadingScreenBg;
    public BufferedImage cardBackgroundImage;
    public BufferedImage shopBackgroundImage;

    public BufferedImage instractionKeyboardMenu;
    public BufferedImage flagGermany;
    public BufferedImage flagGermanyHover;
    public BufferedImage flagEngland;
    public BufferedImage flagEnglandHover;

    public AnimImage selectedCardHover;
    public BufferedImage instractionKeyboardG;
    public BufferedImage instractionFabelwesenKannAngreifen;
    public BufferedImage instractionFabelwesenKannNichtAngreifen;
    public BufferedImage instractionNachtgestaltKannAngreifen;
    public BufferedImage instractionNachtgestaltKannNichtAngreifen;

    public BufferedImage boosterHover;
    public BufferedImage boosterMensch;
    public BufferedImage boosterTier;
    public BufferedImage boosterFabelwesen;
    public BufferedImage boosterNachtgestelt;
    public BufferedImage boosterSegen;
    public BufferedImage boosterFluch;


    public BufferedImage iconArrowMarker;
    public BufferedImage iconCheck;

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
	public BufferedImage iconArrowLeft;
	public BufferedImage iconArrowLeftDisabled;
	public BufferedImage iconArrowRight;
	public BufferedImage iconArrowRigthDisabled;

    public BufferedImage blockAtkTiere;

    public BufferedImage cardGameBG;
    public BufferedImage stapelImage;

    public AnimImage animHauptmenuBG;
    public AnimImage animCardEditorBG;
    public AnimImage animDestroy;
    public AnimImage animDestroy2;
    public AnimImage animSchaden;
    public AnimImage animAufruf;
    public AnimImage animHolo;

    public BufferedImage testImage;
    
    public ImageLoader() {
        loadingScreen = new AnimImage("/bgs/loading/", 10, false, 20);
        loadingScreenBg = resourceAsStream("/bgs/loading/loadingScreenBg.png");
    }

    public void init() {
        genersichBG = resourceAsStream("/bgs/genersichBG.png");
        shopBackgroundImage = resourceAsStream("/bgs/shop/bgShop.png");
        cardBackgroundImage = resourceAsStream("/cardGameImgs/cardBg.png");
        
        instractionKeyboardMenu = resourceAsStream("/instractions/keyboard/hauptmenu.png");
        flagGermany = resourceAsStream("/icons/germany.png");
        flagGermanyHover = resourceAsStream("/icons/germanyHover.png");
        flagEngland = resourceAsStream("/icons/england.png");
        flagEnglandHover = resourceAsStream("/icons/englandHover.png");

        selectedCardHover = new AnimImage("/icons/anim/selectedCard/", 6, true, 10);

        instractionFabelwesenKannAngreifen = resourceAsStream("/instractions/fabelwesenSpickzettelKannAngriff.png");
        instractionFabelwesenKannNichtAngreifen = resourceAsStream("/instractions/fabelwesenSpickzettelKannNichtAngriff.png");
        instractionNachtgestaltKannAngreifen = resourceAsStream("/instractions/nachtgestaltSpickzettelKannAngriff.png");
        instractionNachtgestaltKannNichtAngreifen = resourceAsStream("/instractions/nachtgestaltSpickzettelKannNichtAngriff.png");

        instractionKeyboardG = resourceAsStream("/instractions/keyboard/g.png");

        iconArrowMarker = resourceAsStream("/icons/arrowRight.png");
        iconCheck = resourceAsStream("/icons/check.png");

        iconEffektAvailable = resourceAsStream("/icons/effektAvailable.png");
        iconArrowLeft = resourceAsStream("/icons/arrowLeft.png");
        iconArrowLeftDisabled = resourceAsStream("/icons/arrowLeftDisabled.png");
        iconArrowRight = resourceAsStream("/icons/arrowRight.png");
        iconArrowRigthDisabled = resourceAsStream("/icons/arrowRightDisabled.png");

        blockAtkTiere = resourceAsStream("/cardGameImgs/blockAtkTiere.png");

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
        stapelImage = resourceAsStream("/cardGameImgs/stapel.png");

        boosterHover = resourceAsStream("/shop/boosterHover.png");
        boosterMensch = resourceAsStream("/shop/boosterMenschen.png");
        boosterTier = resourceAsStream("/shop/boosterTiere.png");
        boosterFabelwesen = resourceAsStream("/shop/boosterFabelwesen.png");
        boosterNachtgestelt = resourceAsStream("/shop/boosterNachtgestalten.png");
        boosterSegen = resourceAsStream("/shop/boosterSegen.png");
        boosterFluch = resourceAsStream("/shop/boosterFluch.png");

        animCardEditorBG = new AnimImage("/bgs/cardEditor/", 9, true, 6);
        animHauptmenuBG = new AnimImage("/bgs/menu/", 5, true, 12);
        animDestroy = new AnimImage("/icons/anim/destroy/", 10, false, 6);
        animDestroy2 = new AnimImage("/icons/anim/destroy/", 10, false, 6);
        animSchaden = new AnimImage("/icons/anim/schaden/", 9, false, 6);
        animAufruf = new AnimImage("/icons/anim/aufruf/", 11, false, 6);
        animHolo = new AnimImage("/icons/anim/holo/", 14, false, 6);

        testImage = resourceAsStream("/bgs/testImg.png");

    }


    private BufferedImage resourceAsStream(String name) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(name));
        } catch (IOException e) {
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
			default: return null;
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
			default: return null;
		}  
    }

    public BufferedImage getFlagForLand(String land, boolean isHover) {
        switch (land) {
            case "de": return isHover? flagGermanyHover : flagGermany;
            case "en": return isHover? flagEnglandHover : flagEngland;
            default: throw new Error("Unbekanntes Land kein Icon gefunden getFlagForLand " + land);
        }
    }
}
