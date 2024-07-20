package com.cab.cardGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.attribute.AclEntryFlag;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.draw.SelectedCard;

public class CardGameDrawer {
	CardGame cg;
	GamePanel gp;
	String msg = ""; // Anzeige was ist auf dem Board passiert 

	int fpsCounter = 0;

	Color colorSelectionOponentCardAnimOne = new Color(200, 0, 0, 100);
	Color colorSelectionOponentCardAnimTwo = new Color(200, 0, 0, 20);
	Color colorSelectionOwnCardAnimOne = new Color(0, 200, 0, 100);
	Color colorSelectionOwnCardAnimTwo = new Color(0, 200, 0, 20);
	Color colorActiveEffektCard = new Color(255, 255, 255, 120);

	//AnimImgs
	BufferedImage[] heilenImages = new BufferedImage[12];
	BufferedImage[] schadenImages = new BufferedImage[6];

	boolean showAnimOnBoard = false;
	int animX;
	int animY;
	int animIdx = 0;
	BufferedImage[] animImages;

	int counterAnim = 0;
	boolean flicker = true;


	int statsBeschriftungOffset;
	
	int iconStatusY;
	int iconStatusSize;


	int iconEffektAvailableSize;
	int iconEffektAvailableSizeX;
	int iconEffektAvailableSizeY;

	int iconAttackAvailableX;

	//Animation Angriff
	boolean showAngriffAnim = false;
	int xAngriffAnimCardAngreifer;
	int xAngriffAnimCardAngegriffener;
	int yAngriffAnimCard;
	boolean showAngreifer;
	boolean showAngegriffener;

	int angriffAnimCounter = 0;
	BufferedImage angreiferImg;
	BufferedImage angegriffenerImg;
	int angriffAnimId; 

	int schadenIdx = 0;
	int schadenAnimCounter = 0;
	BufferedImage[] direkterSchaden = new BufferedImage[9];

	int explosionIdx = 0;
	int explosionAnimCounter = 0;
	BufferedImage[] explosionImg = new BufferedImage[10];

	int cardAbstand;
	int lifeCounterPlayerY;
	int lifeCounterOponentY;
	int segenCounterPlayerY;
	int segenCounterOponentY;
	int fluchCounterPlayerY;
	int fluchCounterOponentY;
	int playerStatsIconX;
	int playerStatasNumberX;
	int statsPaperX;
	int playerStatsPaperY, oponentStatyPaperY;
	int statsPaperWidth, statsPaperHeight;

	int stapelBeschriftungX;
	int stapelBeschriftungEinstelligX;
	int stapelX;
	int stapelY;
	int stapelOponentY;
	int stapelWidth;

	int iconMarkerSize;
	int handPanelIconMarkerX;

	int handCardWidth;
	int handCardHeight;

	int handPanelMarkerX;
	int handPanelMarkerY;
    int handPanelHoehe;
    int handPanely;
    int handPanelOponenty;
	int handPanelSelectedY;
    
    int boardPanelBreite;
    int boardPanelx;
    int boardPanely;
    int boardPanelOponenty;
    int gravePanelx;
	int gravePanelyOffset;
    
	int selectedCardX;

    int widthDialog;
	int heightDialog;
	int dialogx;
	int dialogy;
	int dialogBorderX;
	int dialogBorderY;
	int dialogBorderWidth;
	int dialogBorderHeight;
	int dialogCardX;
	int dialogCardY;
	int dialogFirstOptionMarkerX;
	int dialogFirstOptionMarkerY;
	int dialogFirstOptionBeschriftungX;
	int dialogFirstOptionBeschriftungY;
	int dialogArrowRightX;
	int dialogArrowLeftX;

	int dialogSecondOptionMarkerX;
	int dialogSecondOptionMarkerY;
	int dialogSecondOptionBeschriftungX;
	int dialogSecondOptionBeschriftungY;

	Color dialogColor = new Color(0, 0, 0, 210);
	
	int beschreibungWortCounter = 0;
	int beschreibungWortMaxInZeile = 4;

	int counter = 0;
	boolean showEffektCard = false;
	List<CardState> effektCards = new ArrayList<>();
	
	int selectedCardWidth;
    int selectedCardHeight;
	SelectedCard selectedCard;

	private BufferedImage[] loadAnimImg(String path, int length) {
		BufferedImage[] res = new BufferedImage[length];
		
		for (int i = 0; i < res.length; i++) {
			try {
				res[i] = ImageIO.read(getClass().getResourceAsStream(path + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		return res;
	}
	
	public CardGameDrawer(CardGame cg) {
		this.cg = cg;
		this.gp = cg.gp;

		heilenImages = loadAnimImg("/cardGameImgs/anim/heilen/", heilenImages.length);
		schadenImages = loadAnimImg("/cardGameImgs/anim/schaden/", schadenImages.length);
		direkterSchaden = loadAnimImg("/cardGameImgs/anim/direkterSchaden/", direkterSchaden.length);
		explosionImg = loadAnimImg("/cardGameImgs/anim/explosion/", direkterSchaden.length);

		init();
	}

	public void init() {
		//Icons
		iconStatusSize = (int) (gp.tileSize * 0.8);
		iconStatusY = gp.cardHeight - gp.tileSize - 5;
		iconEffektAvailableSize = gp.tileSize / 2;
		iconEffektAvailableSizeX = gp.cardWidth / 2 - 5;
		iconEffektAvailableSizeY = 5;

		iconAttackAvailableX = gp.cardWidth / 3;
		iconMarkerSize = gp.tileSize * 3;

		//Hand
		handCardWidth = (int) ((gp.cardWidth + gp.tileSize) * 0.9);
		handCardHeight = (int) ((gp.cardHeight + gp.tileSize) * 0.95);

		handPanelHoehe = gp.cardHeight;
		handPanely = (int) (Main.screenHeight * 0.822);
	    handPanelOponenty = gp.tileSize;
		handPanelSelectedY = handPanely;
		handPanelIconMarkerX = gp.tileSize * 3;
	   
		
		boardPanelBreite = gp.cardWidth * 4 + cardAbstand * 4;
		boardPanelx = (int) (Main.screenWidth * 0.4);
		boardPanely = (int) (Main.screenHeight * 0.56);
		boardPanelOponenty = (int) (Main.screenHeight * 0.34);

		//Stats
		lifeCounterPlayerY = handPanely + gp.tileSize * 2;
		fluchCounterPlayerY = lifeCounterPlayerY - (int) (gp.tileSize * 1.3);
		segenCounterPlayerY = fluchCounterPlayerY - (int) (gp.tileSize * 1.3);
		lifeCounterOponentY = handPanelOponenty;
		fluchCounterOponentY = lifeCounterOponentY + (int) (gp.tileSize * 1.3);
		segenCounterOponentY = fluchCounterOponentY + (int) (gp.tileSize * 1.3);		
		playerStatsIconX = Main.screenWidth - gp.tileSize * 3;
		playerStatasNumberX = playerStatsIconX + gp.imageLoader.iconHeart.getWidth() + gp.tileSize + 10;
		statsBeschriftungOffset = gp.tileSize - 15;
		statsPaperX = playerStatsIconX - (int) (gp.tileSize * 0.5);
		playerStatsPaperY = segenCounterPlayerY - (int) (gp.tileSize * 0.8);
		oponentStatyPaperY = lifeCounterOponentY - (int) (gp.tileSize * 0.8);
		statsPaperWidth = gp.tileSize * 3;
		statsPaperHeight = gp.tileSize * 5;


		//Stapel
		stapelX = (int) (Main.screenWidth * 0.831);
		stapelY = (int) (Main.screenHeight * 0.835);
		stapelOponentY = (int) (Main.screenHeight * 0.04);
		stapelBeschriftungX = (int) (stapelX + gp.tileSize * 1.08);
		stapelBeschriftungEinstelligX = stapelBeschriftungX;
		stapelWidth = gp.cardWidth + gp.tileSize / 2;

		cardAbstand = (int) (Main.screenWidth * 0.01);

		gravePanelx = (int) (Main.screenWidth * 0.765);

		//Dialog
	    widthDialog = gp.tileSize * 6;
	    heightDialog = gp.tileSize * 7;
	    dialogx = (Main.screenWidth - widthDialog) / 2;
	    dialogy = (Main.screenHeight - heightDialog) / 2;
		dialogBorderHeight = heightDialog - 10;
		dialogBorderWidth = widthDialog - 10;
		dialogBorderX = dialogx + 5;
		dialogBorderY = dialogy + 5;
		dialogCardX = (int) (dialogx * 1.07);
		dialogCardY = (int) (dialogy * 1.04);
		dialogFirstOptionMarkerX = (int) (dialogCardX * 0.91);
		dialogFirstOptionMarkerY = (int) ((dialogCardY + gp.cardHeight) * 0.95);
		dialogFirstOptionBeschriftungX = (int) ((dialogFirstOptionMarkerX + iconMarkerSize) * 0.98);
		dialogFirstOptionBeschriftungY = (int) (Main.screenHeight * 0.535);

		dialogSecondOptionMarkerX = dialogFirstOptionMarkerX;
		dialogSecondOptionMarkerY = dialogFirstOptionMarkerY + gp.tileSize;
		dialogSecondOptionBeschriftungX = dialogFirstOptionBeschriftungX;
		dialogSecondOptionBeschriftungY = dialogFirstOptionBeschriftungY + gp.tileSize;
		dialogArrowRightX = (int) ((dialogFirstOptionMarkerX + dialogBorderWidth) * 0.91);
		dialogArrowLeftX = (int) (dialogFirstOptionMarkerX * 1.04);

		selectedCard = new SelectedCard(gp, gp.tileSize, gp.tileSize);
		selectedCardHeight = gp.cardHeight * 3;
		selectedCardWidth = gp.cardWidth * 3;
	}
	
	public void showMsg(String msg) {
		this.msg = msg;
	}
	
	private void startAnim() {
		fpsCounter = 0;
		animIdx = 0;
		showAnimOnBoard = true;
	}

	public void showAnimKarteStatsAenderung(Player p, CardState card, boolean isPositiv) {
		int idx = -1;
		for (int i = 0; i < p.boardCards.size(); i++) {
			if (p.boardCards.get(i).id == card.id) {
				idx = i;
			}
		}
		animX = (int) ((boardPanelx + gp.cardWidth * idx + cardAbstand * idx) - gp.tileSize * 0.8);
		animY = p.isPlayer? boardPanely : boardPanelOponenty;
		animImages = isPositiv? heilenImages : schadenImages;
		startAnim();
	}

	private void drawStats(Graphics2D g2, Player p, int lifeCounterY, int fluchCounterY, int segenCounterY, int paperY) {
	    g2.setColor(Color.BLACK);
		g2.setFont(Main.v.rumburakFont25);
		g2.drawImage(gp.imageLoader.paper11, statsPaperX, paperY, statsPaperWidth, statsPaperHeight, null);
	    g2.drawImage(gp.imageLoader.iconHeart, playerStatsIconX, lifeCounterY, gp.tileSize, gp.tileSize, null);
	    g2.drawString(String.valueOf(p.lifeCounter), playerStatasNumberX, lifeCounterY + statsBeschriftungOffset);

	    g2.drawImage(gp.imageLoader.iconArtFluch, playerStatsIconX, fluchCounterY, gp.tileSize, gp.tileSize, null);
	    g2.drawString(String.valueOf(p.fluchCounter), playerStatasNumberX, fluchCounterY + statsBeschriftungOffset);

	    g2.drawImage(gp.imageLoader.iconArtSegen, playerStatsIconX, segenCounterY, gp.tileSize, gp.tileSize, null);
	    g2.drawString(String.valueOf(p.segenCounter), playerStatasNumberX, segenCounterY + statsBeschriftungOffset);
	}

	private void drawStapel(Graphics2D g2, int y, Player p) {
		if (p.stapel.size() > 0) {
			g2.drawImage(gp.imageLoader.stapelImage, stapelX, y, stapelWidth, gp.cardHeight, null);
			g2.setColor(Color.white);
			g2.setFont(Main.v.rumburakFont25);

			if (p.stapel.size() < 10) {
				g2.drawString(String.valueOf(p.stapel.size()), stapelBeschriftungEinstelligX, y + gp.cardHeight / 2);
			} else {
				g2.drawString(String.valueOf(p.stapel.size()), stapelBeschriftungX, y + gp.cardHeight / 2);
			}
		}
	}

	private void drawHandPanel(Graphics2D g2, int handPanely, Player p, boolean isPlayer) {
	    g2.setColor(Main.v.colorTransparent);
	    int handPanelBreite = handCardWidth* p.handCards.size() + 5 * (p.handCards.size() - 1);
	    int handPanelx = (gp.getWidth() - handPanelBreite) / 2;

	    for (int i = 0; i < p.handCards.size(); i++) {
	        g2.setColor(Main.v.colorTransparent);
	        int offsetX = handPanelx + handCardWidth * i + 10 * i;
	        
			int handPanelYselectedCard = i == cg.selectedIdx && cg.isState(cg.handCardState)? handPanely - gp.tileSize : handPanely - 10;
	        if (isPlayer) {
				CardState card = p.handCards.get(i);
				boolean isEffektManualActivatable = cg.isEffektManualActivatable(p, card, cg.effekteMangaer.triggerManualFromHand);
				if (!(i == cg.selectedHandCardIdx && cg.isState(cg.handCardSelectedState))) {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.id).image, offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);

					if (cg.isOnTurn) {
						if (isEffektManualActivatable) {
							g2.drawImage(gp.imageLoader.iconEffektAvailable, offsetX + iconEffektAvailableSizeX, handPanelYselectedCard + iconEffektAvailableSizeY, iconEffektAvailableSize, iconEffektAvailableSize, null);
							BufferedImage imageSpickZettel = null;
							if (card.art == Art.Fabelwesen && cg.isArtOnBoardOfPlayer(p, Art.Mensch)) {
								imageSpickZettel = gp.imageLoader.instractionFabelwesenKannAngreifen;
							} else if (card.art == Art.Fabelwesen && !cg.isArtOnBoardOfPlayer(p, Art.Mensch)) {
								imageSpickZettel = gp.imageLoader.instractionFabelwesenKannNichtAngreifen;
							} else if (card.art == Art.Nachtgestalt && cg.isArtOnBoardOfPlayer(p, Art.Mensch)) { 
								imageSpickZettel = gp.imageLoader.instractionNachtgestaltKannNichtAngreifen;
							} else if (card.art == Art.Nachtgestalt && !cg.isArtOnBoardOfPlayer(p, Art.Mensch)) { 
								imageSpickZettel = gp.imageLoader.instractionNachtgestaltKannAngreifen;
							}

							if (imageSpickZettel != null) {
								g2.drawImage(imageSpickZettel, gp.tileSize * 8, boardPanely, gp.tileSize * 5, (int) (gp.tileSize * 3), null);
							}
						} 

						if (cg.isState(cg.handCardState)) {
							if (card.art == Art.Segen && card.defaultCard.kosten <= p.segenCounter && card.isEffektPossible(p)) {
								g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);
							} else if (card.art == Art.Fluch && card.defaultCard.kosten <= p.fluchCounter && card.isEffektPossible(p)) {
								g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);
							} else if (!card.defaultCard.isSpell && !cg.creatureWasPlayedInTurn) {
								g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);
							}

							g2.drawImage(gp.imageLoader.iconArrowMarker, handPanelx - iconMarkerSize, handPanely, iconMarkerSize, iconMarkerSize, null);
					
							if (i == cg.selectedIdx) {
								g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);
								if (isEffektManualActivatable) {
									g2.drawImage(gp.imageLoader.instractionKeyboardG, offsetX - (int) (gp.tileSize * 0.5), handPanely - gp.tileSize * 3, gp.tileSize * 4, gp.tileSize * 2, null);
									g2.setColor(Color.BLACK);
									g2.drawString("Effekt aktivieren", offsetX, handPanely - (int) (gp.tileSize * 1.7));
								} 

							}
						}
					}
				}
	        } else {
	            g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, handPanely, handCardWidth, handCardHeight, null);
	        }


	    }
	}

	private void drawBoardPanel(Graphics2D g2, int y, Player p, boolean isPlayer) {
		g2.setColor(Main.v.colorTransparent); 

		if ((cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) && isPlayer) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, boardPanelx - iconMarkerSize, y, iconMarkerSize, iconMarkerSize, null);
		} else if ((cg.isState(cg.boardOponentState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState)) && !isPlayer) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, boardPanelx - iconMarkerSize, y, iconMarkerSize, iconMarkerSize, null);
		}

		for (int i = 0; i < p.boardCards.size(); i++) {
        	int offsetX = (int) (boardPanelx + gp.cardWidth * i + cardAbstand * i);
			CardState card = p.boardCards.get(i);
			boolean isEffektManualActivatable = cg.isEffektManualActivatable(p, card, cg.effekteMangaer.triggerManualFromBoard) && isPlayer;

        	if (card.isHide) {
                g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, gp.cardWidth, gp.cardHeight, null);
        	} else {
    			g2.drawImage(gp.cardLoader.getCard(card.defaultCard.id).image, offsetX, y, gp.cardWidth, gp.cardHeight, null);
				
				
				if (isEffektManualActivatable) {
					g2.drawImage(gp.imageLoader.iconEffektAvailable, offsetX + iconEffektAvailableSizeX, y + iconEffektAvailableSizeY, iconEffektAvailableSize, iconEffektAvailableSize, null);
				}

				if (cg.checkIsAttackAlowed(p, i) && !cg.inactiveMode && isPlayer) {
					g2.drawImage(gp.imageLoader.iconAttackAvailable, offsetX + iconAttackAvailableX, y + gp.cardHeight, gp.tileSize, gp.tileSize, null);
				}

        	}

        	if (isPlayer && (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState))) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
				}
			} else if (!isPlayer && (cg.isState(cg.boardOponentState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState))) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
				}
			}

			if (cg.isState(cg.boardState)) {
				if (cg.selectedIdx == i && isEffektManualActivatable) {
					g2.drawImage(gp.imageLoader.instractionKeyboardG, offsetX - (int) (gp.tileSize * 0.5), y - gp.tileSize * 2, gp.tileSize * 4, gp.tileSize * 2, null);
					g2.setColor(Color.BLACK);
					g2.drawString("Effekt aktivieren", offsetX, y - (int) (gp.tileSize * 0.7));
				}
			} else if (cg.isState(cg.effektSelectOponentBoardState)) {
				if (isPlayer) {
					if (card == cg.activeEffektCard) {
						g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
					}
				} else {
					if (cg.activeEffektCard.isCardValidForSelection(card)) {
						g2.drawImage(card.defaultCard.cardSelectRed.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
					}
				}
			}

			else if (cg.isState(cg.effektSelectOwnBoardState)) {
				if (isPlayer) {
					if (card == cg.activeEffektCard) {
						g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
					}

					if (cg.activeEffektCard.isCardValidForSelection(card)) {
						g2.drawImage(card.defaultCard.cardSelectGreen.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
					}
				} 
			}

			else if (cg.isState(cg.selectCardToAttackState)) {
				if (isPlayer) {
					if (i == cg.selectedBoardCardIdx) {
						g2.drawImage(p.boardCards.get(i).defaultCard.cardSelectRed.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
					}
				} else {
					g2.drawImage(card.defaultCard.cardSelectRed.get(), offsetX, y, gp.cardWidth, gp.cardHeight, null);
				}
			}	
		}
	}
	
	private void drawGrave(Graphics2D g2, int y, Player p, boolean isPlayer) {  
		g2.setColor(Main.v.colorTransparent); 
 
		if (cg.isState(cg.graveState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gravePanelx - iconMarkerSize, boardPanely, iconMarkerSize, iconMarkerSize, null);
		} else if (cg.isState(cg.graveOponentState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gravePanelx - iconMarkerSize, boardPanelOponenty, iconMarkerSize, iconMarkerSize, null);
		}
		
        if (p.graveCards.size() > 0) {
        	g2.drawImage(gp.cardLoader.getCard(p.graveCards.get(p.graveCards.size() - 1).defaultCard.id).image, gravePanelx, y, gp.cardWidth, gp.cardHeight, null);
    		g2.setPaint(Main.v.colorGardianSelectFromGrave);

        	
            if (isPlayer && cg.isState(cg.graveState)) {
				g2.drawImage(gp.imageLoader.selectedCardHover.get(), gravePanelx, y, gp.cardWidth, gp.cardHeight, null);
            } else  if (!isPlayer && cg.isState(cg.graveOponentState)) {
				g2.drawImage(gp.imageLoader.selectedCardHover.get(), gravePanelx, y, gp.cardWidth, gp.cardHeight, null);
            } 
            
			if (isPlayer) {
				for (int i = 0; i < p.graveCards.size(); i++) {
					if (cg.isEffektManualActivatable(p, p.graveCards.get(i), cg.effekteMangaer.triggerManualFromGrave)) {
						g2.drawImage(gp.imageLoader.iconEffektAvailable, gravePanelx + iconEffektAvailableSizeX, y + iconEffektAvailableSizeY, iconEffektAvailableSize, iconEffektAvailableSize, null);
						break;
					}
				}
			}

        }
	}
	
	public void drawDialog(Graphics2D g2) {
        if (cg.isState(cg.handCardSelectedState) || 
        		cg.isState(cg.boardCardSelectedState) || 
        		cg.isState(cg.graveSelectedState) || 
        		cg.isState(cg.graveSelectedOponentState) || 
        		cg.isState(cg.effektQuestionStateBoard) ||
        		cg.isState(cg.effektQuestionStateHand) ||			
				cg.isState(cg.effektQuestionStateGrave) ||
				cg.isState(cg.effektSelectOwnGraveState) || 
				cg.isState(cg.effektSelectOponentGraveState)
			) {


    	    
			g2.setColor(dialogColor);
			g2.fillRoundRect(dialogx, dialogy, widthDialog, heightDialog, 35, 35);
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(5)); 
			g2.drawRoundRect(dialogBorderX, dialogBorderY, dialogBorderWidth, dialogBorderHeight, 25, 25);
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

			
            if (cg.isState(cg.handCardSelectedState)) {
            	//Beim Klicken wird die Karte entfernt und dann erst State gewechselt
            	if (!gp.blockBtn) {
            		if (cg.selectedIdx == 0) {
                		g2.drawImage(gp.cardLoader.getCard(cg.player.handCards.get(cg.selectedHandCardIdx).defaultCard.id).image, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);
            		} else {
                		g2.drawImage(gp.imageLoader.cardBackgroundImage, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);
            		}
            	}

    			if (cg.selectedIdx == 0) {
    				g2.setColor(Color.white);
					g2.drawImage(gp.imageLoader.iconArrowMarker, dialogFirstOptionMarkerX , dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
    			} else {
    				g2.setColor(Color.gray);
    			}

 
        		g2.drawString("Aufrufen", dialogFirstOptionBeschriftungX, dialogFirstOptionBeschriftungY);	
				
    			
    			if (cg.selectedIdx == 1) {
    				g2.setColor(Color.white);
					g2.drawImage(gp.imageLoader.iconArrowMarker, dialogSecondOptionMarkerX , dialogSecondOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
    			} else {
    				g2.setColor(Color.gray);
    			}
    			g2.drawString("Verdecken", dialogSecondOptionBeschriftungX, dialogSecondOptionBeschriftungY);	
            }  
             
            else if (cg.isState(cg.boardCardSelectedState)) {
            	CardState selectedBoardCard = cg.player.boardCards.get(cg.selectedBoardCardIdx);
            	//Beim Klicken wird die Karte entfernt und dann erst State gewechselt
            	if (!gp.blockBtn) {
            		g2.drawImage(gp.cardLoader.getCard(selectedBoardCard.defaultCard.id).image, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);
            	}
				g2.drawImage(gp.imageLoader.iconArrowMarker, dialogFirstOptionMarkerX , dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);

        		if (selectedBoardCard.isHide) {
            		g2.drawString("Aufdecken", dialogFirstOptionBeschriftungX, dialogFirstOptionBeschriftungY);		
        		} else {
            		g2.drawString("Angreifen", dialogFirstOptionBeschriftungX, dialogFirstOptionBeschriftungY);		
        		}
        	}  
			
            else if (cg.isState(cg.effektQuestionStateBoard) || cg.isState(cg.effektQuestionStateHand) || cg.isState(cg.effektQuestionStateGrave)) {
            	CardState selectedCard = null;
            	//Beim Klicken wird die Karte entfernt und dann erst State gewechselt
            	if (!gp.blockBtn) {
                	if (cg.isState(cg.effektQuestionStateBoard)) {
                		selectedCard = cg.player.boardCards.get(cg.selectedBoardCardIdx);
                	} else if (cg.isState(cg.effektQuestionStateHand)) {
                		selectedCard = cg.player.handCards.get(cg.selectedHandCardIdx);
                	} else if (cg.isState(cg.effektQuestionStateGrave)) {
                		selectedCard = cg.player.graveCards.get(cg.selectGraveCardIdx);
                	}
					g2.drawImage(gp.cardLoader.getCard(selectedCard.defaultCard.id).image, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);
            	}
				g2.drawImage(gp.imageLoader.iconArrowMarker, dialogFirstOptionMarkerX , dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
        		g2.drawString("Aktivieren", dialogFirstOptionBeschriftungX, dialogFirstOptionBeschriftungY);	         		
            }  
			 
            else if (cg.isState(cg.graveSelectedState) || cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.effektSelectOwnGraveState) || cg.isState(cg.effektSelectOponentGraveState)) {
            	if (cg.isState(cg.graveSelectedState) || cg.isState(cg.effektSelectOwnGraveState)) {
            		g2.drawImage(gp.cardLoader.getCard(cg.player.graveCards.get(cg.selectedIdx).defaultCard.id).image, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);

					if (cg.selectedIdx == cg.player.graveCards.size() - 1) {
						g2.drawImage(gp.imageLoader.iconArrowRigthDisabled, dialogArrowRightX , dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
					} else {
						g2.drawImage(gp.imageLoader.iconArrowRight, dialogArrowRightX, dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
					}

            	} else if (cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.effektSelectOponentGraveState)) {
            		g2.drawImage(gp.cardLoader.getCard(cg.oponent.graveCards.get(cg.selectedIdx).defaultCard.id).image, dialogCardX, dialogCardY, gp.cardWidth, gp.cardHeight, null);

					if (cg.selectedIdx == cg.oponent.graveCards.size() - 1) {
						g2.drawImage(gp.imageLoader.iconArrowRigthDisabled, dialogArrowRightX, dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
					} else {
						g2.drawImage(gp.imageLoader.iconArrowRight, dialogArrowRightX , dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
					}
            	}

				if (cg.selectedIdx == 0) {
					g2.drawImage(gp.imageLoader.iconArrowLeftDisabled, dialogArrowLeftX, dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
				} else {
					g2.drawImage(gp.imageLoader.iconArrowLeft, dialogArrowLeftX, dialogFirstOptionMarkerY, iconMarkerSize, iconMarkerSize, null);
				}
            }
        }
	}
	
	public void showEffektCard(CardState card) {
		effektCards.add(card);
	}
	
	public void showAttackOnCard(CardState angreifer, CardState angegriffener) {
		this.angreiferImg = angreifer.defaultCard.image;
		this.angegriffenerImg = angegriffener.defaultCard.image;
		showAngreifer = true;
		showAngegriffener = true;

		if (angegriffener.isHide && angegriffener.atk > angreifer.atk) {
			//Angreifer zerstört
			angriffAnimId = 2;
			gp.playSE(8);
		} else if (angegriffener.isHide && angegriffener.atk == angreifer.atk) {
			//Remis
			angriffAnimId = 3;
			gp.playSE(8);
		} else {

			if (angreifer.atk >= angegriffener.life) {
				//Angegriffener zerstört
				angriffAnimId = 4;
				gp.playSE(8);

			} else {
				//Angegriffener schaden
				angriffAnimId = 5;
				gp.playSE(7);
			}
		}
		showAngriffAnim = true;
	}

	public void showDirectAttack(CardState angreifer) {
		this.angreiferImg = angreifer.defaultCard.image;
		showAngreifer = true;
		showAngegriffener = false;
		angriffAnimId = 6;
		gp.playSE(7);
		showAngriffAnim = true;
	}

	private void drawSelectedCard(Graphics2D g2, List<CardState> cards, int idx) {
		if (cards.size() > idx) {
			CardState card = cards.get(idx);
			if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, gp.tileSize, gp.tileSize, gp.cardWidth * 3, gp.cardHeight * 3, null);
			} else {
				selectedCard.drawCardState(g2, cards.get(idx));
			}
		}
	}

	public void draw(Graphics2D g2) {
		if (gp.gameState == gp.cardGameState) {
			drawStats(g2, cg.player, lifeCounterPlayerY, fluchCounterPlayerY, segenCounterPlayerY, playerStatsPaperY);
			drawStapel(g2, stapelY, cg.player);
            drawBoardPanel(g2, boardPanely, cg.player, true);
            drawGrave(g2, boardPanely, cg.player, true);
            drawGrave(g2, boardPanelOponenty, cg.oponent, false);
            drawBoardPanel(g2, boardPanelOponenty, cg.oponent, false);
            drawHandPanel(g2, handPanelOponenty, cg.oponent, false);
			drawStapel(g2, stapelOponentY, cg.oponent);
			drawStats(g2, cg.oponent, lifeCounterOponentY, fluchCounterOponentY, segenCounterOponentY, oponentStatyPaperY);

			drawHandPanel(g2, handPanely, cg.player, true);

            drawDialog(g2);

			if (cg.isState(cg.effektSelectOwnBoardState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState)) {
				if (counterAnim >= 30) {
					counterAnim = 0;
					flicker = !flicker;
				}
				counterAnim++;
			}

			if (showAnimOnBoard  && !showAngriffAnim) {
				g2.drawImage(animImages[animIdx], animX, animY, gp.tileSize * 4, gp.tileSize * 4, null);

				if (fpsCounter % 5 == 0) {
					animIdx++;

					if (animIdx == animImages.length) {
						showAnimOnBoard = false;
					}
				}

			}

            if (effektCards.size() > 0 && !showAngriffAnim) {
            	if (counter >= 65) {
            		effektCards.remove(0);
            		counter = 0;
            	} else {
            		g2.drawImage(gp.cardLoader.getCard(effektCards.get(0).defaultCard.id).image, (gp.getWidth() - gp.cardWidth * 10) / 2, (gp.getHeight() - gp.cardHeight * 2) / 2, gp.cardWidth * 2, gp.cardHeight * 2, null);
            		counter++;
            	}
            }

            //Live Selected Panel
            if (cg.isState(cg.handCardState)) {
				drawSelectedCard(g2, cg.player.handCards, cg.selectedIdx);
            } else if (cg.isState(cg.handCardSelectedState) || cg.isState(cg.effektQuestionStateHand)) {
				drawSelectedCard(g2, cg.player.handCards, cg.selectedHandCardIdx);
            } else if (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) {
				drawSelectedCard(g2, cg.player.boardCards, cg.selectedIdx);
            } else if (cg.isState(cg.boardCardSelectedState) || cg.isState(cg.effektQuestionStateBoard)) {
				drawSelectedCard(g2, cg.player.boardCards, cg.selectedBoardCardIdx);
            } else if (cg.isState(cg.boardOponentState) || cg.isState(cg.selectCardToAttackState) || cg.isState(cg.effektSelectOponentBoardState)) {
				drawSelectedCard(g2, cg.oponent.boardCards, cg.selectedIdx);
            } else if (cg.isState(cg.graveSelectedState) || cg.isState(cg.effektSelectOwnGraveState)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.selectedIdx);
            }  else if (cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.effektSelectOponentGraveState)) {
				drawSelectedCard(g2, cg.oponent.graveCards, cg.selectedIdx);
            } else if (cg.isState(cg.effektQuestionStateGrave)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.selectGraveCardIdx);
			}

			//Hinweis Panel
            g2.setFont(Main.v.fontTimesNewRoman20);
			if (cg.inactiveMode) {
				g2.setColor(Color.red);
			    g2.drawString(msg, gravePanelx - gp.tileSize * 6, boardPanelOponenty - gp.cardHeight / 2);
			} else {
				g2.setColor(Color.yellow);
			    g2.drawString(msg, gravePanelx - gp.tileSize * 6, boardPanely + gp.cardHeight + gp.tileSize * 2);
			}
            g2.setFont(Main.v.fontTimesNewRoman36);

			//TODO: Refactoren viel kopierter code
			//Angriff Animation
			if (showAngriffAnim) {
				//Background
				g2.setColor(Color.black);
            	g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());
				
				if (showAngreifer) {
					g2.drawImage(angreiferImg, xAngriffAnimCardAngreifer, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);
				}

				if (showAngegriffener) {
					g2.drawImage(angegriffenerImg, xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);
				}

				angriffAnimCounter++;


				if (angriffAnimCounter >= 10) {
					if (angriffAnimId == 5 || angriffAnimId == 6) {
						g2.drawImage(direkterSchaden[schadenIdx], xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);

						schadenAnimCounter++;
					
						if (schadenAnimCounter % 3 == 0) {
							if (angriffAnimId == 5) {
								g2.setColor(Main.v.colorRedTransparent);
								g2.fillRect(xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight);
							}

							schadenIdx++;
							
							if (schadenIdx == direkterSchaden.length) {
								if (angriffAnimId == 5) {
									g2.setColor(Color.RED);
									g2.fillRect(xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight);
								}
								
								angriffAnimId = 0;
								schadenAnimCounter = 0;
								schadenIdx = 0;
							}
						}
					}  else if (angriffAnimId == 4) {
						g2.setColor(Color.black);
						g2.fillRect(xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight);
						showAngegriffener = false;
						g2.drawImage(explosionImg[explosionIdx], xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);

						explosionAnimCounter++;
					
						if (explosionAnimCounter % 3 == 0) {
							explosionIdx++;
							
							if (explosionIdx == explosionImg.length) {
								angriffAnimId = 0;
								explosionAnimCounter = 0;
								explosionIdx = 0;
							}
						}
					} else if (angriffAnimId == 3) {
						showAngreifer = false;
						showAngegriffener = false;

						g2.drawImage(explosionImg[explosionIdx], xAngriffAnimCardAngreifer, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);
						g2.drawImage(explosionImg[explosionIdx], xAngriffAnimCardAngegriffener, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);

						explosionAnimCounter++;
					
						if (explosionAnimCounter % 3 == 0) {
							explosionIdx++;
							
							if (explosionIdx == explosionImg.length) {
								angriffAnimId = 0;
								explosionAnimCounter = 0;
								explosionIdx = 0;
							}
						}
					} else if (angriffAnimId == 2) {

						showAngreifer = false;

						g2.drawImage(explosionImg[explosionIdx], xAngriffAnimCardAngreifer, yAngriffAnimCard, selectedCardWidth, selectedCardHeight, null);

						explosionAnimCounter++;
					
						if (explosionAnimCounter % 3 == 0) {
							explosionIdx++;
							
							if (explosionIdx == explosionImg.length) {
								angriffAnimId = 0;
								explosionAnimCounter = 0;
								explosionIdx = 0;
							}
						}
					}
				}

				if (angriffAnimCounter >= 75) {
					showAngriffAnim = false;
					angriffAnimCounter = 0;
				}
			}
			
		}
		fpsCounter++;
		if (fpsCounter >= 10000) {
			fpsCounter = 0;
		}
		
	}
}