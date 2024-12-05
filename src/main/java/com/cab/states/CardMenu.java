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
    List<List<Integer>> savedStapel = new ArrayList<List<Integer>>();

	int state;
	final int filterState = 0;
	final int truheState = 1;
	final int stapelState = 2;
	final int showMsgState = 3;
	final int saveLoadState = 4;
	final int loadStapelState = 5;
	final int askLoadOrDeleteState = 7;
	
	String msg = "";
	int stateBeforeMsg;

	int selectedIdx = 0;
	int selectedLoadStapelIdx;
	
	int limitCardsInRowTruhe = 5;
	int limitCardRowsTruhe = 4;
	int limitCardsPerPageTruhe = limitCardsInRowTruhe * limitCardRowsTruhe; 
	int totalPages;
	int currentPage = 0;

	final int limitCardsInRowStapel = 7;
	final int limitSaves = 7;
	public final int limitMaxStapel = 20;

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
		koordinatenStapelPaper = new ShakingKoordinaten(Positions.tileSize14Point55, Positions.tileSize9);
		koordinatenTruheString = new ShakingKoordinaten(Positions.tileSize1Point6, Positions.tileSize4);
		koordinatenStapelString = new ShakingKoordinaten(Positions.tileSize15, Positions.tileSize10);
		selectedCard = new SelectedCard(gp, Positions.precentScreenWidth83, Positions.tileSize1Point2);
	}

	public void showStapelEditor() {
		truheAllCards = gp.player.truhe;
		truhe = gp.player.truhe;
		stapel = gp.player.stapel;
		savedStapel = gp.player.savedStapel;
		
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
					if (card.getArt() == filterArten.get(i)) {
						filterList.add(id);
					}

				}
			}
		}

		truhe = filterList;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
	}

	private void switchState(int newState) {
		selectedIdx = 0;
		currentPage = 0;
		state = newState;
	}

	private void showMsg(String msg) {
		stateBeforeMsg = state;
		this.msg = msg;
		switchState(showMsgState);
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
					if (state == showMsgState) {
						switchState(stateBeforeMsg);
					} else if (state == loadStapelState) {
						switchState(stapelState);
					} else if (state == askLoadOrDeleteState) {
						switchState(loadStapelState);
						selectedIdx = selectedLoadStapelIdx;
					} else {
						if (stapel.size() == limitMaxStapel) {
							gp.player.truhe = truheAllCards;
							gp.player.stapel = stapel;
							gp.player.savedStapel = savedStapel;
							gp.save();
							gp.gameState = gp.hauptmenuState;
						} else {
							showMsg("zuWenigKartenStapel");
						}
					}
				}

				else if (gp.keyH.upPressed) {		
					if (state == truheState) {
						removeHoloEffekt();
						if (selectedIdx < limitCardsInRowTruhe + currentPage * limitCardsPerPageTruhe) {
							if (currentPage > 0) {
								currentPage--;
								selectedIdx = selectedIdx - limitCardsInRowTruhe;
							} else {
								switchState(filterState);
							}
						} else {
							selectedIdx = selectedIdx - limitCardsInRowTruhe;
						} 
					} else if (state == stapelState) {
						if (selectedIdx >= limitCardsInRowStapel) {
							selectedIdx = selectedIdx - limitCardsInRowStapel;
						} else {
							switchState(saveLoadState);
						}
					} else if (state == loadStapelState) {
						if (selectedIdx > 0) {
							selectedIdx--;
						}
					} else if (state == askLoadOrDeleteState) {
						selectedIdx = 0;
					} 
					gp.playSE(4);
				} 
				
				else if (gp.keyH.downPressed) {
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
						switchState(truheState);
					} else if (state == saveLoadState) {
						switchState(stapelState);
					} else if (state == loadStapelState) {
						if (selectedIdx < savedStapel.size() - 1) {
							selectedIdx++;
						}
					} else if (state == askLoadOrDeleteState) {
						selectedIdx = 1;
					} 
					gp.playSE(4);
				}
				
				else if (gp.keyH.leftPressed) {
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
					}  else if (state == saveLoadState) {
						selectedIdx = 0;
					}
					gp.playSE(4);
				}
				else if (gp.keyH.rightPressed) {
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
					} else if (state == saveLoadState) {
						selectedIdx = 1;
					}

					gp.playSE(4);
				}
				
				else if (gp.keyH.fPressed) {
					if (state == truheState) {
						removeHoloEffekt();
						if (truhe.size() > 0 && stapel.size() < limitMaxStapel) {
							stapel.add(truhe.get(selectedIdx));
							truheAllCards.remove(truhe.get(selectedIdx));
							filterTruhe();

							if (truhe.size() == 0) {
								currentPage = 0;
								switchState(stapelState);
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
								switchState(truheState);
							} else if (selectedIdx == stapel.size()) {
								selectedIdx--;
							}

							gp.playSE(1);
						}
					} else if (state == filterState) {
						 filterValues.set(selectedIdx, !filterValues.get(selectedIdx));
						filterTruhe();
					} else if (state == showMsgState) {
						switchState(stateBeforeMsg);
					} else if (state == saveLoadState) {
						if (selectedIdx == 0) {
							if (savedStapel.size() >= limitSaves) {
								showMsg("saveFailSpeicher");
							} else if (stapel.size() < limitMaxStapel) {
								showMsg("saveFailMaxStapel");
							} else {
								savedStapel.add(new ArrayList<>(stapel));
								showMsg("saveSuccess");
							}
						} else if (selectedIdx == 1) {
							if (savedStapel.size() > 0) {
								switchState(loadStapelState);
							} else {
								showMsg("keineStapelGespeichert");								
							}
						}
					} else if (state == showMsgState) {
						switchState(stateBeforeMsg);
					} else if (state == loadStapelState) {
						selectedLoadStapelIdx = selectedIdx;
						switchState(askLoadOrDeleteState);
					} else if (state == askLoadOrDeleteState) {
						if (selectedIdx == 0) {
							List<Integer> ids = new ArrayList<Integer>();
							for (Integer id : stapel) {
								ids.add(id);
							}
							for (Integer id : ids) {
								truheAllCards.add(id);
								stapel.remove(id);
							}

							ids = new ArrayList<Integer>();
							for (Integer id : savedStapel.get(selectedLoadStapelIdx)) {
								ids.add(id);
							}
							for (Integer id : ids) {
								truheAllCards.remove(id);
								stapel.add(id);
							}
							filterTruhe();
							switchState(saveLoadState);
						} else if (selectedIdx == 1) {
							savedStapel.remove(selectedLoadStapelIdx);
							if (savedStapel.size() > 0) {
								switchState(loadStapelState);
							} else {
								switchState(saveLoadState);
							}
						} 							
					}
				}
				
				else if (gp.keyH.gPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						switchState(stapelState);
					} else if (state == stapelState || state == saveLoadState) {
						switchState(truheState);
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
		
		g2.drawImage(gp.imageLoader.paper07, Positions.tileSize14Point55, 0, Positions.tileSize11, Positions.tileSize8, null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, Positions.tileSize15Point5, Positions.tileSize, Positions.tileSize1Point6, Positions.tileSize6, null); //INSTRACTION STATUS BILD

		g2.setFont(Main.v.brushedFont15);
		g2.setColor(Color.BLACK); 
		g2.drawString(gp.t("statusSchildB"),  Positions.tileSize17Point5, Positions.tileSize1Point7);
		g2.drawString(gp.t("statusFluegelB"), Positions.tileSize17Point5, Positions.tileSize2Point8);
		g2.drawString(gp.t("statusGiftB"),    Positions.tileSize17Point5, Positions.tileSize4);
		g2.drawString(gp.t("statusFeuerB"), 	 Positions.tileSize17Point5, Positions.tileSize5Point4);
		g2.drawString(gp.t("statusBlitzB"),	 Positions.tileSize17Point5, Positions.tileSize6Point5);

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
			g2.drawString(gp.t("truhe"), koordinatenTruheString.getX(), koordinatenTruheString.getY());        

		} else {
			g2.drawImage(gp.imageLoader.paper06, Positions.tileSize1Point15, Positions.tileSize2Point9, Positions.tileSize3Point8, Positions.tileSize1Point55, null); //TRUHE
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
			g2.drawString(gp.t("truhe"), Positions.tileSize1Point6, Positions.tileSize4);        
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString(currentPage + 1 + " " + gp.t("von") + " " + totalPages, Positions.tileSize12Point2, Positions.tileSize4);   

		int startIndex = currentPage * limitCardsPerPageTruhe;
		int endIndex = (startIndex + limitCardsPerPageTruhe) <= truhe.size()? (startIndex + limitCardsPerPageTruhe) : truhe.size();

		int x = Positions.tileSize; 
		int y = Positions.tileSize4Point68; 

		for (int i = startIndex; i < endIndex; i++) {
			// falls endindex den falschen wert hat, timing problem manchmal
			if (i < truhe.size()) {

				Card card = gp.cardLoader.getCard(truhe.get(i));

				if (state == truheState && selectedIdx == i) {            		
					g2.drawImage(card.getImage(), x, y, Positions.selectedCardWidth, Positions.selectedCardHeight, null); 
			
					if (gp.player.newCardIds.contains(card.getId())) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getHoloEffekt().get(), x, y, Positions.selectedCardWidth, Positions.selectedCardHeight, null); 
					}
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, Positions.selectedCardWidth, Positions.selectedCardHeight, null); 
				} else {
					g2.setColor(Main.v.colorTransparent); 
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getImage(), x, y, Positions.cardWidth, Positions.cardHeight, null); 
					if (gp.player.newCardIds.contains(card.getId())) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getHoloEffekt().get(), x, y, Positions.cardWidth, Positions.cardHeight, null); 
					}
				}


				x += Positions.cardWidth + Positions.tileSize0Point5;
				if (i % limitCardsInRowTruhe == limitCardsInRowTruhe - 1) {
					x = Positions.tileSize;
					y += Positions.cardHeight + Positions.tileSize0Point5;
				}	
			}
		}

		g2.setFont(Main.v.brushedFont20);
		g2.setColor(Color.BLACK); 
		g2.drawString(gp.t("karteSchieben"), Positions.tileSize4, Positions.tileSize19Point5);
		g2.drawString(gp.t("wechselnTruheStapel"), Positions.tileSize4, Positions.tileSize20Point2);
		g2.drawString(gp.t("verlassen"), Positions.tileSize4, Positions.tileSize21);

		x = Positions.tileSize15;
		y = Positions.tileSize10Point5; 
		
		g2.drawImage(gp.imageLoader.paper01, Positions.tileSize14Point8, Positions.tileSize7Point6, Positions.tileSize1Point4, Positions.tileSize1Point2, null);
		g2.drawImage(gp.imageLoader.iconSave, Positions.tileSize15, Positions.tileSize7Point6, Positions.tileSize, Positions.tileSize, null);
		g2.drawImage(gp.imageLoader.paper01, Positions.tileSize16Point4, Positions.tileSize7Point6, Positions.tileSize1Point4, Positions.tileSize1Point2, null);
		g2.drawImage(gp.imageLoader.iconLoad, Positions.tileSize16Point6, Positions.tileSize7Point6, Positions.tileSize, Positions.tileSize, null);

		if (state == stapelState) {
			g2.drawImage(gp.imageLoader.paper06, koordinatenStapelPaper.getX(), koordinatenStapelPaper.getY(), Positions.tileSize3Point8, Positions.tileSize1Point4, null); //STAPEL

			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Main.v.colorOrangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize13Point4, Positions.tileSize8Point7, Positions.tileSize2, Positions.tileSize2, null);
			g2.drawString(gp.t("stapel"), koordinatenStapelString.getX(), koordinatenStapelString.getY()); 

		} else {
			g2.drawImage(gp.imageLoader.paper06, Positions.tileSize14Point55, Positions.tileSize9, Positions.tileSize3Point8, Positions.tileSize1Point4, null); //STAPEL
			g2.setFont(Main.v.brushedFont25);
			g2.setColor(Color.BLACK); 
			g2.drawString(gp.t("stapel"), Positions.tileSize15, Positions.tileSize10); 
		}



		if (state == saveLoadState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize13Point4, Positions.tileSize7Point2, Positions.tileSize2, Positions.tileSize2, null);
			if (selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.boosterHover, Positions.tileSize15, Positions.tileSize7Point6, Positions.tileSize, Positions.tileSize, null);
			} else if (selectedIdx == 1) {
				g2.drawImage(gp.imageLoader.boosterHover, Positions.tileSize16Point6, Positions.tileSize7Point6, Positions.tileSize, Positions.tileSize, null);
			}
		}

		g2.setFont(Main.v.brushedFont25);
		g2.setColor(Color.BLACK); 
		g2.drawString(stapel.size() + "/" + limitMaxStapel, Positions.tileSize31, Positions.tileSize9Point5);   
		
		for (int i = 0; i < stapel.size(); i++) {
			g2.setColor(Main.v.colorTransparent); 
			
			if (state == stapelState && selectedIdx == i) {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).getImage(), x, y, Positions.selectedCardWidth, Positions.selectedCardHeight, null); 
				g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, Positions.selectedCardWidth, Positions.selectedCardHeight, null); 
			} else {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).getImage(), x, y, Positions.cardWidth, Positions.cardHeight, null);
			}

			g2.fillRect(x, y, Positions.cardWidth, Positions.cardHeight);
			x += Positions.cardWidth + Positions.tileSize0Point5;
			if (i % limitCardsInRowStapel == limitCardsInRowStapel- 1) {
				x = Positions.tileSize15;
				y += Positions.cardHeight + Positions.tileSize0Point5;
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
			g2.drawString(gp.t(selectedArt.getTextbaustein()), Positions.tileSize30, Positions.tileSize0Point8);
			g2.setFont(Main.v.brushedFont15);

			if (selectedArt == Art.Fabelwesen) {
				g2.drawString(gp.t("fabelwesenHinweis"), Positions.tileSize24, Positions.tileSize1Point8);
			} else if (selectedArt == Art.Nachtgestalt) {
				g2.drawString(gp.t("nachtgestalenHinweis"), Positions.tileSize24, Positions.tileSize1Point8);
			}
		}

		if (state == loadStapelState || state == askLoadOrDeleteState) {
			g2.setColor(Main.v.colorTransparentDarkBlack); 
			g2.fillRect(0, 0, Positions.screenWidth, Positions.screenHeight);
			g2.setFont(Main.v.brushedFont30);
			int select;
			if (state == loadStapelState) {
				select = selectedIdx;
			} else {
				select = selectedLoadStapelIdx;
			}
			for (int i = 0; i < savedStapel.size(); i++) {
				int selectedAbstandX = Positions.tileSize;

				if (select == i) {
					if (state == loadStapelState) {
						g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize + selectedAbstandX, Positions.tileSize1Point2 + Positions.tileSize3 * i, Positions.tileSize2, Positions.tileSize2, null);
					}
					g2.drawImage(gp.imageLoader.boosterHover, Positions.tileSize2, Positions.tileSize0Point924 + Positions.tileSize3 * i, Positions.tileSize32, Positions.tileSize2Point7, null);
				}

				g2.setColor(gp.getColorSelection(i, select));
				g2.drawImage(gp.imageLoader.paper01, Positions.tileSize2Point6 + selectedAbstandX, Positions.tileSize1Point7 + Positions.tileSize3 * i, Positions.tileSize, Positions.tileSize, null);

				g2.drawString(i + "", Positions.tileSize3 + selectedAbstandX, Positions.tileSize2Point3 + Positions.tileSize3 * i);

				for( int j = 0; j < savedStapel.get(i).size(); j++) {
					double angle = (j % 2 == 0) ? Math.toRadians(-5) : Math.toRadians(5); // Neigung abhängig von Parität

					int centerX = Positions.tileSize4 + Positions.tileSize1Point2 * j + Positions.tileSize4 / 2;
					int centerY = Positions.tileSize1Point2 + Positions.tileSize3 * i;
	
					g2.rotate(angle, centerX, centerY);
	
					g2.drawImage(gp.cardLoader.getCard(savedStapel.get(i).get(j)).getImage(), Positions.tileSize4 + Positions.tileSize1Point2 * j + selectedAbstandX, centerY, Positions.tileSize1Point2, Positions.tileSize2, null);

					if (select != i) {
						g2.setPaint(Main.v.colorGardianSelectFromGrave);
						g2.fillRect(Positions.tileSize4 + Positions.tileSize1Point2 * j + selectedAbstandX, centerY, Positions.tileSize1Point2, Positions.tileSize2);
					}
	
					g2.rotate(-angle, centerX, centerY);
	
				}
			}

			if (state == askLoadOrDeleteState) {
				g2.setColor(Main.v.colorTransparentBlack);
				g2.fillRoundRect(Positions.tileSize15, Positions.tileSize10, Positions.tileSize4, Positions.tileSize3, 35, 35);
				g2.setColor(Color.white);
				g2.setStroke(new BasicStroke(5)); 
				g2.drawRoundRect(Positions.tileSize15, Positions.tileSize10, Positions.tileSize4, Positions.tileSize3, 25, 25);
				g2.setColor(Color.RED);
				g2.setFont(Main.v.brushedFont20);
				int yArrowMarker = selectedIdx == 0? Positions.tileSize10 : Positions.tileSize11;
				g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize14Point55, yArrowMarker, Positions.tileSize2, Positions.tileSize2, null);

				g2.setColor(gp.getColorSelection(0, selectedIdx));
				g2.drawString(gp.t("laden"), Positions.tileSize16Point3, Positions.tileSize11);
				g2.setColor(gp.getColorSelection(1, selectedIdx));
				g2.drawString(gp.t("loeschen"), Positions.tileSize16Point3, Positions.tileSize12);
			}
    	}

		if (state == showMsgState) {
			g2.setColor(Main.v.colorTransparentBlack);
			g2.fillRoundRect(Positions.tileSize14, Positions.screenHalfHeight, Positions.tileSize10, Positions.tileSize2, 35, 35);
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(5)); 
			g2.drawRoundRect(Positions.tileSize14, Positions.screenHalfHeight, Positions.tileSize10, Positions.tileSize2, 25, 25);
			g2.setColor(Color.RED);
			g2.setFont(Main.v.brushedFont20);
			g2.drawString(gp.t(msg), Positions.tileSize15, Positions.tileSize12);
			g2.setColor(Color.YELLOW);
			g2.drawString("Ok", Positions.tileSize19, Positions.tileSize12Point8);
		}
		
	}
}
