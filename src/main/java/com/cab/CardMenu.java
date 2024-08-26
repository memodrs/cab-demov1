package com.cab;

//ToDo Notes
// Verlassen mit zu wenig Karten im Stapel eine message anzeigen und das verlassen verhindern
// [Optional] text anzeigen wenn keine Karten in der Truhe oder Stapel zB. Keine Karten in der Truhe/Stapel vorhanden

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Positions;
import com.cab.draw.SelectedCard;
import com.cab.draw.ShakingKoordinaten;


public class CardMenu {
	GamePanel gp;
	public boolean isIngame;

	List<Integer> truheAllCards = new ArrayList<>();
	List<Integer> truhe = new ArrayList<Integer>();
	List<Integer> stapel = new ArrayList<Integer>();

	int state;
	int filterState = 0;
	int truheState = 1;
	int stapelState = 2;
	int infoState = 3;

	boolean handleNavigation = true;
	int selectedIdx = 0;
	
	boolean filterUnbekannt = true;
	boolean filterMenschen = true;
	boolean filterTiere = true;
	boolean filterFabelwesen = true;
	boolean filterNachtgestalten = true;
	boolean filterSegen = true;
	boolean filterFluch = true;

	int limitCardsInRowTruhe = 5;
	int limitCardRowsTruhe = 4;
	int limitCardsPerPageTruhe = limitCardsInRowTruhe * limitCardRowsTruhe; 
	int totalPages;
	int currentPage = 0;

	int limitCardsInRowStapel = 7;
	public int limitMaxStapel = 21;

	//Draw
	BufferedImage instactionKeyboard;
	ShakingKoordinaten koordinatenTruhePaper;
	ShakingKoordinaten koordinatenTruheString;
	ShakingKoordinaten koordinatenStapelPaper;
	ShakingKoordinaten koordinatenStapelString;

	SelectedCard selectedCard;

	public CardMenu(GamePanel gp) {
		this.gp = gp;

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

	public void showStapelEditor(boolean isIngame) {
		truheAllCards = gp.player.truhe;
		truhe = gp.player.truhe;
		stapel = gp.player.stapel;
		
		selectedIdx = 0;
		currentPage = 0;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
		gp.cardMenu.isIngame = isIngame;

		gp.gameState = gp.cardMenuState;	
		state = truheState;

		Collections.sort(truhe);
		Collections.sort(stapel);
	}


	private Art getSelectedArt() {
		if (state != filterState) {
			return null;
		}

		switch (selectedIdx) {
			case 0: return Art.Unbekannt;
			case 1: return Art.Mensch;
			case 2: return Art.Tier;
			case 3: return Art.Fabelwesen;
			case 4: return Art.Nachtgestalt;
			case 5: return Art.Segen;
			case 6: return Art.Fluch;
			default: return null;
		}
	}

	private void filterTruhe() {
		List<Integer> filterList = new ArrayList<Integer>();

		for (Integer id : truheAllCards) {
			Card card = gp.cardLoader.getCard(id);

			if (card.art == Art.Unbekannt && filterUnbekannt) {
				filterList.add(id);
			} else if (card.art == Art.Mensch && filterMenschen) {
				filterList.add(id);
			} else if (card.art == Art.Tier && filterTiere) {
				filterList.add(id);
			} else if (card.art == Art.Fabelwesen && filterFabelwesen) {
				filterList.add(id);
			} else if (card.art == Art.Nachtgestalt && filterNachtgestalten) {
				filterList.add(id);
			} else if (card.art == Art.Segen && filterSegen) {
				filterList.add(id);
			} else if (card.art == Art.Fluch && filterFluch) {
				filterList.add(id);
			}
		}

		truhe = filterList;
		Collections.sort(truhe);
		Collections.sort(stapel);
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
	}

	public void update() {
		if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
			if (!gp.blockBtn) {
				gp.blockBtn = true;

				if (gp.keyH.qPressed) {
					gp.player.newCardIds = new ArrayList<>();
					gp.player.truhe = truheAllCards;
					gp.player.stapel = stapel;
					gp.gameState = gp.hauptmenuState;
				}

				else if (gp.keyH.upPressed == true) {		
					if (state == truheState) {
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
						if (selectedIdx == 0) {
							filterUnbekannt = !filterUnbekannt;
						} else if (selectedIdx == 1) {
							filterMenschen = !filterMenschen;
						} else if (selectedIdx == 2) {
							filterTiere = !filterTiere;
						} else if (selectedIdx == 3) {
							filterFabelwesen = !filterFabelwesen;
						} else if (selectedIdx == 4) {
							filterNachtgestalten = !filterNachtgestalten;
						} else if (selectedIdx == 5) {
							filterSegen = !filterSegen;
						} else if (selectedIdx == 6) {
							filterFluch = !filterFluch;
						}
						filterTruhe();
					}
				}
				
				else if (gp.keyH.gPressed == true) {
					if (state == truheState) {
						selectedIdx = 0;
						state = stapelState;
					} else if (state == stapelState) {
						selectedIdx = 0;
						currentPage = 0;
						state = truheState;
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
		g2.drawImage(instactionKeyboard, Positions.tileSize, Positions.precentScreenHeight815, Positions.tileSize9, Positions.tileSize3Point6, null); //INSTRACTION KEYBOARD
		
		g2.drawImage(gp.imageLoader.paper11, Positions.tileSize15, 0, Positions.tileSize9, Positions.tileSize8, null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, Positions.tileSize15Point5, Positions.tileSize, Positions.tileSize1Point6, Positions.tileSize6, null); //INSTRACTION STATUS BILD

		g2.setFont(Main.v.brushedFont15);
		g2.setColor(Color.BLACK); 
		g2.drawString("Schild: Blockt einen Angriff", Positions.tileSize17Point5, Positions.tileSize1Point7);
		g2.drawString("Flügel: Kann nur direkt angreifen", Positions.tileSize17Point5, Positions.tileSize2Point8);
		g2.drawString("Gift: Wird nach 2 Runden Vernichtet", Positions.tileSize17Point5, Positions.tileSize4);
		g2.drawString("Feuer: Verliert Leben jede Runde", Positions.tileSize17Point5, Positions.tileSize5Point4);
		g2.drawString("Blitz: Kann nicht angreifen", Positions.tileSize17Point5, Positions.tileSize6Point5);


		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Unbekannt, state == filterState && getSelectedArt() == Art.Unbekannt), Positions.tileSize1Point5, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterUnbekannt) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize1Point5, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Mensch, state == filterState && getSelectedArt() == Art.Mensch), Positions.tileSize3Point8, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterMenschen) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize3Point8, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Tier, state == filterState && getSelectedArt() == Art.Tier), Positions.tileSize5Point2, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterTiere) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize5Point2, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Fabelwesen, state == filterState && getSelectedArt() == Art.Fabelwesen), Positions.tileSize6Point6, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterFabelwesen) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize6Point6, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Nachtgestalt, state == filterState && getSelectedArt() == Art.Nachtgestalt), Positions.tileSize8, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterNachtgestalten) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize8, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Segen, state == filterState && getSelectedArt() == Art.Segen), Positions.tileSize9Point4, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterSegen) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize9Point4, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Fluch, state == filterState && getSelectedArt() == Art.Fluch), Positions.tileSize10Point8, Positions.tileSize1Point2, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
		if (filterFluch) {
			g2.drawImage(gp.imageLoader.iconCheck, Positions.tileSize10Point8, 0, Positions.tileSize1Point4, gp.tileSize, null);
		}

		if (state == filterState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, gp.tileSize, Positions.tileSize2, Positions.tileSize2, null);
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
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).holoEffektImg.get(), x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
					}
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
				} else {
					g2.setColor(Main.v.colorTransparent); 
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).image, x, y, gp.cardWidth, gp.cardHeight, null); 
					if (gp.player.newCardIds.contains(card.id)) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).holoEffektImg.get(), x, y, gp.cardWidth, gp.cardHeight, null); 
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
		g2.drawString("Karte schieben", Positions.tileSize4, Positions.precentScreenHeight85);
		g2.drawString("Wechseln Truhe/Stapel", Positions.tileSize4, Positions.precentScreenHeight885);
		g2.drawString("Verlassen", Positions.tileSize4, Positions.precentScreenHeight924);

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
			Art selectedArt = getSelectedArt();
			g2.drawString(selectedArt.toString(), Positions.precentScreenWidth83, Positions.tileSize0Point8);
			g2.setFont(Main.v.brushedFont15);

			if (selectedArt == Art.Fabelwesen) {
				g2.drawString("Fabelwesen können nur angreifen wenn sich ein Mensch auf deinem Board befindet", (int) (Main.screenWidth * 0.6), Positions.tileSize0Point8 + gp.tileSize);
			} else if (selectedArt == Art.Nachtgestalt) {
				g2.drawString("Nachtgestalten können nur angreifen wenn sich kein Mensch auf deinem Board befindet", (int) (Main.screenWidth * 0.6), Positions.tileSize0Point8 + gp.tileSize);
			}
		}

	}
}
