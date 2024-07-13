package com.cab.cardGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;

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
	int selectedCardY;
	int selectedCardNameY;
	int selectedCardIconLebenY;
	int selectedCardLifeX;
	int selectedCardLifeY;
	int selectedCardIconAtkX;
	int selectedCArdIconAtkY;
	int selectedCardAtkX;
	int selectedCardAtkY;
	int selectedCardIconArtY;
	int selectedCardArtY;

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
		lifeCounterPlayerY = handPanely + gp.tileSize * 3;
		fluchCounterPlayerY = lifeCounterPlayerY - (int) (gp.tileSize * 1.3);
		segenCounterPlayerY = fluchCounterPlayerY - (int) (gp.tileSize * 1.3);
		lifeCounterOponentY = handPanelOponenty;
		fluchCounterOponentY = lifeCounterOponentY + (int) (gp.tileSize * 1.3);
		segenCounterOponentY = fluchCounterOponentY + (int) (gp.tileSize * 1.3);		
		playerStatsIconX = Main.screenWidth - gp.tileSize * 3 - 15;
		playerStatasNumberX = playerStatsIconX + gp.imageLoader.iconHeart.getWidth() + gp.tileSize + 10;
		statsBeschriftungOffset = gp.tileSize - 15;


		//Stapel
		stapelX = (int) (Main.screenWidth * 0.831);
		stapelY = (int) (Main.screenHeight * 0.835);
		stapelOponentY = (int) (Main.screenHeight * 0.04);
		stapelBeschriftungX = (int) (stapelX + gp.cardWidth / 2.5);
		stapelBeschriftungEinstelligX = stapelBeschriftungX + (int) (gp.tileSize / 2.8);
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

		//Selected Card
		selectedCardWidth = (int) (Main.screenWidth * 0.15);
		selectedCardHeight = (int) (Main.screenHeight * 0.4);
		selectedCardY = 50;
		selectedCardX = (int) (Main.screenWidth * 0.025);
		selectedCardNameY = (int) ((selectedCardY + selectedCardHeight) * 1.11);
		selectedCardIconLebenY = (int) ((selectedCardY + selectedCardHeight) * 1.165);
		selectedCardLifeX = (int) ((selectedCardX + gp.tileSize) * 1.1);
		selectedCardLifeY = (int) ((selectedCardY + selectedCardHeight) * 1.22);
		selectedCardIconAtkX = (int) ((selectedCardX + gp.tileSize) * 1.9);
		selectedCArdIconAtkY = (int) ((selectedCardY + selectedCardHeight) * 1.165);
		selectedCardAtkX = (int) ((selectedCardX + gp.tileSize) * 2.4);
		selectedCardAtkY = (int) ((selectedCardY + selectedCardHeight) * 1.22);
		selectedCardIconArtY = (int) ((selectedCardY + selectedCardHeight) * 1.26);
		selectedCardArtY = (int) ((selectedCardY + selectedCardHeight) * 1.32);

		xAngriffAnimCardAngreifer = (Main.screenWidth - 2 * selectedCardWidth) / 3;
		xAngriffAnimCardAngegriffener = 2 * xAngriffAnimCardAngreifer + selectedCardWidth;
		yAngriffAnimCard = (Main.screenHeight - selectedCardHeight) / 2;
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

	public void showAnimLebenStatsAenderung(Player p, boolean isPositiv) {
		animX = (int) (playerStatsIconX * 0.992);
		animY = p.isPlayer? (int) (lifeCounterPlayerY * 0.94) : (int) (lifeCounterOponentY - gp.tileSize * 1.5);
		animImages = isPositiv? heilenImages : schadenImages;
		startAnim();
	}

	public void showAnimSegenStatsAenderung(Player p, boolean isPositiv) {
		animX = (int) (playerStatsIconX * 0.992);
		animY = p.isPlayer? (int) (segenCounterPlayerY * 0.94) : (int) (segenCounterOponentY - gp.tileSize * 1.5);
		animImages = isPositiv? heilenImages : schadenImages;
		startAnim();
	}

	public void showAnimFluchStatsAenderung(Player p, boolean isPositiv) {
		animX = (int) (playerStatsIconX * 0.992);
		animY = p.isPlayer? (int) (fluchCounterPlayerY * 0.94) : (int) (fluchCounterOponentY - gp.tileSize * 1.5);
		animImages = isPositiv? heilenImages : schadenImages;
		startAnim();
	}

	private void drawStats(Graphics2D g2, Player p, int lifeCounterY, int fluchCounterY, int segenCounterY) {
	    g2.setColor(Color.white);
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
	        int offsetX = handPanelx + handCardWidth * i + 5 * i;
	        
			int handPanelYselectedCard = i == cg.selectedIdx && cg.isState(cg.handCardState)? handPanely - gp.tileSize : handPanely - 10;
	        if (isPlayer) {
				if (!(i == cg.selectedHandCardIdx && cg.isState(cg.handCardSelectedState))) {
					g2.drawImage(gp.cardLoader.getCard(p.handCards.get(i).defaultCard.id).image, offsetX, handPanelYselectedCard, handCardWidth, handCardHeight, null);

					if (cg.isEffektManualActivatable(p, p.handCards.get(i), cg.effekteMangaer.triggerManualFromHand)) {
						g2.drawImage(gp.imageLoader.iconEffektAvailable, offsetX + iconEffektAvailableSizeX, handPanelYselectedCard + iconEffektAvailableSizeY, iconEffektAvailableSize, iconEffektAvailableSize, null);
	                }
				}
	        } else {
	            g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, handPanely, handCardWidth, handCardHeight, null);
	        }

	        if (cg.isState(cg.handCardState) && isPlayer) {
				g2.drawImage(gp.imageLoader.iconArrowMarker, handPanelx - iconMarkerSize, handPanely, iconMarkerSize, iconMarkerSize, null);
	            
				if (i == cg.selectedIdx) {
	                g2.setPaint(Main.v.colorGardianSelectFrom);
	            }
	            g2.fillRect(offsetX, handPanelYselectedCard, handCardWidth, handCardHeight);
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

			if (cg.isState(cg.selectCardToAttackState)) {
				if (isPlayer) {
					if (p.boardCards.get(i).id == cg.savedIdPlayerAttack) {
						g2.setColor(colorActiveEffektCard);
					}
				} else {
					if (flicker) {
						g2.setColor(colorSelectionOponentCardAnimOne);
					} else {
						g2.setColor(colorSelectionOponentCardAnimTwo);
					}
				}
				g2.fillRect(offsetX - (int) (gp.tileSize * 0.1), y - (int) (gp.tileSize * 0.1) , gp.cardWidth + (int) (gp.tileSize * 0.2), gp.cardHeight + (int) (gp.tileSize * 0.2)); 
			}

			if (cg.isState(cg.effektSelectOponentBoardState)) {
				if (isPlayer) {
					if (p.boardCards.get(i) == cg.activeEffektCard) {
						g2.setColor(colorActiveEffektCard);
						g2.fillRect(offsetX - (int) (gp.tileSize * 0.1), y - (int) (gp.tileSize * 0.1) , gp.cardWidth + (int) (gp.tileSize * 0.2), gp.cardHeight + (int) (gp.tileSize * 0.2)); 
					}
				} else {
					if (cg.activeEffektCard.isCardValidForSelection(p.boardCards.get(i))) {
						if (flicker) {
							g2.setColor(colorSelectionOponentCardAnimOne);
						} else {
							g2.setColor(colorSelectionOponentCardAnimTwo);
						}
						g2.fillRect(offsetX - (int) (gp.tileSize * 0.1), y - (int) (gp.tileSize * 0.1) , gp.cardWidth + (int) (gp.tileSize * 0.2), gp.cardHeight + (int) (gp.tileSize * 0.2)); 
					}
				}
			}

			if (cg.isState(cg.effektSelectOwnBoardState)) {
				if (isPlayer) {
					if (p.boardCards.get(i) == cg.activeEffektCard) {
						g2.setColor(colorActiveEffektCard);
						g2.fillRect(offsetX - (int) (gp.tileSize * 0.1), y - (int) (gp.tileSize * 0.1) , gp.cardWidth + (int) (gp.tileSize * 0.2), gp.cardHeight + (int) (gp.tileSize * 0.2)); 
					}

					if (cg.activeEffektCard.isCardValidForSelection(p.boardCards.get(i))) {
						if (flicker) {
							g2.setColor(colorSelectionOwnCardAnimOne);
						} else {
							g2.setColor(colorSelectionOwnCardAnimTwo);
						}
						g2.fillRect(offsetX - (int) (gp.tileSize * 0.1), y - (int) (gp.tileSize * 0.1) , gp.cardWidth + (int) (gp.tileSize * 0.2), gp.cardHeight + (int) (gp.tileSize * 0.2)); 
					}
				} 
			}

        	if (p.boardCards.get(i).isHide) {
                g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, gp.cardWidth, gp.cardHeight, null);
        	} else {
    			g2.drawImage(gp.cardLoader.getCard(p.boardCards.get(i).defaultCard.id).image, offsetX, y, gp.cardWidth, gp.cardHeight, null);
				
				if (cg.isEffektManualActivatable(p, p.boardCards.get(i), cg.effekteMangaer.triggerManualFromBoard) && isPlayer) {
					g2.drawImage(gp.imageLoader.iconEffektAvailable, offsetX + iconEffektAvailableSizeX, y + iconEffektAvailableSizeY, iconEffektAvailableSize, iconEffektAvailableSize, null);
				}

				if (cg.checkIsAttackAlowed(p, i) && !cg.inactiveMode && isPlayer) {
					g2.drawImage(gp.imageLoader.iconAttackAvailable, offsetX + iconAttackAvailableX, y + gp.cardHeight, gp.tileSize, gp.tileSize, null);
				}

        	}


        	
        	if (i == cg.selectedIdx) {
			    if ((cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) && isPlayer) {
			        g2.setPaint(Main.v.colorGardianSelectFrom);
			        g2.fillRect(offsetX, y, gp.cardWidth, gp.cardHeight);


			    } else if ((cg.isState(cg.boardOponentState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState)) && !isPlayer) {
            		g2.setPaint(Main.v.colorGardianSelectFromOponent);
	            	g2.fillRect(offsetX, y, gp.cardWidth, gp.cardHeight);	
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
        		g2.setPaint(Main.v.colorGardianSelectFrom);
            } else  if (!isPlayer && cg.isState(cg.graveOponentState)) {
        		g2.setPaint(Main.v.colorGardianSelectFromOponent);
            } 
            
        	g2.fillRect(gravePanelx, y, gp.cardWidth, gp.cardHeight);

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
	
	public void drawSelectedCard(Graphics2D g2, List<CardState> cards, int idx, boolean isOponent) {
		g2.setFont(Main.v.fontTimesNewRoman36);
        g2.setColor(Main.v.colorTransparent); 

    	if (cards.size() > idx) {
    		CardState selectedCard = cards.get(idx);
    		
    		if (isOponent && selectedCard.isHide) {
        		g2.drawImage(gp.imageLoader.cardBackgroundImage, selectedCardX, selectedCardY, selectedCardWidth, selectedCardHeight, null);
    		} else {
        		g2.drawImage(gp.cardLoader.getCard(selectedCard.defaultCard.id).image, selectedCardX, selectedCardY, selectedCardWidth, selectedCardHeight, null);
        		g2.setColor(Color.white);
        		g2.drawString(gp.cardLoader.getCard(selectedCard.defaultCard.id).name, selectedCardX, selectedCardNameY);
        		
        		if (selectedCard.art == Art.Fluch) {
            		g2.drawImage(gp.imageLoader.iconArtFluch, selectedCardX, selectedCardY + selectedCardHeight + 70, gp.tileSize, gp.tileSize, null);
            		g2.drawString(selectedCard.defaultCard.kosten + "", selectedCardX + gp.tileSize + 10, selectedCardY + selectedCardHeight + 100);
     	        } else if (selectedCard.art == Art.Segen)  {
            		g2.drawImage(gp.imageLoader.iconArtSegen, selectedCardX, selectedCardY + selectedCardHeight + 70, gp.tileSize, gp.tileSize, null);
            		g2.drawString(selectedCard.defaultCard.kosten + "", selectedCardX + gp.tileSize + 10, selectedCardY + selectedCardHeight + 100);
     	        } else {
					if (selectedCard.life > selectedCard.defaultCard.def) {
						g2.setColor(Color.orange);
					} else if (selectedCard.life < selectedCard.defaultCard.def) {
						g2.setColor(Color.red);
					} else {
						g2.setColor(Color.white);
					}
					g2.drawImage(gp.imageLoader.iconHeart, selectedCardX, selectedCardIconLebenY, gp.tileSize, gp.tileSize, null);
            		g2.drawString(selectedCard.life + "", selectedCardLifeX, selectedCardLifeY);

            		g2.drawImage(gp.imageLoader.iconAtk, selectedCardIconAtkX, selectedCArdIconAtkY, gp.tileSize, gp.tileSize, null);
					if (selectedCard.atk > selectedCard.defaultCard.atk) {
						g2.setColor(Color.orange);
					} else if (selectedCard.atk < selectedCard.defaultCard.atk) {
						g2.setColor(Color.red);
					} else {
						g2.setColor(Color.white);
					}
            		g2.drawString(selectedCard.atk + "", selectedCardAtkX, selectedCardAtkY);
            		
					g2.setColor(Color.pink);
            		g2.drawString(selectedCard.art + "", selectedCardLifeX, selectedCardArtY);
     	        }
        		g2.setColor(Color.white);
        		int y = (int) ((selectedCardY + selectedCardHeight) * 1.39);
    			g2.setFont(Main.v.fontTimesNewRoman20);
    			for (String line : selectedCard.defaultCard.beschreibung.split("\n")) {
    				g2.drawString(line, selectedCardX, y);
    				y += gp.tileSize / 2;
    			}
    		}
			g2.setFont(Main.v.fontTimesNewRoman36);
    		g2.setColor(Main.v.colorTransparent); 
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

	public void draw(Graphics2D g2) {
		if (gp.gameState == gp.cardGameState) {
			g2.setFont(Main.v.fontTimesNewRoman36);
			

			g2.drawImage(gp.imageLoader.cardGameBG, 0, 0, Main.screenWidth, Main.screenHeight, null);

			if (cg.inactiveMode) {
				g2.setColor(Color.white);
			    g2.drawString("inactive", gravePanelx + gp.tileSize * 4, boardPanely);
			}

			drawStats(g2, cg.player, lifeCounterPlayerY, fluchCounterPlayerY, segenCounterPlayerY);
			drawStapel(g2, stapelY, cg.player);
			drawHandPanel(g2, handPanely, cg.player, true);
            drawBoardPanel(g2, boardPanely, cg.player, true);
            drawGrave(g2, boardPanely, cg.player, true);
            drawGrave(g2, boardPanelOponenty, cg.oponent, false);
            drawBoardPanel(g2, boardPanelOponenty, cg.oponent, false);
            drawHandPanel(g2, handPanelOponenty, cg.oponent, false);
			drawStapel(g2, stapelOponentY, cg.oponent);
			drawStats(g2, cg.oponent, lifeCounterOponentY, fluchCounterOponentY, segenCounterOponentY);
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
            	drawSelectedCard(g2, cg.player.handCards, cg.selectedIdx, false);
            } else if (cg.isState(cg.handCardSelectedState) || cg.isState(cg.effektQuestionStateHand)) {
            	drawSelectedCard(g2, cg.player.handCards, cg.selectedHandCardIdx, false);
            } else if (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) {
            	drawSelectedCard(g2, cg.player.boardCards, cg.selectedIdx, false);
            } else if (cg.isState(cg.boardCardSelectedState) || cg.isState(cg.effektQuestionStateBoard)) {
            	drawSelectedCard(g2, cg.player.boardCards, cg.selectedBoardCardIdx, false);
            } else if (cg.isState(cg.boardOponentState) || cg.isState(cg.selectCardToAttackState) || cg.isState(cg.effektSelectOponentBoardState)) {
            	drawSelectedCard(g2, cg.oponent.boardCards, cg.selectedIdx, true);
            } else if (cg.isState(cg.graveSelectedState) || cg.isState(cg.effektSelectOwnGraveState)) {
            	drawSelectedCard(g2, cg.player.graveCards, cg.selectedIdx, true);
            }  else if (cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.effektSelectOponentGraveState)) {
            	drawSelectedCard(g2, cg.oponent.graveCards, cg.selectedIdx, true);
            } else if (cg.isState(cg.effektQuestionStateGrave)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.selectGraveCardIdx, false);
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