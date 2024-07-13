package com.cab;

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
import com.cab.card.Status;


public class CardMenu {
	GamePanel gp;
	BufferedImage bg;
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
	
	Art[] artTypes = {Art.Unbekannt, Art.Mensch, Art.Tier, Art.Fabelwesen, Art.Nachtgestalt, Art.Segen, Art.Fluch};
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
	int abstandX; 
	int abstandY; 

	int paperFilterX, paperFilterY, paperFilterWidth, paperFilterHeight;
	int paperTruheX, paperTruheY, paperTruheWidth, paperTruheHeight;
	int paperTruheSeiteX, paperTruheSeiteY, paperTruheSeiteWidth, paperTruheSeiteHeight;
	int paperStapelX, paperStapelY, paperStapelWidth, paperStapelHeight;
	int paperStapelAnzahlX, paperStapelAnzahlY, paperStapelAnzahlWidth, paperStapelAnzahlHeight;
	int paperInstractionX, paperInstractionY, paperInstractionWidth, paperInstractionHeight;

	BufferedImage instactionKeyboard;
	int fStringKeyX, fStringKeyY;
	int gStringKeyX, gStringKeyY;
	int qStringKeyX, qStringKeyY;

	int iconArraowMarkerSize;
	int iconArrowMarkerTruheY; 

	int filterPaperWidth, filterPaperHeight;
	int filterPaperX, filterPaperY;

	int iconArtSize;
	int iconFilterArtY;
	int iconArtUnbekanntX;
	int iconArtMenschX;
	int iconArtTierX;
	int iconArtFabelwesenX;
	int iconArtNachtgestaltX;
	int iconArtSegenX;
	int iconArtFluchX;

	int stringTruheX, stringTruheY;
	int stringTruheAnzahlX;
	int truheX, truheY;

	int arrowMarkerStapelX, arrowMarkerStapelY;
	int stringStapelY;
	int stapelX, stapelY;
	int stringStapelAnzahlX, stringStapelAnzahlY;

	int selectedCardX, selectedCardY;
	int paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight;
	int iconHeartX, iconHeartY, iconHeartSize;
	int stringLifeX, stringLifeY;
	int iconAtkX, iconAtkY, iconAtkSize;
	int atkStringX, atkStringY;
	int kostenStringHeaderX, kostenStringHeaderY;
	int kostenStringX, kostenStringY;
	int iconArtX, iconArtY;
	int nameStringX, nameStringY;

	int paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight;
	int headerEffektStringX, headerEffektStringY;

	int statusPaperX, statusPaperY, statusPaperSize;
	int statusIconX, statusIconY, statusIconSize;

	int instractionPaperStatusX, instractionPaperY, instractionPaperWidth, instractionPaperHeight;
	int instractionPaperImgX, instractionPaperImgY, instractionPaperImgWidth, instractionPaperImgHeight;
	int instractionPaperStringSchildY, instractionPaperStringFluegelY, instractionPaperStringGiftY, instractionPaperStringFeuerY, instractionPaperStringBlitzY;
	int instractionPaperStringX; 

	public CardMenu(GamePanel gp) {
		this.gp = gp;

		abstandX = gp.tileSize / 2; 
		abstandY = gp.tileSize / 2;

		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bgs/cardEditor/0.png"));
			instactionKeyboard = ImageIO.read(getClass().getResourceAsStream("/instractions/keyboard/cardEditor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		iconArraowMarkerSize = gp.tileSize * 2;

		paperFilterX = gp.tileSize;
		paperFilterY = (int) (gp.tileSize * 0.7);
		paperFilterWidth = gp.tileSize * 13;
		paperFilterHeight = (int) (gp.tileSize * 2.5);

		paperTruheX = (int) (gp.tileSize * 1.15);
		paperTruheY = (int) (gp.tileSize * 2.9);
		paperTruheWidth = (int) (gp.tileSize * 3.8);
		paperTruheHeight = (int) (gp.tileSize * 1.55);

		paperTruheSeiteX = (int) (gp.tileSize * 11.4);
		paperTruheSeiteY = (int) (gp.tileSize * 3.17);
		paperTruheSeiteWidth = (int) (gp.tileSize * 2.8);
		paperTruheSeiteHeight = (int) (gp.tileSize * 1.4);

		paperStapelX = (int) (gp.tileSize * 14.55);
		paperStapelY = (int) (gp.tileSize * 8.5);
		paperStapelWidth = (int) (gp.tileSize * 3.8);
		paperStapelHeight = (int) (gp.tileSize * 1.4);

		paperStapelAnzahlX = (int) (gp.tileSize * 29.6);
		paperStapelAnzahlY = (int) (gp.tileSize * 8.7);
		paperStapelAnzahlWidth = (int) (gp.tileSize * 2.8);
		paperStapelAnzahlHeight = (int) (gp.tileSize * 1.3);

		paperInstractionX = gp.tileSize;
		paperInstractionY = (int) (Main.screenHeight * 0.815);
		paperInstractionHeight = gp.tileSize * 9;
		paperInstractionWidth = (int) (gp.tileSize * 3.6);

		iconArrowMarkerTruheY = (int) (gp.tileSize * 2.8);

		iconArtSize = (int) (gp.tileSize * 1.4);
		iconFilterArtY = (int) (gp.tileSize * 1.2);

		iconArtUnbekanntX = (int) (gp.tileSize * 1.5);
		iconArtMenschX = (int) (gp.tileSize * 3.8);
		iconArtTierX = iconArtMenschX + iconArtSize + 10;
		iconArtFabelwesenX = iconArtTierX + iconArtSize + 10;
		iconArtNachtgestaltX = iconArtFabelwesenX + iconArtSize + 10;
		iconArtSegenX = iconArtNachtgestaltX + iconArtSize + 10;
		iconArtFluchX = iconArtSegenX + iconArtSize + 10;

		stringTruheX = (int) (gp.tileSize * 1.6);
		stringTruheY = gp.tileSize * 4;
		stringTruheAnzahlX = (int) (stringTruheX + gp.tileSize * 10.6);
		truheX = gp.tileSize;
		truheY = stringTruheY + (int) (gp.tileSize * 0.68);

		stapelX = gp.tileSize * 15;
		stapelY = gp.tileSize * 10;

		fStringKeyX = gp.tileSize * 4;
		fStringKeyY = (int) (Main.screenHeight * 0.85);
		gStringKeyX = gp.tileSize * 4;
		gStringKeyY = (int) (Main.screenHeight * 0.885);
		qStringKeyX = gp.tileSize * 4;
		qStringKeyY = (int) (Main.screenHeight * 0.924);

		arrowMarkerStapelX = (int) (gp.tileSize * 13.4);
		arrowMarkerStapelY = (int) (gp.tileSize * 8.25);
		stringStapelY = (int) (gp.tileSize * 9.5);
		stringStapelAnzahlX = (int) (stapelX + gp.tileSize * 16);
		stringStapelAnzahlY = (int) (gp.tileSize * 9.5);

		selectedCardX = (int) (Main.screenWidth * 0.83);
		selectedCardY = (int) (gp.tileSize * 1.2);
		paperStatsX = (int) (Main.screenWidth * 0.884);
		paperStatsY = (int) (gp.tileSize * 8.88);
		paperStatsWidth = (int) (gp.tileSize * 4.6);
		paperStatsHeight = (int) (gp.tileSize * 3.2);
		iconHeartX = (int) (Main.screenWidth * 0.904);
		iconHeartY = (int) (gp.tileSize * 10);
		iconHeartSize = (int) (gp.tileSize * 0.75);
		stringLifeX = (int) (Main.screenWidth * 0.909);
		stringLifeY = (int) (gp.tileSize * 11.6);
		iconAtkX = (int) (Main.screenWidth * 0.928);
		iconAtkY = (int) (gp.tileSize * 10);
		iconAtkSize = (int) (gp.tileSize * 0.75);
		atkStringX = (int) (Main.screenWidth * 0.932);
		atkStringY = (int) (gp.tileSize * 11.6);
		kostenStringHeaderX = (int) (Main.screenWidth * 0.909);
		kostenStringHeaderY = (int) (gp.tileSize * 10.5);
		kostenStringX = (int) (Main.screenWidth * 0.93);
		kostenStringY = (int) (gp.tileSize * 11.5);
		iconArtX = (int) (Main.screenWidth * 0.948);
		iconArtY = (int) (gp.tileSize * 10.5);
		nameStringX = (int) (Main.screenWidth * 0.83);
		nameStringY = (int) (gp.tileSize * 0.8);
	
		paperEffektX = (int) (Main.screenWidth * 0.813);
		paperEffektY = (int) (gp.tileSize * 11.8);
		paperEffektWidth = (int) (gp.tileSize * 7.5);
		paperEffektHeight = (int) (gp.tileSize * 10);

		headerEffektStringX = (int) (Main.screenWidth * 0.87);
		headerEffektStringY = (int) (gp.tileSize * 12.8);

		statusPaperX = (int) (Main.screenWidth * 0.952);
		statusPaperY = (int) (gp.tileSize * 9.5);
		statusPaperSize = (int) (iconArtSize * 0.9);
		statusIconX = (int) (Main.screenWidth * 0.96);
		statusIconY = (int) (gp.tileSize * 9.75);
		statusIconSize = (int) (gp.tileSize * 0.65);

		instractionPaperStatusX = (int) (gp.tileSize * 15);
		instractionPaperY = (int) (0);
		instractionPaperWidth = gp.tileSize * 9;
		instractionPaperHeight = gp.tileSize * 8;

		instractionPaperImgX = (int) (gp.tileSize * 15.5);
		instractionPaperImgY = gp.tileSize;
		instractionPaperImgWidth = (int) (gp.tileSize * 1.6);
		instractionPaperImgHeight = gp.tileSize * 6;

		instractionPaperStringSchildY = (int) (gp.tileSize * 1.7);
		instractionPaperStringFluegelY = (int) (gp.tileSize * 2.8);
		instractionPaperStringGiftY = (int) (gp.tileSize * 4);
		instractionPaperStringFeuerY = (int) (gp.tileSize * 5.4);
		instractionPaperStringBlitzY = (int) (gp.tileSize * 6.5);

		instractionPaperStringX = (int) (gp.tileSize * 17.5);

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

	private BufferedImage getArtIconForArt(Art art, boolean selectHover) {
		switch (art) {
			case Unbekannt: return selectHover? gp.imageLoader.iconArtUnbekanntHover : gp.imageLoader.iconArtUnbekannt;
			case Mensch: return selectHover? gp.imageLoader.iconArtMenschHover : gp.imageLoader.iconArtMensch;
			case Tier: return selectHover? gp.imageLoader.iconArtTierHover : gp.imageLoader.iconArtTier;
			case Fabelwesen: return selectHover? gp.imageLoader.iconArtFabelwesenHover : gp.imageLoader.iconArtFabelwesen;
			case Nachtgestalt: return selectHover? gp.imageLoader.iconArtNachtgestaltHover : gp.imageLoader.iconArtNachtgestalt;
			case Segen: return selectHover? gp.imageLoader.iconArtSegenHover : gp.imageLoader.iconArtSegen;
			case Fluch: return selectHover? gp.imageLoader.iconArtFluchHover : gp.imageLoader.iconArtFluch;
			default: return null;
		}
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
		if (gp.gameState == gp.cardMenuState) {
			if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
				if (!gp.blockBtn) {
					gp.blockBtn = true;

					if (gp.keyH.qPressed) {
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
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(bg, 0, 0, Main.screenWidth, Main.screenHeight, null); //background
		g2.drawImage(gp.imageLoader.paper02, paperFilterX, paperFilterY, paperFilterWidth, paperFilterHeight, null); //FILTER
		g2.drawImage(gp.imageLoader.paper06, paperTruheX, paperTruheY, paperTruheWidth, paperTruheHeight, null); //TRUHE
		g2.drawImage(gp.imageLoader.paper05, paperTruheSeiteX, paperTruheSeiteY, paperTruheSeiteWidth, paperTruheSeiteHeight, null); //SEITENANZAHL
		g2.drawImage(gp.imageLoader.paper06, paperStapelX, paperStapelY, paperStapelWidth, paperStapelHeight, null); //STAPEL
		g2.drawImage(gp.imageLoader.paper08, paperStapelAnzahlX, paperStapelAnzahlY, paperStapelAnzahlWidth, paperStapelAnzahlHeight, null); //STAPELANZAHL
		g2.drawImage(instactionKeyboard, paperInstractionX, paperInstractionY, paperInstractionHeight, paperInstractionWidth, null); //INSTRACTION KEYBOARD
		
		g2.drawImage(gp.imageLoader.paper11, instractionPaperStatusX, instractionPaperY, instractionPaperWidth, instractionPaperHeight, null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, instractionPaperImgX, instractionPaperImgY, instractionPaperImgWidth, instractionPaperImgHeight, null); //INSTRACTION STATUS BILD

		g2.setFont(Main.v.brushedFont15);
		g2.setColor(Color.BLACK); 
		g2.drawString("Schild: Blockt einen Angriff", instractionPaperStringX, instractionPaperStringSchildY);
		g2.drawString("Flügel: Kann nur direkt angreifen", instractionPaperStringX, instractionPaperStringFluegelY);
		g2.drawString("Gift: Wird nach 2 Runden Vernichtet", instractionPaperStringX, instractionPaperStringGiftY);
		g2.drawString("Feuer: Verliert Leben jede Runde", instractionPaperStringX, instractionPaperStringFeuerY);
		g2.drawString("Blitz: Kann nicht angreifen", instractionPaperStringX, instractionPaperStringBlitzY);


		g2.drawImage(getArtIconForArt(Art.Unbekannt, state == filterState && getSelectedArt() == Art.Unbekannt), iconArtUnbekanntX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterUnbekannt) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtUnbekanntX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Mensch, state == filterState && getSelectedArt() == Art.Mensch), iconArtMenschX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterMenschen) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtMenschX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Tier, state == filterState && getSelectedArt() == Art.Tier), iconArtTierX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterTiere) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtTierX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Fabelwesen, state == filterState && getSelectedArt() == Art.Fabelwesen), iconArtFabelwesenX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterFabelwesen) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtFabelwesenX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Nachtgestalt, state == filterState && getSelectedArt() == Art.Nachtgestalt), iconArtNachtgestaltX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterNachtgestalten) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtNachtgestaltX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Segen, state == filterState && getSelectedArt() == Art.Segen), iconArtSegenX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterSegen) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtSegenX, 0, iconArtSize, gp.tileSize, null);
		}

		g2.drawImage(getArtIconForArt(Art.Fluch, state == filterState && getSelectedArt() == Art.Fluch), iconArtFluchX, iconFilterArtY, iconArtSize, iconArtSize, null);
		if (filterFluch) {
			g2.drawImage(gp.imageLoader.iconCheck, iconArtFluchX, 0, iconArtSize, gp.tileSize, null);
		}

		if (state == filterState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, gp.tileSize, iconArraowMarkerSize, iconArraowMarkerSize, null);
		}

		if (state == truheState) {
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Main.v.colorOrangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, iconArrowMarkerTruheY, iconArraowMarkerSize, iconArraowMarkerSize, null);
			g2.drawString("Truhe", stringTruheX, stringTruheY);        

		} else {
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
			g2.drawString("Truhe", stringTruheX, stringTruheY);        
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString(currentPage + 1 + " von " + totalPages, stringTruheAnzahlX, stringTruheY);   

		int startIndex = currentPage * limitCardsPerPageTruhe;
		int endIndex = (startIndex + limitCardsPerPageTruhe) <= truhe.size()? (startIndex + limitCardsPerPageTruhe) : truhe.size();

		int x = truheX; 
		int y = truheY; 

		for (int i = startIndex; i < endIndex; i++) {
			// falls endindex den falschen wert hat, timing problem manchmal
			if (i < truhe.size()) {

				if (state == truheState && selectedIdx == i) {            		
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).image, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
					g2.drawImage(gp.imageLoader.selectedCardHover, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
				} else {
					g2.setColor(Main.v.colorTransparent); 
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).image, x, y, gp.cardWidth, gp.cardHeight, null); 
				}

				x += gp.cardWidth + abstandX;
				if (i % limitCardsInRowTruhe == limitCardsInRowTruhe - 1) {
					x = truheX;
					y += gp.cardHeight + abstandY;
				}	
			}
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString("Karte schieben", fStringKeyX, fStringKeyY);
		g2.drawString("Wechseln Truhe/Stapel", gStringKeyX, gStringKeyY);
		g2.drawString("Verlassen", qStringKeyX, qStringKeyY);

		x = stapelX;
		y = stapelY; 
		
		if (state == stapelState) {
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Main.v.colorOrangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, arrowMarkerStapelX, arrowMarkerStapelY, iconArraowMarkerSize, iconArraowMarkerSize, null);
		} else {
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
		}

		g2.drawString("Stapel", stapelX, stringStapelY); 
		g2.setFont(Main.v.brushedFont25);
		g2.setColor(Color.BLACK); 
		g2.drawString(stapel.size() + "/" + limitMaxStapel, stringStapelAnzahlX, stringStapelAnzahlY);   
		
		for (int i = 0; i < stapel.size(); i++) {
			g2.setColor(Main.v.colorTransparent); 
			
			if (state == stapelState && selectedIdx == i) {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).image, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
				g2.drawImage(gp.imageLoader.selectedCardHover, x, y, gp.selectedCardWidth, gp.selectedCardHeight, null); 
			} else {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).image, x, y, gp.cardWidth, gp.cardHeight, null);
			}

			g2.fillRect(x, y, gp.cardWidth, gp.cardHeight);
			x += gp.cardWidth + abstandX;
			if (i % limitCardsInRowStapel == limitCardsInRowStapel- 1) {
				x = stapelX;
				y += gp.cardHeight + abstandY;
			}
		}

		Card card = null;
		if (state == truheState && truhe.size() > 0) {
			card = gp.cardLoader.getCard(truhe.get(selectedIdx));
		} else if (state == stapelState && stapel.size() > 0) {
			card = gp.cardLoader.getCard(stapel.get(selectedIdx));
		}
		if (card != null) {
			g2.drawImage(card.image, selectedCardX, selectedCardY, gp.cardWidth * 3, gp.cardHeight * 3, null); 
			
			g2.drawImage(gp.imageLoader.paperStats, paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight, null); 
			
			if (card.status != Status.Default) {
				g2.drawImage(gp.imageLoader.paper05, statusPaperX, statusPaperY, statusPaperSize, statusPaperSize, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(card.status), statusIconX, statusIconY, statusIconSize, statusIconSize, null);
			}
			
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Color.BLACK); 
			if (!card.isSpell) {
				g2.setFont(Main.v.brushedFont36);
				g2.drawImage(gp.imageLoader.iconHeart, iconHeartX, iconHeartY, iconHeartSize, iconHeartSize, null); 
				g2.drawString(card.def + "", stringLifeX, stringLifeY);
				g2.drawImage(gp.imageLoader.iconAtk, iconAtkX, iconAtkY, iconAtkSize, iconAtkSize, null); 
				g2.drawString(card.atk + "", atkStringX, atkStringY);
			} else {
				g2.setFont(Main.v.brushedFont25);
				g2.drawString("Kosten", kostenStringHeaderX, kostenStringHeaderY);
				g2.setFont(Main.v.brushedFont36);
				g2.drawString(card.kosten + "", kostenStringX, kostenStringY);
			}

			g2.drawImage(getArtIconForArt(card.art, false), iconArtX, iconArtY, iconArtSize, iconArtSize, null); 


			if (card.beschreibung.length() > 0) {
				g2.setFont(Main.v.brushedFont20);
				g2.setColor(Color.BLACK); 

				y = (int) (gp.tileSize * 13.8);

				g2.drawImage(gp.imageLoader.paper09, paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight, null); 
				g2.drawString("EFFEKT", headerEffektStringX, headerEffektStringY);

				for (String line : card.beschreibung.split("\n")) {
					g2.drawString(line, (int) (gp.getWidth() * 0.84), y);
    				y += (int) (gp.tileSize * 0.7) ;
    			}
			}

			g2.setColor(Color.WHITE);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString(card.name, nameStringX, nameStringY);
		} else if (state == filterState) {
			g2.setColor(Color.WHITE);
			g2.setFont(Main.v.brushedFont36);
			Art selectedArt = getSelectedArt();
			g2.drawString(selectedArt.toString(), nameStringX, nameStringY);
			g2.setFont(Main.v.brushedFont15);

			if (selectedArt == Art.Fabelwesen) {
				g2.drawString("Fabelwesen können nur angreifen wenn sich ein Mensch auf deinem Board befindet", (int) (Main.screenWidth * 0.6), nameStringY + gp.tileSize);
			} else if (selectedArt == Art.Nachtgestalt) {
				g2.drawString("Nachtgestalten können nur angreifen wenn sich kein Mensch auf deinem Board befindet", (int) (Main.screenWidth * 0.6), nameStringY + gp.tileSize);
			}
		}

	}
}
