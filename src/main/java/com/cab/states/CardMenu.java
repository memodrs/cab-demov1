package com.cab.states;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Positions;
import com.cab.draw.SelectedCard;
import com.cab.draw.ShakingKoordinaten;


public class CardMenu {
	GamePanel gp;

	List<Integer> truheAllCards = new ArrayList<>();
	List<Integer> truhe = new ArrayList<Integer>();
	List<Integer> stapel = new ArrayList<Integer>();

	int state;
	int filterState = 0;
	int truheState = 1;
	int stapelState = 2;
	int showMsgZuWenigKartenImStapelState = 3;

	boolean handleNavigation = true;
	int selectedIdx = 0;
	
	int limitCardsInRowTruhe = 5;
	int limitCardRowsTruhe = 4;
	int limitCardsPerPageTruhe = limitCardsInRowTruhe * limitCardRowsTruhe; 
	int totalPages;
	int currentPage = 0;

	int limitCardsInRowStapel = 7;
	public int limitMaxStapel = 20;

	List<Art> filterArten = new ArrayList<>();
	List<Boolean> filterValues = new ArrayList<>();
	List<Integer> xPositionFilterArten = new ArrayList<>();

	//Draw
	BufferedImage instactionKeyboard;
	ShakingKoordinaten koordinatenTruhePaper;
	ShakingKoordinaten koordinatenTruheString;
	ShakingKoordinaten koordinatenStapelPaper;
	ShakingKoordinaten koordinatenStapelString;

	SelectedCard selectedCard;

	public CardMenu(GamePanel gp) {
		this.gp = gp;

		int idx = 0;
		for (Art art : Art.values()) {
			filterArten.add(art);
			filterValues.add(true);
			xPositionFilterArten.add(Positions.tileSize2 + idx * Positions.tileSize1Point4);
			idx++;
		}

		try {
			instactionKeyboard = ImageIO.read(getClass().getResourceAsStream("/instractions/keyboard/cardEditor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		koordinatenTruhePaper = new ShakingKoordinaten(Positions.tileSize1Point15, Positions.tileSize2Point9);
		koordinatenStapelPaper = new ShakingKoordinaten(Positions.tileSize14Point55, Positions.tileSize8Point5);
		koordinatenTruheString = new ShakingKoordinaten(Positions.tileSize1Point6, Positions.tileSize4);
		koordinatenStapelString = new ShakingKoordinaten(Positions.tileSize15, Positions.tileSize9Point5);
		selectedCard = new SelectedCard(gp, Positions.precentScreenWidth83, Positions.tileSize1Point2);
	}

	public void showStapelEditor() {
		truheAllCards = gp.player.truhe;
		truhe = gp.player.truhe;
		stapel = gp.player.stapel;
		
		selectedIdx = 0;
		currentPage = 0;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);

		gp.gameState = gp.cardMenuState;	
		state = truheState;
	}

	private void filterTruhe() {
		List<Integer> filterList = new ArrayList<Integer>();

		for (int i = 0; i < filterArten.size(); i++) {
			if (filterValues.get(i)) {
				for (Integer id : truheAllCards) {
					Card card = gp.cardLoader.getCard(id);
					if (card.art == filterArten.get(i)) {
						filterList.add(id);
					}

				}
			}
		}

		truhe = filterList;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
	}

	private void switchStateToTruhe() {
		selectedIdx = 0;
		currentPage = 0;
		state = truheState;
	}

	private void removeHoloEffekt() {
		if (truhe.size() > 0 && gp.player.newCardIds.contains(truhe.get(selectedIdx))) {
			gp.player.newCardIds.remove(truhe.get(selectedIdx));
		}
	}

	public void update() {
		if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;

				if (gp.keyH.qPressed) {
					if (state == showMsgZuWenigKartenImStapelState) {
						switchStateToTruhe();
					} else {
						if (stapel.size() == limitMaxStapel) {
							gp.player.truhe = truheAllCards;
							gp.player.stapel = stapel;
							gp.save();
							gp.gameState = gp.hauptmenuState;
						} else {
							state = showMsgZuWenigKartenImStapelState;
						}
					}
				}

				else if (gp.keyH.upPressed == true) {		
					if (state == truheState) {
						removeHoloEffekt();
						if (selectedIdx < limitCardsInRowTruhe + currentPage * limitCardsPerPageTruhe) {
							if (currentPage > 0) {
								currentPage--;
								selectedIdx = selectedIdx - limitCardsInRowTruhe;
							} else {
								selectedIdx = 0;
								state = filterState;
							}
						} else {
							selectedIdx = selectedIdx - limitCardsInRowTruhe;
						} 
					} else if (state == stapelState) {
						if (selectedIdx >= limitCardsInRowStapel) {
							selectedIdx = selectedIdx - limitCardsInRowStapel;
						}
					}  
					gp.playSE(4);
				} 
				
				else if (gp.keyH.downPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						if (selectedIdx >= (limitCardsInRowTruhe * (limitCardRowsTruhe - 1) + currentPage * limitCardsPerPageTruhe)) {
						
							if (currentPage < (totalPages - 1)) {
								currentPage++;
								selectedIdx = selectedIdx + limitCardsInRowTruhe < truhe.size()? selectedIdx + limitCardsInRowTruhe: truhe.size() - 1;
							}
						} else {
							selectedIdx = selectedIdx + limitCardsInRowTruhe < truhe.size()? selectedIdx + limitCardsInRowTruhe : truhe.size() - 1;
						} 
					} else if (state == stapelState) {
						selectedIdx = (selectedIdx + limitCardsInRowStapel) < stapel.size()? selectedIdx + limitCardsInRowStapel : stapel.size() - 1;
					} else if (state == filterState) {
						selectedIdx = 0;
						state = truheState;
					}

					gp.playSE(4);
				}
				
				else if (gp.keyH.leftPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						if (selectedIdx % limitCardsInRowTruhe != 0) {
							selectedIdx = selectedIdx - 1;
						}
					} else if (state == stapelState) {
						if (selectedIdx % limitCardsInRowStapel != 0) {
							selectedIdx = selectedIdx - 1;
						}
					}
					
					else if (state == filterState) {
						if (selectedIdx > 0) {
							selectedIdx--;
						}
					}
					gp.playSE(4);
				}
				else if (gp.keyH.rightPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						if (((selectedIdx + 1) < truhe.size())) {
							if ((selectedIdx + 1) % limitCardsInRowTruhe != 0) {
								selectedIdx = selectedIdx + 1;
							}
						}
					} else if (state == stapelState) {
						if (((selectedIdx + 1) < stapel.size())) {
							if ((selectedIdx + 1) % limitCardsInRowStapel != 0) {
								selectedIdx = selectedIdx + 1;
							}
						}
					} else if (state == filterState) {
						if (selectedIdx < 6) {
							selectedIdx++;
						}
					}

					gp.playSE(4);
				}
				
				else if (gp.keyH.fPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						if (truhe.size() > 0 && stapel.size() < limitMaxStapel) {
							stapel.add(truhe.get(selectedIdx));
							truheAllCards.remove(truhe.get(selectedIdx));
							filterTruhe();

							if (truhe.size() == 0) {
								selectedIdx = 0;
								currentPage = 0;
								state = stapelState;
							} else {
								if (currentPage == totalPages) {
									currentPage--;
								} if (selectedIdx == truhe.size()) {
								selectedIdx--;
								}	
							}
							gp.playSE(1);
						}
					} else if (state == stapelState) {
						if (stapel.size() > 0) {
							truheAllCards.add(stapel.get(selectedIdx));
							stapel.remove(selectedIdx);
							filterTruhe();

							if (stapel.size() == 0) {
								selectedIdx = 0;
								state = truheState;
							} else if (selectedIdx == stapel.size()) {
								selectedIdx--;
							}

							gp.playSE(1);
						}
					} else if (state == filterState) {
						 filterValues.set(selectedIdx, !filterValues.get(selectedIdx));
						filterTruhe();
					} else if (state == showMsgZuWenigKartenImStapelState) {
						switchStateToTruhe();
					}
				}
				
				else if (gp.keyH.gPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						selectedIdx = 0;
						state = stapelState;
					} else if (state == stapelState) {
						switchStateToTruhe();
					}
				}
			}
		} 
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(gp.imageLoader.animCardEditorBG.get(), 0, 0, Main.screenWidth, Main.screenHeight, null); //background
		g2.drawImage(gp.imageLoader.paper02, Positions.tileSize, Positions.tileSize0Point7, Positions.tileSize13, Positions.tileSize2Point5, null); //FILTER
		g2.drawImage(gp.imageLoader.paper05, Positions.tileSize11Point4, Positions.tileSize3Point17, Positions.tileSize2Point8, Positions.tileSize1Point4, null); //SEITENANZAHL
		g2.drawImage(gp.imageLoader.paper08, Positions.tileSize29Point6, Positions.tileSize8Point7, Positions.tileSize2Point8, Positions.tileSize1Point3, null); //STAPELANZAHL
		g2.drawImage(instactionKeyboard, Positions.tileSize, Positions.tileSize18Point5, Positions.tileSize9, Positions.tileSize3Point6, null); //INSTRACTION KEYBOARD
		
		g2.drawImage(gp.imageLoader.paper11, Positions.tileSize15, 0, Positions.tileSize9, Positions.tileSize8, null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, Positions.tileSize15Point5, Positions.tileSize, Positions.tileSize1Point6, Positions.tileSize6, null); //INSTRACTION STATUS BILD

		g2.setFont(Main.v.brushedFont15);
		g2.setColor(Color.BLACK); 
		g2.drawString("Schild: Blockt einen Angriff", Positions.tileSize17Point5, Positions.tileSize1Point7);
		g2.drawString("Flügel: Kann nur direkt angreifen", Positions.tileSize17Point5, Positions.tileSize2Point8);
		g2.drawString("Gift: Wird nach 2 Runden Vernichtet", Positions.tileSize17Point5, Positions.tileSize4);
		g2.drawString("Feuer: Verliert Leben jede Runde", Positions.tileSize17Point5, Positions.tileSize5Point4);
		g2.drawString("Blitz: Kann nicht angreifen", Positions.tileSize17Point5, Positions.tileSize6Point5);

		for (int i = 0; i < filterArten.size(); i++) {
			g2.drawImage(gp.imageLoader.getArtIconForArt(filterArten.get(i), state == filterState && selectedIdx == i), xPositionFilterArten.get(i), Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
			if (filterValues.get(i)) {
				g2.drawImage(gp.imageLoader.iconCheck, xPositionFilterArten.get(i), 0, Positions.tileSize1Point4, Positions.tileSize, null);
			}
		}

		if (state == filterState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, Positions.tileSize, Positions.tileSize2, Positions.tileSize2, null);
		}

		if (state == truheState) {
			g2.drawImage(gp.imageLoader.paper06, koordinatenTruhePaper.getX(), koordinatenTruhePaper.getY(), Positions.tileSize3Point8, Positions.tileSize1Point55, null); //TRUHE
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Main.v.colorOrangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, Positions.tileSize2Point8, Positions.tileSize2, Positions.tileSize2, null);
			g2.drawString("Truhe", koordinatenTruheString.getX(), koordinatenTruheString.getY());        

		} else {
			g2.drawImage(gp.imageLoader.paper06, Positions.tileSize1Point15, Positions.tileSize2Point9, Positions.tileSize3Point8, Positions.tileSize1Point55, null); //TRUHE
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
			g2.drawString("Truhe", Positions.tileSize1Point6, Positions.tileSize4);        
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString(currentPage + 1 + " von " + totalPages, Positions.tileSize12Point2, Positions.tileSize4);   

		int startIndex = currentPage * limitCardsPerPageTruhe;
		int endIndex = (startIndex + limitCardsPerPageTruhe) <= truhe.size()? (startIndex + limitCardsPerPageTruhe) : truhe.size();

		int x = Positions.tileSize; 
		int y = Positions.tileSize4Point68; 

		for (int i = startIndex; i < endIndex; i++) {
			// falls endindex den falschen wert hat, timing problem manchmal
			if (i < truhe.size()) {

				Card card = gp.cardLoader.getCard(truhe.get(i));

				if (state == truheState && selectedIdx == i) {            		
					g2.drawImage(card.image, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
			
					if (gp.player.newCardIds.contains(card.id)) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).holoEffekt.get(), x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
					}
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
				} else {
					g2.setColor(Main.v.colorTransparent); 
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).image, x, y, gp.cardWidth, gp.cardHeight, null); 
					if (gp.player.newCardIds.contains(card.id)) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).holoEffekt.get(), x, y, gp.cardWidth, gp.cardHeight, null); 
					}
				}


				x += gp.cardWidth + Positions.tileSize0Point5;
				if (i % limitCardsInRowTruhe == limitCardsInRowTruhe - 1) {
					x = Positions.tileSize;
					y += gp.cardHeight + Positions.tileSize0Point5;
				}	
			}
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString("Karte schieben", Positions.tileSize4, Positions.tileSize19Point5);
		g2.drawString("Wechseln Truhe/Stapel", Positions.tileSize4, Positions.tileSize20Point2);
		g2.drawString("Verlassen", Positions.tileSize4, Positions.tileSize21);

		x = Positions.tileSize15;
		y = Positions.tileSize10; 
		
		if (state == stapelState) {
			g2.drawImage(gp.imageLoader.paper06, koordinatenStapelPaper.getX(), koordinatenStapelPaper.getY(), Positions.tileSize3Point8, Positions.tileSize1Point4, null); //STAPEL

			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Main.v.colorOrangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize13Point4, Positions.tileSize8Point25, Positions.tileSize2, Positions.tileSize2, null);
			g2.drawString("Stapel", koordinatenStapelString.getX(), koordinatenStapelString.getY()); 

		} else {
			g2.drawImage(gp.imageLoader.paper06, Positions.tileSize14Point55, Positions.tileSize8Point5, Positions.tileSize3Point8, Positions.tileSize1Point4, null); //STAPEL
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
			g2.drawString("Stapel", Positions.tileSize15, Positions.tileSize9Point5); 
		}

		g2.setFont(Main.v.brushedFont25);
		g2.setColor(Color.BLACK); 
		g2.drawString(stapel.size() + "/" + limitMaxStapel, Positions.tileSize31, Positions.tileSize9Point5);   
		
		for (int i = 0; i < stapel.size(); i++) {
			g2.setColor(Main.v.colorTransparent); 
			
			if (state == stapelState && selectedIdx == i) {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).image, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
				g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
			} else {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).image, x, y, gp.cardWidth, gp.cardHeight, null);
			}

			g2.fillRect(x, y, gp.cardWidth, gp.cardHeight);
			x += gp.cardWidth + Positions.tileSize0Point5;
			if (i % limitCardsInRowStapel == limitCardsInRowStapel- 1) {
				x = Positions.tileSize15;
				y += gp.cardHeight + Positions.tileSize0Point5;
			}
		}

		Card card = null;
		if (state == truheState && truhe.size() > 0) {
			card = gp.cardLoader.getCard(truhe.get(selectedIdx));
		} else if (state == stapelState && stapel.size() > 0) {
			card = gp.cardLoader.getCard(stapel.get(selectedIdx));
		}
		if (card != null) {
			selectedCard.drawCard(g2, card);
		} else if (state == filterState) {
			g2.setColor(Color.WHITE);
			g2.setFont(Main.v.brushedFont36);
			Art selectedArt = filterArten.get(selectedIdx);
			g2.drawString(selectedArt.toString(), Positions.tileSize34, Positions.tileSize0Point8);
			g2.setFont(Main.v.brushedFont15);

			if (selectedArt == Art.Fabelwesen) {
				g2.drawString("Fabelwesen können nur angreifen wenn sich ein Mensch auf deinem Board befindet", Positions.tileSize24, Positions.tileSize1Point8);
			} else if (selectedArt == Art.Nachtgestalt) {
				g2.drawString("Nachtgestalten können nur angreifen wenn sich kein Mensch auf deinem Board befindet", Positions.tileSize24, Positions.tileSize1Point8);
			}
		}

		if (state == showMsgZuWenigKartenImStapelState) {
			g2.setColor(Main.v.colorTransparentBlack);
			g2.fillRoundRect(Positions.tileSize12, Positions.screenHalfHeight, Positions.tileSize12, Positions.tileSize2, 35, 35);
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(5)); 
			g2.drawRoundRect(Positions.tileSize12, Positions.screenHalfHeight, Positions.tileSize12, Positions.tileSize2, 25, 25);
			g2.setColor(Color.RED);
			g2.setFont(Main.v.brushedFont20);
			g2.drawString("Du hast zu wenig Karten in deinem Stapel " + stapel.size() + "/" + limitMaxStapel, Positions.tileSize13, Positions.tileSize12);
			g2.setColor(Color.YELLOW);
			g2.drawString("Ok", Positions.tileSize18, Positions.tileSize12Point8);

		}

	}
}
